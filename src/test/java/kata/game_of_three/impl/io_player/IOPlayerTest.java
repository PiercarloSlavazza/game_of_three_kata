package kata.game_of_three.impl.io_player;

import kata.game_of_three.GameTable;
import kata.game_of_three.Move;
import kata.game_of_three.Player;
import kata.game_of_three.impl.GameOfThreeMockUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class IOPlayerTest {

    @Test
    public void shouldReadNextMoveFromUser() throws IOException {
	GameTable gameTable = mock(GameTable.class);

	Player opponentPlayer = GameOfThreeMockUtils.mockPlayer("player1");
	try (InputStream inputStream = new ByteArrayInputStream("1".getBytes());
			PipedInputStream pipedInputStream = new PipedInputStream();
			OutputStream outputStream = new PipedOutputStream(pipedInputStream);
			PrintStream printStream = new PrintStream(outputStream)) {
	    IOPlayer ioPlayer = new IOPlayer(GameOfThreeMockUtils.mockPlayerIdentifier("player2"), gameTable, inputStream, printStream);

	    UUID gameUuid = UUID.randomUUID();

	    Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), ioPlayer.getIdentifier(), Move.REPLY.ZERO, 14);
	    ioPlayer.playTurn(player1Move);

	    Move expectedMove = new Move(gameUuid, ioPlayer.getIdentifier(), opponentPlayer.getIdentifier(), Move.REPLY.ONE, 5);
	    Mockito.verify(gameTable, times(1)).acceptMove(expectedMove);

	    outputStream.close();
	    String lineSeparator = System.getProperty("line.separator");
	    String playerOutput = String.format("[player1] Opponent move: 14%s[player2] Your reply [-1,0,1]:%sYour move is: 1, 5. Waiting for your opponent...%s",
						lineSeparator,
						lineSeparator,
						lineSeparator);
	    assertEquals(playerOutput, IOUtils.toString(pipedInputStream, "UTF-8"));
	}
    }

}
