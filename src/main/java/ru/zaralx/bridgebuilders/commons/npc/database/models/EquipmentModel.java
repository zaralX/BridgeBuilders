package ru.zaralx.bridgebuilders.commons.npc.database.models;

public class EquipmentModel extends BaseModel {
    public EquipmentModel() {
        name = "Replay_Tick_Equipment";
        columns.add(new ModelColumn("id", "INTEGER", true, true, true));
        columns.add(new ModelColumn("replay", "INTEGER", true, false));
        columns.add(new ModelColumn("HAND_material", "TEXT", false, false));
        columns.add(new ModelColumn("OFF_HAND_material", "TEXT", false, false));
        columns.add(new ModelColumn("HELMET_material", "TEXT", false, false));
        columns.add(new ModelColumn("CHESTPLATE_material", "TEXT", false, false));
        columns.add(new ModelColumn("LEGGINGS_material", "TEXT", false, false));
        columns.add(new ModelColumn("BOOTS_material", "TEXT", false, false));
    }
}
