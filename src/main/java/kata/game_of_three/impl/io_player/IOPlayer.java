package kata.game_of_three.impl.io_player;

import kata.game_of_three.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType") public class IOPlayer implements Player {

    private final PlayerIdentifier playerIdentifier;
    private final GameTable gameTable;
    private final Scanner scanner;
    private final PrintStream printStream;

    public IOPlayer(PlayerIdentifier playerIdentifier, GameTable gameTable, InputStream inputStream, PrintStream printStream) {
	this.playerIdentifier = playerIdentifier;
	this.gameTable = gameTable;
	this.scanner = new Scanner(inputStream);
	this.printStream = printStream;
    }

    @Override public PlayerIdentifier getIdentifier() {
	return playerIdentifier;
    }

    private Optional<Move.REPLY> normalizeReply(int reply) {
	if (reply == 0) return Optional.of(Move.REPLY.ZERO);
	if (reply == -1) return  Optional.of(Move.REPLY.MINUS_ONE);
	if (reply == 1) return Optional.of(Move.REPLY.ONE);
	throw new IllegalStateException("reply must be in [-1, 1] but is" + reply);
    }

    private String formatWrongInputMessage(Optional<Integer> input) {
	return "Invalid input: please answer with -1 or 0 or 1" + input.map(i -> " (your input was: " + i + ")").orElse("") + ":";
    }

    @Override public void playTurn(Move opponentMove) {
	Integer opponentNumber = opponentMove.getResultingNumber();
	printStream.println("Opponent move: " + opponentNumber);
	printStream.println("You reply [-1,0,1]:");
	do {
	    try {
		int reply = scanner.nextInt();
		Optional<Move.REPLY> normalizedReply = normalizeReply(reply);
		if (!normalizedReply.isPresent()) {
		    printStream.println(formatWrongInputMessage(Optional.of(reply)));
		    continue;
		}

		int resultingNumber = (opponentNumber + reply) / 3;
		Move replyMove = new Move(opponentMove.getGameUuid(), playerIdentifier, opponentMove.getPlayer(), normalizedReply.get(), resultingNumber);
		gameTable.acceptMove(replyMove);

		break;
	    } catch (InputMismatchException e) {
		printStream.println(formatWrongInputMessage(Optional.empty()));
	    }
	} while (true);
    }

    private String formatEndGameOutcomeReason(boolean youWin, GameResult.GAME_OUTCOME_REASON gameOutcomeReason) {
	switch (gameOutcomeReason) {
	    case GOT_ONE:
		return youWin ? "You got 1." : "You opponent got 1.";
	    case INVALID_MOVE:
		return youWin ? "Your opponent did an invalid move" : "You did an invalid move";
	    case UNKNOWN_PLAYER:
		return "Your opponent replied to an unknown player.";
	    default:
	        throw new IllegalStateException("unknown game outcome reason|" + gameOutcomeReason);
	}
    }

    @Override public void endGame(GameResult gameResult) {
	boolean youWin = gameResult.getGameOutcome().equals(GameResult.GAME_OUTCOME.YOU_WIN);
	String endGameMessage = "You " + (youWin ? "WIN!" : "lose.") + "\n" + formatEndGameOutcomeReason(youWin, gameResult.getGameOutcomeReason());
	printStream.println(endGameMessage);
    }
}
