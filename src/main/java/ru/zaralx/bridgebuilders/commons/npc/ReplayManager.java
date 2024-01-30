package ru.zaralx.bridgebuilders.commons.npc;

import java.util.ArrayList;
import java.util.List;

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

    public Replay getReplay(String name) {
        return replays.stream()
                .filter(replay -> replay.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public ReplayInRecording getRecordingReplay(String name) {
        return recordingReplays.stream()
                .filter(replay -> replay.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
