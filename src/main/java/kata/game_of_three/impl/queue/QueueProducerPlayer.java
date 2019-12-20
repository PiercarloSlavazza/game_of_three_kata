package kata.game_of_three.impl.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import kata.game_of_three.GameResult;
import kata.game_of_three.Move;
import kata.game_of_three.Player;
import kata.game_of_three.PlayerIdentifier;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueProducerPlayer implements Player {

    private static final String GAME_OF_THREE_PLAYERS_EVENTS_EXCHANGE_NAME = "game_of_three_players_events";

    private final PlayerIdentifier playerIdentifier;
    private final Connection connection;
    private final Channel channel;

    public QueueProducerPlayer(PlayerIdentifier playerIdentifier, ConnectionFactory connectionFactory) {
	this.playerIdentifier = playerIdentifier;
	try {
	    connection = connectionFactory.newConnection();
	    channel = connection.createChannel();
	    channel.exchangeDeclare(GAME_OF_THREE_PLAYERS_EVENTS_EXCHANGE_NAME, "direct");
	} catch (IOException | TimeoutException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override public PlayerIdentifier getIdentifier() {
	return playerIdentifier;
    }

    @Override public void playTurn(Move opponentMove) {
	ObjectMapper objectMapper = new ObjectMapper();
	try {
	    channel.basicPublish(GAME_OF_THREE_PLAYERS_EVENTS_EXCHANGE_NAME,
				 playerIdentifier.getId(),
				 null,
				 objectMapper.writeValueAsBytes(opponentMove));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override public void endGame(GameResult gameResult) {
	ObjectMapper objectMapper = new ObjectMapper();
	try {
	    channel.basicPublish(GAME_OF_THREE_PLAYERS_EVENTS_EXCHANGE_NAME,
				 playerIdentifier.getId(),
				 null,
				 objectMapper.writeValueAsBytes(gameResult));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
}
