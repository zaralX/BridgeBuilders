package ru.zaralx.bridgebuilders.commons.npc.record;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.material.Cauldron;
import ru.zaralx.bridgebuilders.commons.npc.BaseNPC;

public class BlockInteractRecord extends BaseRecord {
    private final Location location;
    private final BlockData blockData;

    public BlockInteractRecord(Location location, BlockData blockData) {
        this.location = location;
        this.blockData = blockData;
    }

    @Override
    public void execute(BaseNPC npc) {
        super.execute(npc);
        npc.swingRightArm();
        location.getBlock().setBlockData(blockData);
        location.getWorld().playSound(location, getSound(blockData), 1F, 0.8F);
    }

    private Sound getSound(BlockData blockData) {
        if (blockData instanceof Cauldron) {
            if (((Cauldron) blockData).isFull()) return Sound.ITEM_BUCKET_FILL;
            if (((Cauldron) blockData).isEmpty()) return Sound.ITEM_BUCKET_EMPTY;
        } else if (blockData instanceof TrapDoor) {
            if (!((TrapDoor) location.getBlock().getBlockData()).isOpen() && ((TrapDoor) blockData).isOpen()) return Sound.BLOCK_WOODEN_TRAPDOOR_OPEN;
            if (((TrapDoor) location.getBlock().getBlockData()).isOpen() && !((TrapDoor) blockData).isOpen()) return Sound.BLOCK_WOODEN_TRAPDOOR_CLOSE;
        } else if (blockData instanceof Door) {
            if (!((Door) location.getBlock().getBlockData()).isOpen() && ((Door) blockData).isOpen()) return Sound.BLOCK_WOODEN_DOOR_OPEN;
            if (((Door) location.getBlock().getBlockData()).isOpen() && !((Door) blockData).isOpen()) return Sound.BLOCK_WOODEN_DOOR_CLOSE;
        }
        return Sound.BLOCK_STONE_PLACE;
    }

    public Location getLocation() {
        return location;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    @Override
    public String toString() {
        return "BlockPlaceRecord{" +
                "location=" + location +
                ", blockData=" + blockData +
                '}';
    }
}
