package ru.zaralx.bridgebuilders.commons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.zaralx.bridgebuilders.BridgeBuilders;
import ru.zaralx.bridgebuilders.commons.game.BuildItems;
import ru.zaralx.bridgebuilders.commons.game.Game;
import ru.zaralx.bridgebuilders.commons.game.GameManager;

import java.util.ArrayList;
import java.util.List;

public class GameCommand implements CommandExecutor, TabCompleter {
    GameManager gameManager = BridgeBuilders.getInstance().getGameManager();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("list")) gameList(sender);
        if (args[0].equalsIgnoreCase("item_list")) itemList(sender, args[1]);

        if (sender instanceof Player player) {
            if (args[0].equalsIgnoreCase("join")) {
                if (gameManager.join(player, args[1])) {
                    player.sendMessage("Connected");
                } else {
                    player.sendMessage("Failed to connect");
                }
            }

            if (args[0].equalsIgnoreCase("leave")) {
                if (gameManager.leave(player)) {
                    player.sendMessage("Leaved");
                } else {
                    player.sendMessage("Failed to leave");
                }
            }

            if (args[0].equalsIgnoreCase("start")) {
                if (gameManager.start(args[1])) {
                    player.sendMessage("Started");
                } else {
                    player.sendMessage("Failed to start");
                }
            }
        }

        return true;
    }

    private void itemList(CommandSender sender, String gameId) {
        for (Game game : gameManager.getGames()) {
            if (game.getId().equals(gameId) || game.getName().equals(gameId)) {
                for (BuildItems.BuildItem item : game.getBuildItems().getItems()) {
                    sender.sendMessage(
                            "§f" + item.material.toString() + " §ex" + item.count
                    );
                }
                break;
            }
        }
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

        switch (args.length) {
            case 1:
                arguments.addAll(List.of("list", "join", "leave", "start", "item_list"));
                break;
            case 2:
                if (args[0].equals("join") || args[0].equals("start") || args[0].equals("item_list")) {
                    for (Game game : gameManager.getGames()) {
                        arguments.add(game.getId());
                    }
                }
                break;
        }

        return arguments;
    }
}
