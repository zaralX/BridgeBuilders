package ru.zaralx.bridgebuilders;

import org.bukkit.plugin.java.JavaPlugin;
import ru.zaralx.bridgebuilders.commons.commands.GameCommand;
import ru.zaralx.bridgebuilders.commons.commands.TestCommand;
import ru.zaralx.bridgebuilders.commons.game.GameManager;
import ru.zaralx.bridgebuilders.commons.npc.ReplayManager;
import ru.zaralx.bridgebuilders.commons.npc.database.RecordsDatabase;
import ru.zaralx.bridgebuilders.listeners.PlayerListener;

import java.sql.SQLException;

public final class BridgeBuilders extends JavaPlugin {
    private static BridgeBuilders instance;
    private ReplayManager replayManager;
    private RecordsDatabase recordsDatabase;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;
        replayManager = new ReplayManager();
        gameManager = new GameManager(this);

        if (!getDataFolder().exists()) getDataFolder().mkdir();

        recordsDatabase = new RecordsDatabase();
        recordsDatabase.connect();

        getCommand("test").setExecutor(new TestCommand());
        getCommand("game").setExecutor(new GameCommand());

        new PlayerListener();

        gameManager.initGames();
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

    public GameManager getGameManager() {
        return gameManager;
    }

    public RecordsDatabase getRecordsDatabase() {
        return recordsDatabase;
    }
}
