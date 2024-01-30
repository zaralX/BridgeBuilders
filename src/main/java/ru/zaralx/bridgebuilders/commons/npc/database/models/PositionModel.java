package ru.zaralx.bridgebuilders.commons.npc.database.models;

public class PositionModel extends BaseModel {
    public PositionModel() {
        name = "Replay_Tick_Position";
        columns.add(new ModelColumn("id", "INTEGER", true, true, true));
        columns.add(new ModelColumn("replay", "INTEGER", true, false));
        columns.add(new ModelColumn("x", "DOUBLE", true, false));
        columns.add(new ModelColumn("y", "DOUBLE", true, false));
        columns.add(new ModelColumn("z", "DOUBLE", true, false));
        columns.add(new ModelColumn("yaw", "FLOAT", true, false));
        columns.add(new ModelColumn("pitch", "FLOAT", true, false));
    }
}
