package ru.zaralx.bridgebuilders.commons.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.zaralx.bridgebuilders.commons.npc.Replay;

import java.util.List;

public class GameTeam {
    private Location respawnLocation;
    private GameTeamType teamType;
    private List<Player> players;
    private Replay replay;

    public GameTeam(Location respawnLocation, GameTeamType teamType, List<Player> players, Replay replay) {
        this.respawnLocation = respawnLocation;
        this.teamType = teamType;
        this.players = players;
        this.replay = replay;
    }
}
