package ru.zaralx.bridgebuilders.commons.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaseNPC {
    private Location location;
    private String displayName;
    private Skin skin;
    private ItemStack item;
    private ServerPlayer serverPlayer;
    private ServerPlayer npc;
    private Pose pose = Pose.STANDING;
    private ServerGamePacketListenerImpl packetListener;
    private List<BukkitTask> bukkitTasks = new ArrayList<>();
    private final List<Player> showsFor = new ArrayList<>();

    public BaseNPC(String displayName, Location location) {
        this.location = location;
        this.displayName = displayName;
    }

    public BaseNPC(String displayName, Location location, Skin skin) {
        this.location = location;
        this.displayName = displayName;
        this.skin = skin;
    }

    public void initForAllOnline(boolean ignoreShows) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!ignoreShows && !showsFor.contains(player)) continue;
            init(player);
            showsFor.add(player);
        }
    }

    public void removeShows(Player player) {
        showsFor.remove(player);
    }

    public void init(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        this.serverPlayer = craftPlayer.getHandle();
        MinecraftServer server = serverPlayer.getServer();
        ServerLevel level = serverPlayer.getLevel();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), displayName);
        if (this.skin != null) {
            if (!this.skin.getSignature().isEmpty())
                gameProfile.getProperties().put("textures", new Property("textures", skin.getTexture(), skin.getSignature()));
            else
                gameProfile.getProperties().put("textures", new Property("textures", skin.getTexture()));
        }

        this.npc = new ServerPlayer(server, level, gameProfile);
        if (this.location != null) {
            npc.setPos(location.getX(), location.getY(), location.getZ());
        }

        this.packetListener = serverPlayer.connection;

        // Player Info Packet
        packetListener.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, npc));

        // Spawn Packet
        packetListener.send(new ClientboundAddPlayerPacket(npc));

        // Second Skin Layer
        SynchedEntityData watcher = new SynchedEntityData(serverPlayer);
        watcher.define(new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), (byte) 127);

        List<SynchedEntityData.DataValue<?>> l = new ArrayList<>();
        try {
            Field field = watcher.getClass().getDeclaredField("e");
            field.setAccessible(true);
            Int2ObjectMap<SynchedEntityData.DataItem<?>> map = (Int2ObjectMap<SynchedEntityData.DataItem<?>>) field.get(watcher);
            for(SynchedEntityData.DataItem<?> item : map.values())
                l.add(item.value());
        } catch (Exception e) {}

        packetListener.send(new ClientboundSetEntityDataPacket(npc.getId(), l));
    }

    public void swingRightArm() {
        packetListener.send(new ClientboundAnimatePacket(npc, 0));
    }

    public void leaveBed() {
        packetListener.send(new ClientboundAnimatePacket(npc, 2));
    }

    public void swingLeftArm() {
        packetListener.send(new ClientboundAnimatePacket(npc, 3));
    }

    public void criticalEffect() {
        packetListener.send(new ClientboundAnimatePacket(npc, 4));
    }

    public void magicCriticalEffect() {
        packetListener.send(new ClientboundAnimatePacket(npc, 5));
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void addBukkitRunnable(BukkitTask runnable) {
        this.bukkitTasks.add(runnable);
    }

    public void removeBukkitTask(BukkitTask runnable) {
        if (this.bukkitTasks.remove(runnable)) {
            runnable.cancel();
        }
    }

    public void delete() {
        // Despawn Packet
        packetListener.send(new ClientboundRemoveEntitiesPacket(npc.getBukkitEntity().getEntityId()));
        for (BukkitTask task : bukkitTasks) {
            removeBukkitTask(task);
        }
    }

    public ServerGamePacketListenerImpl getPacketListener() {
        return packetListener;
    }

    public ServerPlayer getNpc() {
        return npc;
    }

    public Location getLocation() {
        return location;
    }

    public ServerPlayer getServerPlayer() {
        return serverPlayer;
    }
}
