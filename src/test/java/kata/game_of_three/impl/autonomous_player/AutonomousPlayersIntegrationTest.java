package kata.game_of_three.impl.autonomous_player;

import kata.game_of_three.GameResult;
import kata.game_of_three.Move;
import kata.game_of_three.Player;
import kata.game_of_three.PlayerInvitation;
import kata.game_of_three.impl.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class AutonomousPlayersIntegrationTest {

    @Test
    public void shouldPlayAGameUntilTheEnd() {

	CachedPlayerFactory cachedPlayerFactory = new CachedPlayerFactory();
	InMemoryGames games = new InMemoryGames();
	GameTableImpl gameTable = new GameTableImpl(games, cachedPlayerFactory, UUID::randomUUID);
	AutonomousPlayerEventsCollector autonomousPlayerEventsCollector = new AutonomousPlayerEventsCollector();

	LocalAutonomousPlayerFactory localAutonomousPlayerFactory = new LocalAutonomousPlayerFactory(gameTable, autonomousPlayerEventsCollector);

	PlayerIdentifierImpl player1Identifier = new PlayerIdentifierImpl("P1");
	PlayerIdentifierImpl player2Identifier = new PlayerIdentifierImpl("P2");

	Player player1 = localAutonomousPlayerFactory.buildPlayer(player1Identifier).orElseThrow(RuntimeException::new);
	cachedPlayerFactory.addPlayer(player1);

	Player player2 = localAutonomousPlayerFactory.buildPlayer(player2Identifier).orElseThrow(RuntimeException::new);
	cachedPlayerFactory.addPlayer(player2);

	PlayerInvitation playerInvitation = new PlayerInvitation(player1Identifier, player2Identifier, 56);
	UUID gameUuid = gameTable.invitePlayer(playerInvitation);

	List<Turn> expectedTurns = new ArrayList<>();
	expectedTurns.add(new Turn(new Move(gameUuid, player2Identifier, player1Identifier, Move.REPLY.ONE, 19),
				   new Move(gameUuid, player1Identifier, player2Identifier, 56)));

	expectedTurns.add(new Turn(new Move(gameUuid, player1Identifier, player2Identifier, Move.REPLY.MINUS_ONE, 6),
				   new Move(gameUuid, player2Identifier, player1Identifier, Move.REPLY.ONE, 19)));

	expectedTurns.add(new Turn(new Move(gameUuid, player2Identifier, player1Identifier, Move.REPLY.ZERO, 2),
				   new Move(gameUuid, player1Identifier, player2Identifier, Move.REPLY.MINUS_ONE, 6)));

	expectedTurns.add(new Turn(new Move(gameUuid, player1Identifier, player2Identifier, Move.REPLY.ONE, 1),
				   new Move(gameUuid, player2Identifier, player1Identifier, Move.REPLY.ZERO, 2)));
	assertEquals(expectedTurns, autonomousPlayerEventsCollector.getRecordedTurns());

	List<PlayerEndGame> expectedPlayerEndGames = new ArrayList<>();
	expectedPlayerEndGames.add(new PlayerEndGame(player1Identifier,
						     new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.GOT_ONE)));
	expectedPlayerEndGames.add(new PlayerEndGame(player2Identifier,
						     new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.GOT_ONE)));
	assertEquals(expectedPlayerEndGames, autonomousPlayerEventsCollector.getRecordedPlayerEndGames());
    }

}
