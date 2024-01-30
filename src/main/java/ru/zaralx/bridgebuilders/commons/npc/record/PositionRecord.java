package ru.zaralx.bridgebuilders.commons.npc.record;

import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Location;
import org.bukkit.Particle;
import ru.zaralx.bridgebuilders.commons.npc.BaseNPC;

public class PositionRecord extends BaseRecord {
    private final Location location;

    public PositionRecord(Location location) {
        this.location = location;
    }

    @Override
    public void execute(BaseNPC npc) {
        super.execute(npc);
        // Set Yaw
        packetListener.send(new ClientboundRotateHeadPacket(npc.getNpc(), (byte) ((location.getYaw()%360)*256/360)));

        // Set Position And Rotation
        packetListener.send(new ClientboundMoveEntityPacket.PosRot(
                npc.getNpc().getBukkitEntity().getEntityId(),
                (short) ((location.getX() * 32 - npc.getLocation().getX() * 32) * 128),
                (short) ((location.getY() * 32 - npc.getLocation().getY() * 32) * 128),
                (short) ((location.getZ() * 32 - npc.getLocation().getZ() * 32) * 128),
                (byte) ((location.getYaw()%360)*256/360),
                (byte) ((location.getPitch()%360)*256/360),
                true));
        npc.setLocation(location);
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "PositionRecord{" +
                "location=" + location +
                '}';
    }
}
