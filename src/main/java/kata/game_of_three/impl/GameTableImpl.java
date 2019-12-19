package kata.game_of_three.impl;

import kata.game_of_three.*;

import java.util.Optional;

public class GameTableImpl implements GameTable {

    private final Games games;

    public GameTableImpl(Games games) {
	this.games = games;
    }

    @Override public void invitePlayer(PlayerInvitation playerInvitation) {

    }

    private boolean isNumberDivisibleByThree(Integer number) {
        return number % 3 == 0;
    }

    private void acceptMove(Player player, Player opponent, Move move) {
        if (!isNumberDivisibleByThree(move.getResultingNumber())) {
	    player.endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	    opponent.endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	    return;
	}

	opponent.playTurn(move);
    }

    @Override public void acceptMove(Move move) {
	Game game = games.getGame(move.getGameUuid());
	Player player = game.getPlayerById(move.getPlayer()).
			orElseThrow(() -> new RuntimeException(String.format("unknown player|%s|in game|%s|with players|player1|%s|player 2|%s",
									     move.getPlayer().getIdentifier(),
									     game.getGameUuid(),
									     game.getPlayer1().getIdentifier(),
									     game.getPlayer2().getIdentifier())));

	Optional<Player> opponent = game.getPlayerById(move.getOpponent());
	if (!opponent.isPresent()) {
	    player.endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	    return;
	}

	opponent.ifPresent(_opponent -> acceptMove(player, _opponent, move));
    }
}
