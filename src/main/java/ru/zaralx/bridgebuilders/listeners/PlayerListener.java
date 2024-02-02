package ru.zaralx.bridgebuilders.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import ru.zaralx.bridgebuilders.BridgeBuilders;
import ru.zaralx.bridgebuilders.commons.npc.Replay;
import ru.zaralx.bridgebuilders.commons.npc.ReplayInRecording;
import ru.zaralx.bridgebuilders.commons.npc.record.BlockDestroyRecord;
import ru.zaralx.bridgebuilders.commons.npc.record.BlockInteractRecord;
import ru.zaralx.bridgebuilders.commons.npc.record.BlockPlaceRecord;

public class PlayerListener implements Listener {
    public PlayerListener() {
        Bukkit.getPluginManager().registerEvents(this, BridgeBuilders.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        for (Replay replay : BridgeBuilders.getInstance().getReplayManager().getReplays()) {
            replay.getNpc().init(event.getPlayer());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        for (Replay replay : BridgeBuilders.getInstance().getReplayManager().getReplays()) {
            replay.getNpc().removeShows(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        // event.getBlockReplacedState() // Предыдущий блок
        // event.getBlock(); // Новый блок
        // event.getBlockAgainst(); // Блок на который ставим

        Player player = event.getPlayer();
        Block block = event.getBlock();

        for (ReplayInRecording replay : BridgeBuilders.getInstance().getReplayManager().getRecordingReplays(player)) {
            if (replay.isRecording())
                replay.updateLastTickRecord(replay.getLastTickRecord().setBlockPlaceRecord(
                        new BlockPlaceRecord(block.getLocation(), block.getBlockData(), event.getHand())
                ));
        }
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType().equals(Material.AIR)) {
            for (ReplayInRecording replay : BridgeBuilders.getInstance().getReplayManager().getRecordingReplays(player)) {
                if (replay.isRecording())
                    if (event.getBucket().equals(Material.WATER_BUCKET))
                        replay.updateLastTickRecord(replay.getLastTickRecord().setBlockPlaceRecord(
                                new BlockPlaceRecord(block.getLocation(), Bukkit.createBlockData(Material.WATER), event.getHand())
                        ));
                    else if (event.getBucket().equals(Material.LAVA_BUCKET))
                        replay.updateLastTickRecord(replay.getLastTickRecord().setBlockPlaceRecord(
                                new BlockPlaceRecord(block.getLocation(), Bukkit.createBlockData(Material.LAVA), event.getHand())
                        ));
            }
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType().equals(Material.WATER) || block.getType().equals(Material.LAVA)) {
            for (ReplayInRecording replay : BridgeBuilders.getInstance().getReplayManager().getRecordingReplays(player)) {
                if (replay.isRecording())
                    replay.updateLastTickRecord(replay.getLastTickRecord().setBlockPlaceRecord(
                            new BlockPlaceRecord(block.getLocation(), Bukkit.createBlockData(Material.AIR), event.getHand())
                    ));
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        for (ReplayInRecording replay : BridgeBuilders.getInstance().getReplayManager().getRecordingReplays(player)) {
            if (replay.isRecording())
                replay.updateLastTickRecord(replay.getLastTickRecord().setBlockDestroyRecord(
                        new BlockDestroyRecord(location)
                ));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block != null) {
            BlockData previusBlockData = block.getBlockData().clone();
            for (ReplayInRecording replay : BridgeBuilders.getInstance().getReplayManager().getRecordingReplays(player)) {
                if (replay.isRecording())
                    Bukkit.getScheduler().runTaskLater(BridgeBuilders.getInstance(), () -> {
                        if (!previusBlockData.equals(block.getBlockData())) {
                            replay.updateLastTickRecord(replay.getLastTickRecord().setBlockInteractRecord(
                                    new BlockInteractRecord(block.getLocation(), block.getBlockData())
                            ));
                        }
                    }, 1);
            }
        }
    }
}
