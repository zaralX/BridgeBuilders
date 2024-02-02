package ru.zaralx.bridgebuilders;

import org.bukkit.plugin.java.JavaPlugin;
import ru.zaralx.bridgebuilders.commons.commands.TestCommand;
import ru.zaralx.bridgebuilders.commons.npc.ReplayManager;
import ru.zaralx.bridgebuilders.commons.npc.database.RecordsDatabase;
import ru.zaralx.bridgebuilders.listeners.PlayerListener;

import java.sql.SQLException;

public final class BridgeBuilders extends JavaPlugin {
    private static BridgeBuilders instance;
    private ReplayManager replayManager;
    private RecordsDatabase recordsDatabase;

    @Override
    public void onEnable() {
        instance = this;
        replayManager = new ReplayManager();

        recordsDatabase = new RecordsDatabase();
        recordsDatabase.connect();

        getCommand("test").setExecutor(new TestCommand());

        new PlayerListener();
    }

    @Override
    public void onDisable() {

    }

    public static BridgeBuilders getInstance() {
        return instance;
    }

    public ReplayManager getReplayManager() {
        return replayManager;
    }

    public RecordsDatabase getRecordsDatabase() {
        return recordsDatabase;
    }
}
