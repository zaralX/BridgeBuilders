package ru.zaralx.bridgebuilders.commons.npc.database.models;

public class BlockInteractModel extends BaseModel {
    public BlockInteractModel() {
        name = "Replay_Tick_Block_Interact";
        columns.add(new ModelColumn("id", "INTEGER", true, true, true));
        columns.add(new ModelColumn("replay", "INTEGER", true, false));
        columns.add(new ModelColumn("x", "DOUBLE", true, false));
        columns.add(new ModelColumn("y", "DOUBLE", true, false));
        columns.add(new ModelColumn("z", "DOUBLE", true, false));
        columns.add(new ModelColumn("new_block_data", "TEXT", true, false));
    }
}
