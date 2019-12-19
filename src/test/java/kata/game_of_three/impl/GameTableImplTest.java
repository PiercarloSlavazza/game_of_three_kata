package kata.game_of_three.impl;

import kata.game_of_three.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static kata.game_of_three.impl.GameOfThreeMockUtils.mockPlayer;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("Duplicates") public class GameTableImplTest {

    private Games games;
    private GameTableImpl gameTable;
    private PlayerFactory playerFactory;
    private UUIDProvider uuidProvider;

    @Before
    public void setUp() {
	games = mock(Games.class);
	playerFactory = mock(PlayerFactory.class);
	uuidProvider = mock(UUIDProvider.class);
	gameTable = new GameTableImpl(games, playerFactory, uuidProvider);
    }

    @Test
    public void shouldEndGame() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 8);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.ONE, 3);
	gameTable.acceptMove(move);

	verify(player2, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.GOT_ONE));
	verify(player1, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.GOT_ONE));
	verify(games, times(1)).endGame(gameUuid);
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
	assertEquals(game.getLastMove(), move);
    }

    @Test
    public void shouldAcceptValidMoveZero() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 15);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.ZERO, 5);
	gameTable.acceptMove(move);

	verify(player1, times(1)).playTurn(move);
	assertEquals(game.getLastMove(), move);
    }

    @Test
    public void shouldAcceptValidMoveMinusOne() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 16);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(Optional.of(game));

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.MINUS_ONE, 5);
	gameTable.acceptMove(move);

	verify(player1, times(1)).playTurn(move);
	assertEquals(game.getLastMove(), move);
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

	verify(player2, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(player1, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(games, times(1)).endGame(gameUuid);
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

	verify(player2, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(player1, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(games, times(1)).endGame(gameUuid);
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

	verify(player2, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(player1, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(games, times(1)).endGame(gameUuid);
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

	verify(player2, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(player1, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(games, times(1)).endGame(gameUuid);
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

	verify(player2, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(player1, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(games, times(1)).endGame(gameUuid);
    }

    @Test
    public void shouldStartGameOnInvitation() {

	Player player1 = mockPlayer("player1");
	when(playerFactory.buildPlayer(player1.getIdentifier())).thenReturn(Optional.of(player1));
	Player player2 = mockPlayer("player2");
	when(playerFactory.buildPlayer(player2.getIdentifier())).thenReturn(Optional.of(player2));

	UUID gameUuid = UUID.randomUUID();
	when(uuidProvider.getUUID()).thenReturn(gameUuid);

	PlayerInvitation playerInvitation = new PlayerInvitation(player1.getIdentifier(), player2.getIdentifier(), 56);
	gameTable.invitePlayer(playerInvitation);

	Move expectedMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), playerInvitation.getGameInception());
	Game expectedGame = new Game(gameUuid, player1, player2, expectedMove);
	verify(games, times(1)).startGame(expectedGame);
	verify(player2, times(1)).playTurn(expectedMove);
    }

    @Test
    public void shouldNotStartGameWhenUnknownInvited() {

	Player player1 = mockPlayer("player1");
	when(playerFactory.buildPlayer(player1.getIdentifier())).thenReturn(Optional.of(player1));
	Player player2 = mockPlayer("player2");
	when(playerFactory.buildPlayer(player2.getIdentifier())).thenReturn(Optional.empty());

	UUID gameUuid = UUID.randomUUID();
	when(uuidProvider.getUUID()).thenReturn(gameUuid);

	PlayerInvitation playerInvitation = new PlayerInvitation(player1.getIdentifier(), player2.getIdentifier(), 56);
	gameTable.invitePlayer(playerInvitation);

	Move expectedMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), playerInvitation.getGameInception());
	Game expectedGame = new Game(gameUuid, player1, player2, expectedMove);
	verify(player1, times(1)).endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.UNKNOWN_PLAYER));
	verify(games, times(0)).startGame(expectedGame);
	verify(player2, times(0)).playTurn(expectedMove);
    }
}

