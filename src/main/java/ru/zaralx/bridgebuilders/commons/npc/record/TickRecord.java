package ru.zaralx.bridgebuilders.commons.npc.record;

import ru.zaralx.bridgebuilders.commons.npc.BaseNPC;

/*
 * TickRecord - Вторая точка анимации NPC (После Replay)
 * В этот класс записываются все действия
 * игрока, а так же вызывается воспроизведение
 * всех записей о анимации этого тика
 */
public class TickRecord {
    private final PositionRecord positionRecord;
    private final EquipmentRecord equipmentRecord;
    private final PoseRecord poseRecord;
    private BlockPlaceRecord blockPlaceRecord;
    private BlockDestroyRecord blockDestroyRecord;

    public TickRecord(PositionRecord positionRecord, EquipmentRecord equipmentRecord, PoseRecord poseRecord) {
        this.positionRecord = positionRecord;
        this.equipmentRecord = equipmentRecord;
        this.poseRecord = poseRecord;
    }

    public TickRecord(PositionRecord positionRecord, EquipmentRecord equipmentRecord, PoseRecord poseRecord, BlockPlaceRecord blockPlaceRecord, BlockDestroyRecord blockDestroyRecord) {
        this.positionRecord = positionRecord;
        this.equipmentRecord = equipmentRecord;
        this.poseRecord = poseRecord;
        this.blockPlaceRecord = blockPlaceRecord;
        this.blockDestroyRecord = blockDestroyRecord;
    }

    public void animate(BaseNPC npc) {
        positionRecord.execute(npc);
        equipmentRecord.execute(npc);
        poseRecord.execute(npc);
        if (blockPlaceRecord != null)
            blockPlaceRecord.execute(npc);
        if (blockDestroyRecord != null)
            blockDestroyRecord.execute(npc);
    }

    public PositionRecord getPositionRecord() {
        return positionRecord;
    }

    public EquipmentRecord getEquipmentRecord() {
        return equipmentRecord;
    }

    public PoseRecord getPoseRecord() {
        return poseRecord;
    }

    public BlockPlaceRecord getBlockPlaceRecord() {
        return blockPlaceRecord;
    }

    public BlockDestroyRecord getBlockDestroyRecord() {
        return blockDestroyRecord;
    }

    public TickRecord setBlockPlaceRecord(BlockPlaceRecord blockPlaceRecord) {
        this.blockPlaceRecord = blockPlaceRecord;
        return this;
    }

    public TickRecord setBlockDestroyRecord(BlockDestroyRecord blockDestroyRecord) {
        this.blockDestroyRecord = blockDestroyRecord;
        return this;
    }

    @Override
    public String toString() {
        return "TickRecord{" +
                "positionRecord=" + positionRecord +
                ", equipmentRecord=" + equipmentRecord +
                ", poseRecord=" + poseRecord +
                ", blockPlaceRecord=" + blockPlaceRecord +
                ", blockDestroyRecord=" + blockDestroyRecord +
                '}';
    }
}
