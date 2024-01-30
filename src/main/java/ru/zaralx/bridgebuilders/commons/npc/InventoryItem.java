package ru.zaralx.bridgebuilders.commons.npc;

import org.bukkit.Material;

public class InventoryItem {
    private final Material material;
    private final Integer count;

    public InventoryItem(Material material, Integer count) {
        this.material = material;
        this.count = count;
    }

    public Material getMaterial() {
        return material;
    }

    public Integer getCount() {
        return count;
    }
}
