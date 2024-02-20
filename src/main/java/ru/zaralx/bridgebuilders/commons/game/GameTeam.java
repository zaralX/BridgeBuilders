package ru.zaralx.bridgebuilders.commons.game;

import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import ru.zaralx.bridgebuilders.BridgeBuilders;
import ru.zaralx.bridgebuilders.commons.npc.Replay;

import java.util.ArrayList;
import java.util.List;

public class GameTeam {
    private final Location respawnLocation;
    private final GameTeamType teamType;
    private final Replay replay;
    private List<Player> players = new ArrayList<>();
    private BuildItems builderInventory;
    private BukkitTask replayTask;

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

    private void initTask() {
        replayTask = Bukkit.getScheduler().runTaskTimer(BridgeBuilders.getInstance(), () -> {
            if (replay.getNext() == null) return;

            replay.getNext();
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
