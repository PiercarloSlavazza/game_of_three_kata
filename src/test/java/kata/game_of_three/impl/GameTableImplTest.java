package kata.game_of_three.impl;

import kata.game_of_three.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@SuppressWarnings("Duplicates") public class GameTableImplTest {

    private Games games;
    private GameTableImpl gameTable;

    @Before
    public void setUp() {
	games = mock(Games.class);
	gameTable = new GameTableImpl(games);
    }

    private static Player mockPlayer(String playerId) {
	PlayerIdentifier playerIdentifier = mock(PlayerIdentifier.class);
	when(playerIdentifier.getId()).thenReturn(playerId);

	Player player = mock(Player.class);
	when(player.getIdentifier()).thenReturn(playerIdentifier);
	return player;
    }

    @Test
    public void shouldAcceptValidPlusOne() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 14);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.ONE, 5);
	gameTable.acceptMove(move);

	verify(player1, times(1)).playTurn(move);
    }

    @Test
    public void shouldAcceptValidMoveZero() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 9);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.ZERO, 3);
	gameTable.acceptMove(move);

	verify(player1, times(1)).playTurn(move);
    }

    @Test
    public void shouldAcceptValidMoveMinusOne() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 10);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.MINUS_ONE, 3);
	gameTable.acceptMove(move);

	verify(player1, times(1)).playTurn(move);
    }

    @Test
    public void shouldNotAcceptNumberNotDivisibleByThree() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 8);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.ONE, 7);
	gameTable.acceptMove(move);

	verify(player2, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(player1, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
    }

    @Test
    public void shouldNotAcceptNumberLowerThanThree() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 8);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.ONE, 2);
	gameTable.acceptMove(move);

	verify(player2, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(player1, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
    }

    @Test
    public void shouldNotAcceptZero() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 8);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.ONE, 0);
	gameTable.acceptMove(move);

	verify(player2, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(player1, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
    }

    @Test
    public void shouldNotAcceptNegativeNumbers() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 8);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.ONE, -1);
	gameTable.acceptMove(move);

	verify(player2, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(player1, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
    }

    @Test
    public void shouldNotAcceptBadlyCalculatedNumbers() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 8);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.ONE, 15);
	gameTable.acceptMove(move);

	verify(player2, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(player1, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
    }

    @Test
    public void shouldFailWhenGameDoesNotExists() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 8);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	Move move = new Move(UUID.randomUUID(), player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.ONE, 15);
	try {
	    gameTable.acceptMove(move);
	    fail();
	} catch (RuntimeException ignored) {}
    }

    @Test
    public void shouldNotAcceptUnknownOpponent() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 8);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	PlayerIdentifier unknownOpponent = mock(PlayerIdentifier.class);
	when(unknownOpponent.getId()).thenReturn("unknown");

	Move move = new Move(gameUuid, player2.getIdentifier(), unknownOpponent, Move.REPLY.ONE, 15);
	gameTable.acceptMove(move);

	verify(player2, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(player1, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
    }
}

