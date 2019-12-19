package kata.game_of_three.impl.io_player;

import kata.game_of_three.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOPlayer implements Player {

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

    @Override public void playTurn(Move opponentMove) {
        printStream.println("Opponent move: " + opponentMove.getResultingNumber());
        printStream.println("You reply [-1,0,1]:");
        int reply = scanner.nextInt();
    }

    @Override public void endGame(GameResult gameResult) {

    }
}
