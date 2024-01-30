package ru.zaralx.bridgebuilders.commons.npc;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import ru.zaralx.bridgebuilders.BridgeBuilders;
import ru.zaralx.bridgebuilders.commons.npc.record.TickRecord;

import java.util.List;

/*
 * Replay - Входная точка анимации NPC
 * При new Replay() инициализируется Task
 * для включения анимации!
 */
public class Replay {
    private final String name;
    private List<TickRecord> records;
    private Integer currentTick = 0;
    private Integer totalTicks = 0;
    private Boolean nowPlaying;
    private BukkitTask task;
    private final BaseNPC npc;

    public Replay(String name, List<TickRecord> records, Boolean nowPlaying) {
        this.name = name;
        this.records = records;
        this.totalTicks = records.size();
        this.nowPlaying = nowPlaying;

        this.npc = new BaseNPC(name, records.get(0).getPositionRecord().getLocation());

        task = Bukkit.getScheduler().runTaskTimer(BridgeBuilders.getInstance(), () -> {
            if (this.nowPlaying && totalTicks > currentTick+1) {
                currentTick++;
                records.get(currentTick).animate(npc);
            }
        }, 0, 1);
    }

    public void start() {
        nowPlaying = true;
    }

    public void pause() {
        nowPlaying = false;
    }

    public void stop() {
        nowPlaying = false;
        currentTick = 0;
    }

    public void restart() {
        currentTick = 0;
        nowPlaying = true;
    }

    public String getName() {
        return name;
    }

    public BaseNPC getNpc() {
        return npc;
    }
}
