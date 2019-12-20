package kata.game_of_three.shell;

import kata.game_of_three.Player;
import kata.game_of_three.PlayerIdentifier;
import kata.game_of_three.PlayerInvitation;
import kata.game_of_three.impl.*;
import kata.game_of_three.impl.autonomous_player.AutonomousPlayer;
import kata.game_of_three.impl.autonomous_player.AutonomousPlayerEventsLogger;

import java.util.UUID;

public class RunAutonomousGameShell {

    public static void main(String[] args) {

	RegistryPlayerFactory registryPlayerFactory = new RegistryPlayerFactory();
	InMemoryGames games = new InMemoryGames();
	GameTableImpl gameTable = new GameTableImpl(games, registryPlayerFactory, UUID::randomUUID);
	AutonomousPlayerEventsLogger autonomousPlayerEventsLogger = new AutonomousPlayerEventsLogger();

	PlayerIdentifier player1Identifier = new PlayerIdentifier("P1");
	PlayerIdentifier player2Identifier = new PlayerIdentifier("P2");

	Player player1 = new AutonomousPlayer(player1Identifier, gameTable, autonomousPlayerEventsLogger);
	registryPlayerFactory.addPlayer(player1);

	Player player2 = new AutonomousPlayer(player2Identifier, gameTable, autonomousPlayerEventsLogger);
	registryPlayerFactory.addPlayer(player2);

	PlayerInvitation playerInvitation = new PlayerInvitation(player1Identifier, player2Identifier, 56);
	gameTable.invitePlayerAndReturnGameUuid(playerInvitation);
    }

}
