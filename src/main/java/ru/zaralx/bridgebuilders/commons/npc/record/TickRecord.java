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

    public TickRecord(PositionRecord positionRecord, EquipmentRecord equipmentRecord, PoseRecord poseRecord) {
        this.positionRecord = positionRecord;
        this.equipmentRecord = equipmentRecord;
        this.poseRecord = poseRecord;
    }

    public void animate(BaseNPC npc) {
        positionRecord.execute(npc);
        equipmentRecord.execute(npc);
        poseRecord.execute(npc);
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

    @Override
    public String toString() {
        return "TickRecord{" +
                "positionRecord=" + positionRecord +
                ", equipmentRecord=" + equipmentRecord +
                '}';
    }
}
