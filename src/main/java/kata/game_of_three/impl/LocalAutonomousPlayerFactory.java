package kata.game_of_three.impl;

import kata.game_of_three.*;
import kata.game_of_three.impl.autonomous_player.AutonomousPlayer;
import kata.game_of_three.impl.autonomous_player.AutonomousPlayerEventsListener;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Optional;

public class LocalAutonomousPlayerFactory implements PlayerFactory, PlayerIdentifierVisitor<Player> {

    private final GameTable gameTable;
    private final AutonomousPlayerEventsListener[] playerEventsListeners;

    public LocalAutonomousPlayerFactory(GameTable gameTable, AutonomousPlayerEventsListener... playerEventsListeners) {
	this.gameTable = gameTable;
	this.playerEventsListeners = playerEventsListeners;
    }

    @Override public Optional<Player> buildPlayer(PlayerIdentifier playerIdentifier) {
	return Optional.of(playerIdentifier.accept(this));
    }

    @Override public Player visit(RemotePlayerIdentifier playerIdentifier) {
	throw new NotImplementedException("");
    }

    @Override public Player visit(PlayerIdentifierImpl playerIdentifier) {
	return new AutonomousPlayer(playerIdentifier, gameTable, playerEventsListeners);
    }
}
