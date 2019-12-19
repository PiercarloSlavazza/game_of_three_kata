package kata.game_of_three.impl;

import com.rabbitmq.client.ConnectionFactory;
import kata.game_of_three.Player;
import kata.game_of_three.PlayerFactory;
import kata.game_of_three.PlayerIdentifier;
import kata.game_of_three.impl.queue.QueueProducerPlayer;

import java.util.Optional;

public class RabbitMQProducerPlayerFactory implements PlayerFactory {

    private final ConnectionFactory connectionFactory;

    public RabbitMQProducerPlayerFactory(ConnectionFactory connectionFactory) {
	this.connectionFactory = connectionFactory;
    }

    @Override public Optional<Player> buildPlayer(PlayerIdentifier playerIdentifier) {
	return Optional.of(new QueueProducerPlayer(playerIdentifier, connectionFactory));
    }
}
