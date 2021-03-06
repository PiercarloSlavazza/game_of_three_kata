package kata.game_of_three.impl.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import kata.game_of_three.GameResult;
import kata.game_of_three.Move;
import kata.game_of_three.Player;
import kata.game_of_three.PlayerIdentifier;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class QueueProducerPlayer implements Player {

    static final String GAME_OF_THREE_PLAYERS_EVENTS_EXCHANGE_NAME = "game_of_three_players_events";

    private final PlayerIdentifier playerIdentifier;
    @SuppressWarnings("FieldCanBeLocal") private final Connection connection;
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
	try {
	    channel.queueDeclare(opponentMove.getOpponent().getId(),
				 false,
				 false,
				 true,
			     new HashMap<>());
	    channel.queueBind(opponentMove.getOpponent().getId(), GAME_OF_THREE_PLAYERS_EVENTS_EXCHANGE_NAME, opponentMove.getOpponent().getId());

	    ObjectMapper objectMapper = new ObjectMapper().registerModule(new Jdk8Module());

	    channel.basicPublish(GAME_OF_THREE_PLAYERS_EVENTS_EXCHANGE_NAME,
				 playerIdentifier.getId(),
				 null,
				 objectMapper.writeValueAsBytes(opponentMove));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override public void endGame(GameResult gameResult) {
	ObjectMapper objectMapper = new ObjectMapper().registerModule(new Jdk8Module());
	try {
	    channel.basicPublish(GAME_OF_THREE_PLAYERS_EVENTS_EXCHANGE_NAME,
				 playerIdentifier.getId(),
				 null,
				 objectMapper.writeValueAsBytes(gameResult));
	    channel.close();
	    connection.close();
	} catch (IOException | TimeoutException e) {
	    throw new RuntimeException(e);
	}
    }
}
