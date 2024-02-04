package ru.zaralx.bridgebuilders.commons.npc.record;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Fence;
import org.bukkit.inventory.EquipmentSlot;
import ru.zaralx.bridgebuilders.BridgeBuilders;
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

        // TODO fix fence connections
        Block block = location.getBlock();
        if (blockData instanceof Fence) {
            block.setType(blockData.getMaterial());
        } else {
            block.setBlockData(blockData);
        }

        if (blockData instanceof Door doorData) {
            Block topBlock = block.getRelative(0, 1, 0);
            BlockData topBlockData = Bukkit.createBlockData(block.getType());
            ((Door) topBlockData).setHalf(Bisected.Half.TOP);
            ((Door) topBlockData).setHinge(doorData.getHinge());
            topBlock.setBlockData(topBlockData);
        }

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
