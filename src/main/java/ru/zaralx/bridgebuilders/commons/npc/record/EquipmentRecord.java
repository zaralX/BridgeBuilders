package ru.zaralx.bridgebuilders.commons.npc.record;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.EntityEquipment;
import ru.zaralx.bridgebuilders.commons.npc.BaseNPC;
import ru.zaralx.bridgebuilders.commons.npc.CustomEquipment;

import java.util.ArrayList;
import java.util.List;

public class EquipmentRecord extends BaseRecord {
    private final CustomEquipment equipment;

    public EquipmentRecord(EntityEquipment equipment) {
        this.equipment = new CustomEquipment(equipment);
    }

    public EquipmentRecord(CustomEquipment equipment) {
        this.equipment = equipment;
    }

    @Override
    public void execute(BaseNPC npc) {
        super.execute(npc);
        packetListener.send(new ClientboundSetEquipmentPacket(npc.getNpc().getBukkitEntity().getEntityId(), buildEquipment()));
    }

    public List<Pair<EquipmentSlot, ItemStack>> buildEquipment() {
        List<Pair<EquipmentSlot, ItemStack>> list = new ArrayList<>();

        list.add(new Pair<>(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(equipment.HAND)));
        list.add(new Pair<>(EquipmentSlot.OFFHAND, CraftItemStack.asNMSCopy(equipment.OFF_HAND)));
        list.add(new Pair<>(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(equipment.HELMET)));
        list.add(new Pair<>(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(equipment.CHESTPLATE)));
        list.add(new Pair<>(EquipmentSlot.LEGS, CraftItemStack.asNMSCopy(equipment.LEGGINGS)));
        list.add(new Pair<>(EquipmentSlot.FEET, CraftItemStack.asNMSCopy(equipment.BOOTS)));

        return list;
    }

    public org.bukkit.inventory.ItemStack getHand() {
        return equipment.HAND;
    }

    public org.bukkit.inventory.ItemStack getOffHand() {
        return equipment.OFF_HAND;
    }

    public org.bukkit.inventory.ItemStack getHelmet() {
        if (equipment.HELMET == null) return new org.bukkit.inventory.ItemStack(Material.AIR);
        return equipment.HELMET;
    }

    public org.bukkit.inventory.ItemStack getChestplate() {
        if (equipment.CHESTPLATE == null) return new org.bukkit.inventory.ItemStack(Material.AIR);
        return equipment.CHESTPLATE;
    }

    public org.bukkit.inventory.ItemStack getLeggings() {
        if (equipment.LEGGINGS == null) return new org.bukkit.inventory.ItemStack(Material.AIR);
        return equipment.LEGGINGS;
    }

    public org.bukkit.inventory.ItemStack getBoots() {
        if (equipment.BOOTS == null) return new org.bukkit.inventory.ItemStack(Material.AIR);
        return equipment.BOOTS;
    }

    @Override
    public String toString() {
        return "EquipmentRecord{" +
                "equipment=" + equipment +
                '}';
    }
}
