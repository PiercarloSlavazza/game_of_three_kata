package kata.game_of_three.impl;

import kata.game_of_three.Player;
import kata.game_of_three.PlayerIdentifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameOfThreeMockUtils {

    static PlayerIdentifier mockPlayerIdentifier(String playerId) {
	PlayerIdentifier playerIdentifier = mock(PlayerIdentifier.class);
	when(playerIdentifier.getId()).thenReturn(playerId);

	return playerIdentifier;
    }

    static Player mockPlayer(String playerId) {
	PlayerIdentifier playerIdentifier = mockPlayerIdentifier(playerId);

	Player player = mock(Player.class);
	when(player.getIdentifier()).thenReturn(playerIdentifier);
	return player;
    }

}
