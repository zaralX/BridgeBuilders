package ru.zaralx.bridgebuilders.commons.npc.record;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import ru.zaralx.bridgebuilders.commons.npc.BaseNPC;

public class BlockDestroyRecord extends BaseRecord {
    private final Location location;

    public BlockDestroyRecord(Location location) {
        this.location = location;
    }

    @Override
    public void execute(BaseNPC npc) {
        super.execute(npc);
        Block block = location.getBlock();
        if (block.getType().equals(Material.AIR)) {
            return;
        }

        npc.swingRightArm();
        location.getWorld().playSound(location, block.getBlockData().getSoundGroup().getBreakSound(), 1F, 0.8F);
        location.getWorld().spawnParticle(Particle.ITEM_CRACK, location.clone().add(0.5,0.5,0.5), 10, 0.33, 0.25, 0.25, 0.05, new ItemStack(block.getBlockData().getMaterial()));
        block.setType(Material.AIR);
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "BlockDestroyRecord{" +
                "location=" + location +
                '}';
    }
}
