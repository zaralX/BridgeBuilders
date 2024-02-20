package ru.zaralx.bridgebuilders.commons.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import ru.zaralx.bridgebuilders.BridgeBuilders;
import ru.zaralx.bridgebuilders.commons.npc.Replay;
import ru.zaralx.bridgebuilders.commons.npc.ReplayReflect;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final String id;
    private final String name;
    private final Integer minPlayers;
    private final Integer maxPlayers;
    private final Location mapCenter;
    private final List<GameTeam> teams = new ArrayList<>();
    private boolean runned = false;

    public Game(String id, FileConfiguration configuration) {
        World world = Bukkit.getWorld(configuration.getString("world"));
        this.id = id;
        this.name = configuration.getString("name");
        this.minPlayers = configuration.getInt("min_players");
        this.maxPlayers = configuration.getInt("max_players");
        this.mapCenter = new Location(
                world,
                configuration.getInt("map_center.x")+0.5,
                configuration.getInt("map_center.y")+0.5,
                configuration.getInt("map_center.z")+0.5
        );


        for (GameTeamType gameTeamType : GameTeamType.values()) {
            Location location = new Location(
                    world,
                    configuration.getInt(gameTeamType.name().toLowerCase() + "_team.spawn.x")+0.5,
                    configuration.getInt(gameTeamType.name().toLowerCase() + "_team.spawn.y")+0.5,
                    configuration.getInt(gameTeamType.name().toLowerCase() + "_team.spawn.z")+0.5
            );
            Replay replay = BridgeBuilders.getInstance().getRecordsDatabase().loadReplay(
                    world,
                    configuration.getString("replay"),
                    ReplayReflect.fromDegrees(configuration.getString(
                            gameTeamType.name().toLowerCase() + "_team.reflect"
                    )),
                    this.mapCenter
            );

            GameTeam gameTeam = new GameTeam(location, gameTeamType, replay);
            teams.add(gameTeam);
        }
    }

    public boolean join(Player player) {
        if (getMaxPlayers() <= getPlayers().size()) return false;

        GameTeam lowestTeam = teams.get(0);

        for (GameTeam team : teams) {
            if (team.getPlayers().size() < lowestTeam.getPlayers().size()) {
                lowestTeam = team;
            }
        }

        lowestTeam.addPlayer(player);
        player.sendMessage("§aYou are joined in "+lowestTeam.getTeamType().getColor()+lowestTeam.getTeamType().getName());
        return true;
    }

    public boolean leave(Player player) {
        for (GameTeam team : teams) {
            if (team.getPlayers().contains(player)) {
                team.removePlayer(player);
                return true;
            }
        }
        return false;
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for (GameTeam team : teams) {
            players.addAll(team.getPlayers());
        }
        return players;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public Location getMapCenter() {
        return mapCenter;
    }

    public List<GameTeam> getTeams() {
        return teams;
    }

    public boolean isRunned() {
        return runned;
    }
}
