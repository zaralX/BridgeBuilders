package ru.zaralx.bridgebuilders.commons.npc.database.models;

public class TickModel extends BaseModel {
    public TickModel() {
        name = "Replay_Tick";
        columns.add(new ModelColumn("id", "INTEGER", true, true, true));
        columns.add(new ModelColumn("replay", "INTEGER", true, false));
        columns.add(new ModelColumn("position", "BIGINT", true, false));
        columns.add(new ModelColumn("equipment", "BIGINT", true, false));
        columns.add(new ModelColumn("pose", "BIGINT", true, false));
        columns.add(new ModelColumn("place_block", "BIGINT", true, false));
    }
}
