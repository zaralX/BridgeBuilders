package ru.zaralx.bridgebuilders.commons.game;

import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import ru.zaralx.bridgebuilders.BridgeBuilders;
import ru.zaralx.bridgebuilders.commons.npc.Replay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameTeam {
    private final Game game;
    private final Location respawnLocation;
    private final GameTeamType teamType;
    private final Replay replay;
    private List<Player> players = new ArrayList<>();
    private final BuildItems builderInventory = new BuildItems();
    private BukkitTask replayTask;

    public GameTeam(Game game, Location respawnLocation, GameTeamType teamType, List<Player> players, Replay replay) {
        this.game = game;
        this.respawnLocation = respawnLocation;
        this.teamType = teamType;
        this.players = players;
        this.replay = replay;
    }

    public GameTeam(Game game, Location respawnLocation, GameTeamType teamType, Replay replay) {
        this.game = game;
        this.respawnLocation = respawnLocation;
        this.teamType = teamType;
        this.replay = replay;
    }

    public void initTask() {
        replayTask = Bukkit.getScheduler().runTaskTimer(BridgeBuilders.getInstance(), () -> {
            if (replay.getNext() == null) return;
            if (replay.getNext().getBlockPlaceRecord() != null) {
                Location searchLoc = replay.getNext().getBlockPlaceRecord().getLocation();
                Material material = null;
                for (Location location : game.getBuildItems().getEndLocations().keySet()) {
                    if (location.getBlockX() == searchLoc.getBlockX() && location.getBlockY() == searchLoc.getBlockY() && location.getBlockZ() == searchLoc.getBlockZ()) {
                        material = game.getBuildItems().getEndLocations().get(location);
                    }
                }
                if (material == null) return;
                if (material.equals(
                        replay.getNext().getBlockPlaceRecord().getBlockData().getMaterial()
                )) {
                    if (builderInventory.getItems().isEmpty()) return;
                    for (BuildItems.BuildItem item : builderInventory.getItems()){
                        if (item.material.equals(replay.getNext().getBlockPlaceRecord().getBlockData().getMaterial())) {
                            if (item.count < 1) {
                                return;
                            } else {
                                item.remove();
                                break;
                            }
                        }
                    }
                }
            }
            replay.next();
        }, 0, 1);
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
