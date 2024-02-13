package ru.zaralx.bridgebuilders.commons.game;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.zaralx.bridgebuilders.BridgeBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameManager {
    private BridgeBuilders bridgeBuildersInstance;
    private List<Game> games = new ArrayList<>();
    private File gamesFolder;

    public GameManager(BridgeBuilders bridgeBuildersInstance) {
        this.bridgeBuildersInstance = bridgeBuildersInstance;
        this.gamesFolder = new File(bridgeBuildersInstance.getDataFolder().getAbsolutePath() + File.separator + "games");
    }

    public void initGames() {
        if (!bridgeBuildersInstance.getDataFolder().exists()) bridgeBuildersInstance.getDataFolder().mkdir();
        if (!gamesFolder.exists()) gamesFolder.mkdir();

        for (File file : Objects.requireNonNull(gamesFolder.listFiles())) {
            String fileName = file.getName();
            String gameName = fileName.substring(0, fileName.lastIndexOf('.'));
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            Game newGame = new Game(gameName.toLowerCase(), configuration);
            games.add(newGame);
        }
    }

    public List<Game> getGames() {
        return games;
    }
}
