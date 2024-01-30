package ru.zaralx.bridgebuilders.commons.npc.database.models;

public class PoseModel extends BaseModel {
    public PoseModel() {
        name = "Replay_Tick_Pose";
        columns.add(new ModelColumn("id", "INTEGER", true, true, true));
        columns.add(new ModelColumn("replay", "INTEGER", true, false));
        columns.add(new ModelColumn("pose", "TEXT", false, false));
    }
}
