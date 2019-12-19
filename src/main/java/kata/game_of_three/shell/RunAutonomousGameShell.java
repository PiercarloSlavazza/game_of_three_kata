package kata.game_of_three.shell;

import kata.game_of_three.Player;
import kata.game_of_three.PlayerInvitation;
import kata.game_of_three.impl.*;
import kata.game_of_three.impl.autonomous_player.AutonomousPlayerEventsLogger;

import java.util.UUID;

public class RunAutonomousGameShell {

    public static void main(String[] args) {

	RegistryPlayerFactory registryPlayerFactory = new RegistryPlayerFactory();
	InMemoryGames games = new InMemoryGames();
	GameTableImpl gameTable = new GameTableImpl(games, registryPlayerFactory, UUID::randomUUID);
	AutonomousPlayerEventsLogger autonomousPlayerEventsLogger = new AutonomousPlayerEventsLogger();

	LocalAutonomousPlayerFactory localAutonomousPlayerFactory = new LocalAutonomousPlayerFactory(gameTable, autonomousPlayerEventsLogger);

	PlayerIdentifierImpl player1Identifier = new PlayerIdentifierImpl("P1");
	PlayerIdentifierImpl player2Identifier = new PlayerIdentifierImpl("P2");

	Player player1 = localAutonomousPlayerFactory.buildPlayer(player1Identifier).orElseThrow(RuntimeException::new);
	registryPlayerFactory.addPlayer(player1);

	Player player2 = localAutonomousPlayerFactory.buildPlayer(player2Identifier).orElseThrow(RuntimeException::new);
	registryPlayerFactory.addPlayer(player2);

	PlayerInvitation playerInvitation = new PlayerInvitation(player1Identifier, player2Identifier, 56);
	gameTable.invitePlayerAndReturnGameUuid(playerInvitation);
    }

}
