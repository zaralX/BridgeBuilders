package ru.zaralx.bridgebuilders.commons.npc;

import org.bukkit.entity.Player;
import ru.zaralx.bridgebuilders.commons.npc.record.TickRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReplayManager {
    private List<Replay> replays = new ArrayList<>();
    private List<ReplayInRecording> recordingReplays = new ArrayList<>();

    public void addReplay(Replay replay) {
        if (!replays.contains(replay)) {
            replays.add(replay);
        }
    }

    public void addRecordingReplay(ReplayInRecording replay) {
        this.recordingReplays.add(replay);
    }

    public void removeRecordingReplay(ReplayInRecording replay) {
        replay.getTask().cancel();
        this.recordingReplays.remove(replay);
    }

    public List<Replay> getReplays() {
        return replays;
    }

    public Replay getReplay(String name) {
        return replays.stream()
                .filter(replay -> replay.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Replay getReplay(String name, ReplayReflect reflect) {
        return replays.stream()
                .filter(replay -> replay.getName().equals(name))
                .filter(replay -> replay.getReplayReflect().equals(reflect))
                .findFirst()
                .orElse(null);
    }

    public ReplayInRecording getRecordingReplay(String name) {
        return recordingReplays.stream()
                .filter(replay -> replay.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<ReplayInRecording> getRecordingReplays(Player player) {
        return recordingReplays.stream()
                .filter(replay -> replay.getPlayer().equals(player))
                .collect(Collectors.toList());
    }
}
