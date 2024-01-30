package ru.zaralx.bridgebuilders.commons.npc.record;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Pose;
import ru.zaralx.bridgebuilders.commons.npc.BaseNPC;
import ru.zaralx.bridgebuilders.utils.NmsConverters;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PoseRecord extends BaseRecord {
    private final Pose pose;

    public PoseRecord(Pose pose) {
        this.pose = pose;
    }

    public PoseRecord(org.bukkit.entity.Pose pose) {
        this.pose = NmsConverters.convertPoseBukkitToNms(pose);
    }

    @Override
    public void execute(BaseNPC npc) {
        super.execute(npc);
        SynchedEntityData data = npc.getNpc().getEntityData();
        data.set(EntityDataSerializers.POSE.createAccessor(6),pose);

        List<SynchedEntityData.DataValue<?>> l = new ArrayList<>();
        try {
            Field field = data.getClass().getDeclaredField("e");
            field.setAccessible(true);
            Int2ObjectMap<SynchedEntityData.DataItem<?>> map = (Int2ObjectMap<SynchedEntityData.DataItem<?>>) field.get(data);
            for(SynchedEntityData.DataItem<?> item : map.values())
                l.add(item.value());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        packetListener.send(new ClientboundSetEntityDataPacket(npc.getNpc().getId(),l));

        // TODO: OPTIMIZE PACKETS
        SynchedEntityData watcher = new SynchedEntityData(npc.getServerPlayer());
        watcher.define(new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), (byte) 127);

        l = new ArrayList<>();
        try {
            Field field = watcher.getClass().getDeclaredField("e");
            field.setAccessible(true);
            Int2ObjectMap<SynchedEntityData.DataItem<?>> map = (Int2ObjectMap<SynchedEntityData.DataItem<?>>) field.get(watcher);
            for(SynchedEntityData.DataItem<?> item : map.values())
                l.add(item.value());
        } catch (Exception e) {}

        packetListener.send(new ClientboundSetEntityDataPacket(npc.getNpc().getId(), l));
    }

    public Pose getPose() {
        return pose;
    }

    @Override
    public String toString() {
        return "PoseRecord{" +
                "pose=" + pose +
                '}';
    }
}
