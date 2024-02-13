package ru.zaralx.bridgebuilders.commons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.zaralx.bridgebuilders.BridgeBuilders;
import ru.zaralx.bridgebuilders.commons.game.Game;
import ru.zaralx.bridgebuilders.commons.game.GameManager;

import java.util.ArrayList;
import java.util.List;

public class GameCommand implements CommandExecutor, TabCompleter {
    GameManager gameManager = BridgeBuilders.getInstance().getGameManager();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("list")) gameList(sender);
        if (args[0].equalsIgnoreCase("list")) gameList(sender);

        if (sender instanceof Player player) {

        }

        return true;
    }

    private void gameList(CommandSender sender) {
        for (Game game : gameManager.getGames()) {
            sender.sendMessage(
                    game.getId() + " §8| §9" + game.getName() + " §8| §e" + game.getPlayers().size() + "/" + game.getMaxPlayers()
            );
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> arguments = new ArrayList<>();

        return arguments;
    }
}
