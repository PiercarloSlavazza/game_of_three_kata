package kata.game_of_three;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Game {

    private final UUID gameUuid;
    private final Player player1;
    private final Player player2;
    private Move lastMove;

    public Game(UUID gameUuid, Player player1, Player player2, Move lastMove) {
	this.gameUuid = gameUuid;
	this.player1 = player1;
	this.player2 = player2;
	this.lastMove = lastMove;
    }

    public Optional<Player> getPlayerById(PlayerIdentifier playerIdentifier) {
        if (player1.getIdentifier().equals(playerIdentifier)) return Optional.of(player1);
	if (player2.getIdentifier().equals(playerIdentifier)) return Optional.of(player2);
	return Optional.empty();
    }

    public Player getPlayer1() {
	return player1;
    }

    public Player getPlayer2() {
	return player2;
    }

    public Player getOpponent(PlayerIdentifier playerIdentifier) {
        if (player1.getIdentifier().getId().equals(playerIdentifier.getId())) return player2;
        if (player2.getIdentifier().getId().equals(playerIdentifier.getId())) return player1;

        throw new RuntimeException("unknown player identifier|" + playerIdentifier);
    }

    public UUID getGameUuid() {
	return gameUuid;
    }

    public void setLastMove(Move lastMove) {
	this.lastMove = lastMove;
    }

    public Move getLastMove() {
	return lastMove;
    }

    @Override public String toString() {
	return "Game{" +
			"gameUuid=" + gameUuid +
			", player1=" + player1 +
			", player2=" + player2 +
			", lastMove=" + lastMove +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof Game))
	    return false;
	Game game = (Game) o;
	return Objects.equals(gameUuid, game.gameUuid) &&
			Objects.equals(player1, game.player1) &&
			Objects.equals(player2, game.player2) &&
			Objects.equals(lastMove, game.lastMove);
    }

    @Override public int hashCode() {
	return Objects.hash(gameUuid, player1, player2, lastMove);
    }
}
