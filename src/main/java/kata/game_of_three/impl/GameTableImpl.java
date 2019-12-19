package kata.game_of_three.impl;

import kata.game_of_three.*;

import java.util.Optional;
import java.util.UUID;

public class GameTableImpl implements GameTable {

    private final Games games;
    private final PlayerFactory playerFactory;
    private final UUIDProvider uuidProvider;

    public GameTableImpl(Games games, PlayerFactory playerFactory, UUIDProvider uuidProvider) {
	this.games = games;
	this.playerFactory = playerFactory;
	this.uuidProvider = uuidProvider;
    }

    @Override public UUID invitePlayerAndReturnGameUuid(PlayerInvitation playerInvitation) {
	UUID gameUuid = uuidProvider.getUUID();

	Player starterPlayer = playerFactory.buildPlayer(playerInvitation.getFrom()).orElseThrow(() -> new RuntimeException("unknown start player|" + playerInvitation.getFrom()));

	if (playerInvitation.getGameInception() < 1) {
	    starterPlayer.endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	    return gameUuid;
	}

        Optional<Player> invitedPlayer = playerFactory.buildPlayer(playerInvitation.getTo());
        if (!invitedPlayer.isPresent()) {
            starterPlayer.endGame(new GameResult(gameUuid, GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.UNKNOWN_PLAYER));
            return gameUuid;
	}

	Player _invitedPlayer = invitedPlayer.get();

	Move move = new Move(gameUuid, starterPlayer.getIdentifier(), _invitedPlayer.getIdentifier(), playerInvitation.getGameInception());
	Game game = new Game(gameUuid, starterPlayer, _invitedPlayer, move);
        games.startGame(game);

        _invitedPlayer.playTurn(move);
        return gameUuid;
    }

    private int getAddedNumber(Move move) {
        return move.getReply().map(reply -> {
	    switch (reply) {
		case ZERO:
		    return 0;
		case ONE:
		    return 1;
		case MINUS_ONE:
		    return -1;
		default:
		    throw new RuntimeException("unknown reply|" + move.getReply());
	    }
	}).orElseThrow(() -> new IllegalStateException("reply is missing from move|" + move));
    }

    private boolean isValidNumber(Move move, Move lastMove) {
	Integer moveNumber = move.getResultingNumber();
	if (moveNumber < 1) return false;

	int addedNumber = getAddedNumber(move);
	return ((double)(lastMove.getResultingNumber() + addedNumber) / 3f) == moveNumber;
    }

    private void acceptMove(Player player, Player opponent, Move move, Move lastMove, Game game) {
	if (!isValidNumber(move, lastMove)) {
	    player.endGame(new GameResult(move.getGameUuid(), GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	    opponent.endGame(new GameResult(move.getGameUuid(), GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	    games.endGame(game.getGameUuid());
	    return;
	}

	if (move.getResultingNumber() == 1) {
	    player.endGame(new GameResult(move.getGameUuid(), GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.GOT_ONE));
	    opponent.endGame(new GameResult(move.getGameUuid(), GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.GOT_ONE));
	    games.endGame(move.getGameUuid());
	    return;
	}

	game.setLastMove(move);
	opponent.playTurn(move);
    }

    @Override public void acceptMove(Move move) {
	Optional<Game> game = games.getGame(move.getGameUuid());
	if (!game.isPresent()) throw new RuntimeException("unknown game|" + move.getGameUuid());

	Game _game = game.get();
	Player player = _game.getPlayerById(move.getPlayer()).
			orElseThrow(() -> new RuntimeException(String.format("unknown player|%s|in game|%s|with players|player1|%s|player 2|%s",
									     move.getPlayer().getId(),
									     _game.getGameUuid(),
									     _game.getPlayer1().getIdentifier(),
									     _game.getPlayer2().getIdentifier())));

	Optional<Player> opponent = _game.getPlayerById(move.getOpponent());
	if (!opponent.isPresent()) {
	    player.endGame(new GameResult(move.getGameUuid(), GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	    Player actualOpponent = _game.getOpponent(player.getIdentifier());
	    actualOpponent.endGame(new GameResult(move.getGameUuid(), GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	    games.endGame(_game.getGameUuid());
	    return;
	}

	opponent.ifPresent(_opponent -> acceptMove(player, _opponent, move, _game.getLastMove(), _game));
    }
}
