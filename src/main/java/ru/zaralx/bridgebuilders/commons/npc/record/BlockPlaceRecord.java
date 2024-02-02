package ru.zaralx.bridgebuilders.commons.npc.record;

import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import ru.zaralx.bridgebuilders.commons.npc.BaseNPC;

public class BlockPlaceRecord extends BaseRecord {
    private final Location location;
    private final BlockData blockData;
    private final int hand;

    public BlockPlaceRecord(Location location, BlockData blockData, int hand) {
        this.location = location;
        this.blockData = blockData;
        this.hand = hand;
    }

    public BlockPlaceRecord(Location location, BlockData blockData, EquipmentSlot slot) {
        this.location = location;
        this.blockData = blockData;
        if (slot.equals(EquipmentSlot.OFF_HAND))
            this.hand = 0;
        else
            this.hand = 1;
    }

    @Override
    public void execute(BaseNPC npc) {
        super.execute(npc);
        if (hand == 0) {
            npc.swingLeftArm();
        } else if (hand == 1) {
            npc.swingRightArm();
        }
        location.getBlock().setBlockData(blockData);
        location.getWorld().playSound(location, blockData.getSoundGroup().getPlaceSound(), 1F, 0.8F);
    }

    public Location getLocation() {
        return location;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public int getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return "BlockPlaceRecord{" +
                "location=" + location +
                ", blockData=" + blockData +
                '}';
    }
}
