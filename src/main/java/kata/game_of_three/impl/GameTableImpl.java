package kata.game_of_three.impl;

import kata.game_of_three.*;

import java.util.Optional;

public class GameTableImpl implements GameTable {

    private final Games games;
    private final PlayerFactory playerFactory;
    private final UUIDProvider uuidProvider;

    public GameTableImpl(Games games, PlayerFactory playerFactory, UUIDProvider uuidProvider) {
	this.games = games;
	this.playerFactory = playerFactory;
	this.uuidProvider = uuidProvider;
    }

    @Override public void invitePlayer(PlayerInvitation playerInvitation) {
	Player starterPlayer = playerFactory.buildPlayer(playerInvitation.getFrom()).orElseThrow(() -> new RuntimeException("unknown start player|" + playerInvitation.getFrom()));
        Optional<Player> invitedPlayer = playerFactory.buildPlayer(playerInvitation.getTo());
        if (!invitedPlayer.isPresent()) {
            starterPlayer.endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.UNKNOWN_PLAYER));
            return;
	}

	Player _invitedPlayer = invitedPlayer.get();
        Game game = new Game(uuidProvider.getUUID(), starterPlayer, _invitedPlayer);
        games.startGame(game);

        Move move = new Move(game.getGameUuid(), starterPlayer.getIdentifier(), _invitedPlayer.getIdentifier(), playerInvitation.getGameInception());
        _invitedPlayer.playTurn(move);
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
	if (moveNumber < 3) return false;

	int addedNumber = getAddedNumber(move);
	return ((lastMove.getResultingNumber() + addedNumber) / 3) == moveNumber;
    }

    private void acceptMove(Player player, Player opponent, Move move, Move lastMove, Game game) {
	if (!isValidNumber(move, lastMove)) {
	    player.endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	    opponent.endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	    games.endGame(game.getGameUuid());
	    return;
	}

	if (move.getResultingNumber() == 3) {
	    player.endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.GOT_ONE));
	    opponent.endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.GOT_ONE));
	    games.endGame(move.getGameUuid());
	    return;
	}

	opponent.playTurn(move);
	game.setLastMove(move);
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
	    player.endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	    Player actualOpponent = _game.getOpponent(player.getIdentifier());
	    actualOpponent.endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	    games.endGame(_game.getGameUuid());
	    return;
	}

	opponent.ifPresent(_opponent -> {
	    Move lastMove = _game.getLastMove().orElseThrow(() -> new IllegalStateException("game does not have a last move"));
	    acceptMove(player, _opponent, move, lastMove, _game);
	});
    }
}
