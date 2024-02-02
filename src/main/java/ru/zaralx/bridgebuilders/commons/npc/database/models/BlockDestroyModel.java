package ru.zaralx.bridgebuilders.commons.npc.database.models;

public class BlockDestroyModel extends BaseModel {
    public BlockDestroyModel() {
        name = "Replay_Tick_Block_Destroy";
        columns.add(new ModelColumn("id", "INTEGER", true, true, true));
        columns.add(new ModelColumn("replay", "INTEGER", true, false));
        columns.add(new ModelColumn("x", "DOUBLE", true, false));
        columns.add(new ModelColumn("y", "DOUBLE", true, false));
        columns.add(new ModelColumn("z", "DOUBLE", true, false));
    }
}
