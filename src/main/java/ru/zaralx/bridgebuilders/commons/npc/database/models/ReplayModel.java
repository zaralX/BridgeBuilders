package ru.zaralx.bridgebuilders.commons.npc.database.models;

public class ReplayModel extends BaseModel {
    public ReplayModel() {
        name = "Replays";
        columns.add(new ModelColumn("id", "INTEGER", true, true, true));
        columns.add(new ModelColumn("name", "TEXT", true, false));
        columns.add(new ModelColumn("totalTicks", "BIGINT", true, false));
    }
}
