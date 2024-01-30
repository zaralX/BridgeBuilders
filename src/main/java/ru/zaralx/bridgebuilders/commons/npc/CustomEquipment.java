package ru.zaralx.bridgebuilders.commons.npc;

import org.bukkit.Material;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class CustomEquipment {
    public ItemStack HAND;
    public ItemStack OFF_HAND;
    public ItemStack HELMET;
    public ItemStack CHESTPLATE;
    public ItemStack LEGGINGS;
    public ItemStack BOOTS;

    public CustomEquipment(Material HAND, Material OFF_HAND, Material HELMET, Material CHESTPLATE, Material LEGGINGS, Material BOOTS) {
        this.HAND = new org.bukkit.inventory.ItemStack(HAND);
        this.OFF_HAND = new org.bukkit.inventory.ItemStack(OFF_HAND);
        this.HELMET = new org.bukkit.inventory.ItemStack(HELMET);
        this.CHESTPLATE = new org.bukkit.inventory.ItemStack(CHESTPLATE);
        this.LEGGINGS = new org.bukkit.inventory.ItemStack(LEGGINGS);
        this.BOOTS = new org.bukkit.inventory.ItemStack(BOOTS);
    }

    public CustomEquipment(String HAND, String OFF_HAND, String HELMET, String CHESTPLATE, String LEGGINGS, String BOOTS) {
        this.HAND = new org.bukkit.inventory.ItemStack(Material.getMaterial(HAND));
        this.OFF_HAND = new org.bukkit.inventory.ItemStack(Material.getMaterial(OFF_HAND));
        this.HELMET = new org.bukkit.inventory.ItemStack(Material.getMaterial(HELMET));
        this.CHESTPLATE = new org.bukkit.inventory.ItemStack(Material.getMaterial(CHESTPLATE));
        this.LEGGINGS = new org.bukkit.inventory.ItemStack(Material.getMaterial(LEGGINGS));
        this.BOOTS = new org.bukkit.inventory.ItemStack(Material.getMaterial(BOOTS));
    }

    public CustomEquipment(EntityEquipment eq) {
        this.HAND = eq.getItemInMainHand().clone();
        this.OFF_HAND = eq.getItemInOffHand().clone();
        if (eq.getArmorContents().length >= 4) {
            if (eq.getArmorContents()[3] != null) {
                this.HELMET = eq.getArmorContents()[3].clone();
            }
        } if (eq.getArmorContents().length >= 3) {
            if (eq.getArmorContents()[2] != null) {
                this.CHESTPLATE = eq.getArmorContents()[2].clone();
            }
        } if (eq.getArmorContents().length >= 2) {
            if (eq.getArmorContents()[1] != null) {
                this.LEGGINGS = eq.getArmorContents()[1].clone();
            }
        } if (eq.getArmorContents().length >= 1) {
            if (eq.getArmorContents()[0] != null) {
                this.BOOTS = eq.getArmorContents()[0].clone();
            }
        }


    }

    @Override
    public String toString() {
        return "CustomEquipment{" +
                "HAND=" + HAND +
                ", OFF_HAND=" + OFF_HAND +
                ", HELMET=" + HELMET +
                ", CHESTPLATE=" + CHESTPLATE +
                ", LEGGINGS=" + LEGGINGS +
                ", BOOTS=" + BOOTS +
                '}';
    }
}
