package kata.game_of_three.shell;

import kata.game_of_three.Player;
import kata.game_of_three.PlayerInvitation;
import kata.game_of_three.impl.GameTableImpl;
import kata.game_of_three.impl.InMemoryGames;
import kata.game_of_three.impl.PlayerIdentifierImpl;
import kata.game_of_three.impl.RegistryPlayerFactory;
import kata.game_of_three.impl.autonomous_player.AutonomousPlayer;
import kata.game_of_three.impl.autonomous_player.AutonomousPlayerEventsLogger;
import kata.game_of_three.impl.io_player.IOPlayer;

import java.util.UUID;

public class RunMixedGameShell {

    public static void main(String[] args) {

	RegistryPlayerFactory registryPlayerFactory = new RegistryPlayerFactory();
	InMemoryGames games = new InMemoryGames();
	GameTableImpl gameTable = new GameTableImpl(games, registryPlayerFactory, UUID::randomUUID);
	AutonomousPlayerEventsLogger autonomousPlayerEventsLogger = new AutonomousPlayerEventsLogger();

	PlayerIdentifierImpl player1Identifier = new PlayerIdentifierImpl("P1");
	PlayerIdentifierImpl player2Identifier = new PlayerIdentifierImpl("P2");

	Player player1 = new AutonomousPlayer(player1Identifier, gameTable, autonomousPlayerEventsLogger);
	registryPlayerFactory.addPlayer(player1);

	Player player2 = new IOPlayer(player2Identifier, gameTable, System.in, System.out);
	registryPlayerFactory.addPlayer(player2);

	PlayerInvitation playerInvitation = new PlayerInvitation(player1Identifier, player2Identifier, 234);
	gameTable.invitePlayerAndReturnGameUuid(playerInvitation);
    }

}
