package ru.zaralx.bridgebuilders.commons.commands;

import org.bukkit.block.data.Directional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.zaralx.bridgebuilders.BridgeBuilders;
import ru.zaralx.bridgebuilders.commons.npc.Replay;
import ru.zaralx.bridgebuilders.commons.npc.ReplayInRecording;
import ru.zaralx.bridgebuilders.commons.npc.ReplayManager;

import java.util.ArrayList;
import java.util.List;

public class TestCommand implements CommandExecutor, TabCompleter {
    ReplayManager replayManager = BridgeBuilders.getInstance().getReplayManager();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args[0].equals("some_test")) {
                player.sendMessage(player.getLocation().clone().add(0, -1, 0).getBlock().getBlockData().toString());
                player.sendMessage(((Directional) player.getLocation().clone().add(0, -1, 0).getBlock().getBlockData()).getFacing().toString());
            }
                if (args.length >= 3) {
                    if (args[0].equals("record"))
                        if (args[1].equals("start")) {
                            replayManager.addRecordingReplay(new ReplayInRecording(
                                    args[2],
                                    true,
                                    (Player) sender
                            ));
                            sender.sendMessage("§aStarted");
                        } else if (args[1].equals("stop")) {
                            replayManager.getRecordingReplay(args[2]).stop();
                            sender.sendMessage("§aStopped");
                        } else if (args[1].equals("save") && args.length >= 4) {
                            ReplayInRecording recordingReplay = replayManager.getRecordingReplay(args[2]);

                            if (BridgeBuilders.getInstance().getRecordsDatabase().saveNewReplay((Player) sender, args[3], recordingReplay.getRecords())) {
                                replayManager.removeRecordingReplay(recordingReplay);
                                sender.sendMessage("§aSaved");
                            }
                        } else if (args[1].equals("play")) {
                            Replay replay = replayManager.getReplay(args[2]);

                            replay.getNpc().initForAllOnline(true);
                            replay.start();
                            sender.sendMessage("§aResumed");
                        } else if (args[1].equals("pause")) {
                            Replay replay = replayManager.getReplay(args[2]);

                            replay.pause();
                            sender.sendMessage("§aPaused");
                        } else if (args[1].equals("restart")) {
                            Replay replay = replayManager.getReplay(args[2]);

                            replay.restart();
                            sender.sendMessage("§aRestarted");
                        } else if (args[1].equals("load")) {
                            sender.sendMessage("§eLoading..");
                            Replay replay = BridgeBuilders.getInstance().getRecordsDatabase().loadReplay((Player) sender, args[2]);
                            if (replay == null) {
                                sender.sendMessage("§cFailed to load");
                                return true;
                            }
                            replayManager.addReplay(replay);
                            replay.getNpc().initForAllOnline(false);
                            sender.sendMessage("§aLoaded");
                        }
            } else sender.sendMessage("§cNeed arguments");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> arguments = new ArrayList<>();

        if (args.length == 1) {
            arguments.add("record");
            arguments.add("some_test");
        } else if (args.length == 2) {
            arguments.add("start");
            arguments.add("stop");
            arguments.add("save");
            arguments.add("play");
            arguments.add("pause");
            arguments.add("load");
            arguments.add("restart");
        } else if (args.length == 3) {
            arguments.add("<NAME>");
        } else if (args.length == 4) {
            arguments.add("<TO NAME>");
        }

        return arguments;
    }
}
