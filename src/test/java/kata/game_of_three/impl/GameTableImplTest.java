package kata.game_of_three.impl;

import kata.game_of_three.Player;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class GameTableImplTest {

    private GameTableImpl gameTable;

    @Before
    public void setUp() {
	gameTable = new GameTableImpl();
    }

    @Test
    public void shouldAcceptValidMove() {

	Player opponent = mock(Player.class);


    }

}
