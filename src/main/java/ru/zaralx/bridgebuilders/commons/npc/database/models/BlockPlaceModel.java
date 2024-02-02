package ru.zaralx.bridgebuilders.commons.npc.database.models;

public class BlockPlaceModel extends BaseModel {
    public BlockPlaceModel() {
        name = "Replay_Tick_Block_Place";
        columns.add(new ModelColumn("id", "INTEGER", true, true, true));
        columns.add(new ModelColumn("replay", "INTEGER", true, false));
        columns.add(new ModelColumn("x", "DOUBLE", true, false));
        columns.add(new ModelColumn("y", "DOUBLE", true, false));
        columns.add(new ModelColumn("z", "DOUBLE", true, false));
        columns.add(new ModelColumn("block_data", "TEXT", true, false));
        columns.add(new ModelColumn("hand", "INTEGER", true, false));
    }
}
