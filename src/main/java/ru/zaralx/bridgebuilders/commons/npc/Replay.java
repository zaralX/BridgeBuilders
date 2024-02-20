package ru.zaralx.bridgebuilders.commons.npc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import ru.zaralx.bridgebuilders.BridgeBuilders;
import ru.zaralx.bridgebuilders.commons.npc.record.*;

import java.util.ArrayList;
import java.util.List;

/*
 * Replay - Входная точка анимации NPC
 * При new Replay() инициализируется Task
 * для включения анимации!
 */
public class Replay {
    private final String name;
    private final List<TickRecord> records;
    private Integer currentTick = 0;
    private Integer totalTicks = 0;
    private Boolean nowPlaying;
    private BukkitTask task;
    private final BaseNPC npc;
    private final ReplayReflect replayReflect;

    public Replay(String name, List<TickRecord> records, Boolean nowPlaying, ReplayReflect replayReflect) {
        this.name = name;
        this.records = records;
        this.totalTicks = records.size();
        this.nowPlaying = nowPlaying;
        this.replayReflect = replayReflect;

        this.npc = new BaseNPC(name, records.get(0).getPositionRecord().getLocation(), new Skin("ewogICJ0aW1lc3RhbXAiIDogMTYyNzMzMDg3NTQxNCwKICAicHJvZmlsZUlkIiA6ICJlZDUzZGQ4MTRmOWQ0YTNjYjRlYjY1MWRjYmE3N2U2NiIsCiAgInByb2ZpbGVOYW1lIiA6ICI0MTQxNDE0MWgiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJlYzY2ZGUyZTVhOGUyMDMwZWY0MDZkYzE0NTRhYmI5ZmQ4ZmJjNDE4NzYxYmEzM2VlZWM4ZWU5YjQ1ODg3ZCIKICAgIH0KICB9Cn0=", "t3dijDG0f3yWI5fsXOrDipZy/SSgpZ/CJYK6U3fl5ZuN9jW+3aeU0q3cm4KACXyhl1ouPQHDv1prdOodosqyonu8Ha8DlPBSEwKz6c19YGSBvUkPAaiJkjh6hh64y5mZvFwZirm9vCq7e86xLVCYUEXz/aMgGpjw+vvha9v3Lo5K17SiFq2E4t8QAtdYbxBfvXAfYnuqLWGMqsgvqWO4k/ZTZc3UfQZq1X79gUVIdILnzBuHB8Rc59xca8P47ZcQgdBrJbMN09eFXXHsyUMwMm0ez943Ym/fwtX62v29Zsu8nJtC1JSPibnh5L767zO0TJosrSdH0ncQbllgOrrXk4WSoDTDoDplLqyezncdJDhqlUgDXk7J5YFNNw91ISSYeHo60CTG6cGrBWYjtr6Vnq7CO7/Ntg8O/i4Q4UBG1qqU0lTMvPwfRAjdqGIzkaWxRUHlH45P3UnjH//tDFBPHPiS0XiaRX18Kt0V7VAriBA39e/FbIEVXlYqTvQTwPMLz55KYwMOzl5DD8EHrtCQkrcS6DywW+ZFtg3oYMlCcGmmlRktC40s8xzkJM7+vVroCPKRobCtwbRFI1bymBENxNFgw1JPS6/tKYsU/fDVmwWCDi2f3SddM1rz11XUSSzMhhVWDmGRZLR5r7R/7ZI7UrOFnp/I+Bn4RLQS0dfZirM="));
    }

    public Replay reflect(Location center, ReplayReflect reflect) {
        List<TickRecord> new_records = new ArrayList<>();
        for (TickRecord tick : records) {
            new_records.add(new TickRecord(
                    new PositionRecord(reflect.reflect(center, tick.getPositionRecord().getLocation().clone())),
                    tick.getEquipmentRecord(),
                    tick.getPoseRecord(),
                    tick.getBlockPlaceRecord() != null ? new BlockPlaceRecord(reflect.reflectBlock(center, tick.getBlockPlaceRecord().getLocation()), reflect.reflectBlockData(tick.getBlockPlaceRecord().getBlockData().clone()), tick.getBlockPlaceRecord().getHand()) : null,
                    tick.getBlockDestroyRecord() != null ? new BlockDestroyRecord(reflect.reflectBlock(center, tick.getBlockDestroyRecord().getLocation())) : null,
                    tick.getBlockInteractRecord() != null ? new BlockInteractRecord(reflect.reflectBlock(center, tick.getBlockInteractRecord().getLocation()), reflect.reflectBlockData(tick.getBlockInteractRecord().getBlockData().clone())) : null
            ));
        }
        return new Replay(name, new_records, nowPlaying, reflect);
    }

    public void next() {
        if (this.nowPlaying && totalTicks > currentTick+1) {
            currentTick++;
            records.get(currentTick).animate(npc);
        }
    }

    public TickRecord getNext() {
        if (totalTicks > currentTick+1) {
            return records.get(currentTick+1);
        }
        return null;
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

    public ReplayReflect getReplayReflect() {
        return replayReflect;
    }

    public List<TickRecord> getRecords() {
        return records;
    }

    public BukkitTask getTask() {
        return task;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }
}
