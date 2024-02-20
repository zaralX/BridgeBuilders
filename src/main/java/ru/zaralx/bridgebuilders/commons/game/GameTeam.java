package ru.zaralx.bridgebuilders.commons.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.zaralx.bridgebuilders.commons.npc.Replay;

import java.util.ArrayList;
import java.util.List;

public class GameTeam {
    private Location respawnLocation;
    private GameTeamType teamType;
    private List<Player> players = new ArrayList<>();
    private Replay replay;

    public GameTeam(Location respawnLocation, GameTeamType teamType, List<Player> players, Replay replay) {
        this.respawnLocation = respawnLocation;
        this.teamType = teamType;
        this.players = players;
        this.replay = replay;
    }

    public GameTeam(Location respawnLocation, GameTeamType teamType, Replay replay) {
        this.respawnLocation = respawnLocation;
        this.teamType = teamType;
        this.replay = replay;
    }

    public Location getRespawnLocation() {
        return respawnLocation;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Replay getReplay() {
        return replay;
    }

    public GameTeamType getTeamType() {
        return teamType;
    }
}
