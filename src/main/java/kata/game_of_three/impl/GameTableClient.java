package kata.game_of_three.impl;

import kata.game_of_three.GameTable;
import kata.game_of_three.Move;
import kata.game_of_three.PlayerInvitation;
import kata.game_of_three.rest_api.client.ApiException;
import kata.game_of_three.rest_api.client.GameofthreegametableApi;

import java.util.UUID;

public class GameTableClient implements GameTable {

    private final GameofthreegametableApi gameofthreegametableApi;

    public GameTableClient(GameofthreegametableApi gameofthreegametableApi) {
	this.gameofthreegametableApi = gameofthreegametableApi;
    }

    @Override public UUID invitePlayerAndReturnGameUuid(PlayerInvitation playerInvitation) {
	try {
	    return gameofthreegametableApi.invitePlayer(playerInvitation);
	} catch (ApiException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override public void acceptMove(Move move) {
	try {
	    gameofthreegametableApi.acceptMove(move);
	} catch (ApiException e) {
	    throw new RuntimeException(e);
	}
    }
}
