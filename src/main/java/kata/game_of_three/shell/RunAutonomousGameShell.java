package kata.game_of_three.shell;

import kata.game_of_three.Player;
import kata.game_of_three.PlayerInvitation;
import kata.game_of_three.impl.*;
import kata.game_of_three.impl.autonomous_player.AutonomousPlayerEventsLogger;

import java.util.UUID;

public class RunAutonomousGameShell {

    public static void main(String[] args) {

	CachedPlayerFactory cachedPlayerFactory = new CachedPlayerFactory();
	InMemoryGames games = new InMemoryGames();
	GameTableImpl gameTable = new GameTableImpl(games, cachedPlayerFactory, UUID::randomUUID);
	AutonomousPlayerEventsLogger autonomousPlayerEventsLogger = new AutonomousPlayerEventsLogger();

	LocalAutonomousPlayerFactory localAutonomousPlayerFactory = new LocalAutonomousPlayerFactory(gameTable, autonomousPlayerEventsLogger);

	PlayerIdentifierImpl player1Identifier = new PlayerIdentifierImpl("P1");
	PlayerIdentifierImpl player2Identifier = new PlayerIdentifierImpl("P2");

	Player player1 = localAutonomousPlayerFactory.buildPlayer(player1Identifier).orElseThrow(RuntimeException::new);
	cachedPlayerFactory.addPlayer(player1);

	Player player2 = localAutonomousPlayerFactory.buildPlayer(player2Identifier).orElseThrow(RuntimeException::new);
	cachedPlayerFactory.addPlayer(player2);

	PlayerInvitation playerInvitation = new PlayerInvitation(player1Identifier, player2Identifier, 56);
	gameTable.invitePlayer(playerInvitation);
    }

}
