package ru.zaralx.bridgebuilders.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import ru.zaralx.bridgebuilders.BridgeBuilders;
import ru.zaralx.bridgebuilders.commons.npc.ReplayInRecording;
import ru.zaralx.bridgebuilders.commons.npc.record.BlockDestroyRecord;
import ru.zaralx.bridgebuilders.commons.npc.record.BlockPlaceRecord;

public class PlayerListener implements Listener {
    public PlayerListener() {
        Bukkit.getPluginManager().registerEvents(this, BridgeBuilders.getInstance());
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        // e.getBlockReplacedState() // Предыдущий блок
        // e.getBlock(); // Новый блок
        // e.getBlockAgainst(); // Блок на который ставим

        Player player = e.getPlayer();
        Block block = e.getBlock();

        for (ReplayInRecording replay : BridgeBuilders.getInstance().getReplayManager().getRecordingReplays(player)) {
            if (replay.isRecording())
                replay.updateLastTickRecord(replay.getLastTickRecord().setBlockPlaceRecord(
                        new BlockPlaceRecord(block.getLocation(), block.getBlockData(), e.getHand())
                ));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();

        for (ReplayInRecording replay : BridgeBuilders.getInstance().getReplayManager().getRecordingReplays(player)) {
            if (replay.isRecording())
                replay.updateLastTickRecord(replay.getLastTickRecord().setBlockDestroyRecord(
                        new BlockDestroyRecord(location)
                ));
        }
    }
}
