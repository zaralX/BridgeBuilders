package ru.zaralx.bridgebuilders.commons.npc.record;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import ru.zaralx.bridgebuilders.commons.npc.BaseNPC;

/*
 * BaseRecord - Основа для записей
 * действий игрока и применения их
 * в дальнейшем
 */
public abstract class BaseRecord {
    public ServerGamePacketListenerImpl packetListener;
    public void execute(BaseNPC npc) {
        packetListener = npc.getPacketListener();
    }
}
