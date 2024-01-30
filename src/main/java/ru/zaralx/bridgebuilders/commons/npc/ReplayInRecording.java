package ru.zaralx.bridgebuilders.commons.npc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import ru.zaralx.bridgebuilders.BridgeBuilders;
import ru.zaralx.bridgebuilders.commons.npc.record.EquipmentRecord;
import ru.zaralx.bridgebuilders.commons.npc.record.PoseRecord;
import ru.zaralx.bridgebuilders.commons.npc.record.PositionRecord;
import ru.zaralx.bridgebuilders.commons.npc.record.TickRecord;

import java.util.ArrayList;
import java.util.List;

/*
 * Replay - Входная точка анимации NPC
 * При new Replay() инициализируется Task
 * для включения анимации!
 */
public class ReplayInRecording {
    private String name;
    private List<TickRecord> records = new ArrayList<>();
    private Integer totalTicks = 0;
    private Boolean isRecording;
    private BukkitTask task;
    private Player player;

    public ReplayInRecording(String name, Boolean isRecording, Player player) {
        this.name = name;
        this.isRecording = isRecording;
        this.player = player;

        task = Bukkit.getScheduler().runTaskTimer(BridgeBuilders.getInstance(), () -> {
            if (isRecording) {
                records.add(new TickRecord(new PositionRecord(player.getLocation()), new EquipmentRecord(player.getEquipment()), new PoseRecord(player.getPose())));
                totalTicks++;
            }
        }, 0, 1);
    }

    public void start() {
        isRecording = true;
    }

    public void pause() {
        isRecording = false;
    }

    public void stop() {
        isRecording = false;
    }

    public void restart() {
        totalTicks = 0;
        records = new ArrayList<>();
        isRecording = true;
    }

    public String getName() {
        return name;
    }

    public List<TickRecord> getRecords() {
        return records;
    }

    public Integer getTotalTicks() {
        return totalTicks;
    }

    public Boolean getRecording() {
        return isRecording;
    }

    public BukkitTask getTask() {
        return task;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "ReplayInRecording{" +
                "name='" + name + '\'' +
                ", records=" + records +
                ", totalTicks=" + totalTicks +
                ", isRecording=" + isRecording +
                ", task=" + task +
                ", player=" + player +
                '}';
    }
}
