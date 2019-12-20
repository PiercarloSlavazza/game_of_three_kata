package kata.game_of_three.impl.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import kata.game_of_three.GameResult;
import kata.game_of_three.Move;
import kata.game_of_three.Player;
import kata.game_of_three.PlayerIdentifier;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static kata.game_of_three.impl.queue.QueueProducerPlayer.GAME_OF_THREE_PLAYERS_EVENTS_EXCHANGE_NAME;

public class QueueConsumerPlayer implements Player {

    private final Player player;
    @SuppressWarnings("FieldCanBeLocal") private final Connection connection;
    @SuppressWarnings("FieldCanBeLocal") private final Channel channel;

    public QueueConsumerPlayer(Player player, ConnectionFactory connectionFactory) {
	this.player = player;
	try {
	    connection = connectionFactory.newConnection();
	    channel = connection.createChannel();
	    channel.exchangeDeclare(GAME_OF_THREE_PLAYERS_EVENTS_EXCHANGE_NAME, "direct");

	    String queueName = channel.queueDeclare().getQueue();
	    channel.queueBind(queueName, GAME_OF_THREE_PLAYERS_EVENTS_EXCHANGE_NAME, player.getIdentifier().getId());

	    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	        byte[] message = delivery.getBody();
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new Jdk8Module());

		Optional<Move> move = tryToReadMove(message, objectMapper);
		if (move.isPresent()) {
		    move.ifPresent(this::playTurn);
		    return;
		}

		Optional<GameResult> gameResult = tryToReadGameResult(message, objectMapper);
		if (gameResult.isPresent()) {
		    gameResult.ifPresent(this::endGame);
		    return;
		}

		throw new IllegalStateException("unknown message from queue");
	    };
	    channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });

	} catch (IOException | TimeoutException e) {
	    throw new RuntimeException(e);
	}
    }

    private Optional<Move> tryToReadMove(byte[] jsonSerializedMove, ObjectMapper objectMapper) {
	try {
	    return Optional.of(objectMapper.readValue(jsonSerializedMove, Move.class));
	} catch (ClassCastException e) {
	    return Optional.empty();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private Optional<GameResult> tryToReadGameResult(byte[] jsonSerializedMove, ObjectMapper objectMapper) {
	try {
	    return Optional.of(objectMapper.readValue(jsonSerializedMove, GameResult.class));
	} catch (ClassCastException e) {
	    return Optional.empty();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override public PlayerIdentifier getIdentifier() {
	return player.getIdentifier();
    }

    @Override public void playTurn(Move opponentMove) {
        player.playTurn(opponentMove);
    }

    @Override public void endGame(GameResult gameResult) {
        player.endGame(gameResult);
    }
}
