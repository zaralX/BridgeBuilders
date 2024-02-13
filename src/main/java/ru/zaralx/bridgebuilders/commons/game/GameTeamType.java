package ru.zaralx.bridgebuilders.commons.game;

import net.md_5.bungee.api.ChatColor;

public enum GameTeamType {
    RED("Red", ChatColor.of("#ff0000")),
    GREEN("Green", ChatColor.of("#00ff00")),
    BLUE("Blue", ChatColor.of("#0000ff")),
    YELLOW("Yellow", ChatColor.of("#ffff00"));
    private final String name;
    private final ChatColor color;

    GameTeamType(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }
}
