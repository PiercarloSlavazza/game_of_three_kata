package kata.game_of_three.shell;

import com.rabbitmq.client.ConnectionFactory;
import kata.game_of_three.PlayerIdentifier;
import kata.game_of_three.PlayerInvitation;
import kata.game_of_three.impl.GameTableImpl;
import kata.game_of_three.impl.InMemoryGames;
import kata.game_of_three.impl.RegistryPlayerFactory;
import kata.game_of_three.impl.autonomous_player.AutonomousPlayer;
import kata.game_of_three.impl.autonomous_player.AutonomousPlayerEventsLogger;
import kata.game_of_three.impl.queue.QueueConsumerPlayer;
import kata.game_of_three.impl.queue.QueueProducerPlayer;

import java.util.UUID;

public class RunAutonomousQueuedGameShell {

    public static void main(String[] args) {

	RegistryPlayerFactory registryPlayerFactory = new RegistryPlayerFactory();
	InMemoryGames games = new InMemoryGames();
	GameTableImpl gameTable = new GameTableImpl(games, registryPlayerFactory, UUID::randomUUID);
	AutonomousPlayerEventsLogger autonomousPlayerEventsLogger = new AutonomousPlayerEventsLogger();

	ConnectionFactory connectionFactory = new ConnectionFactory();
	connectionFactory.setHost("localhost");

	PlayerIdentifier player1Identifier = new PlayerIdentifier("P1");
	PlayerIdentifier player2Identifier = new PlayerIdentifier("P2");

	AutonomousPlayer player1 = new AutonomousPlayer(player1Identifier, gameTable, autonomousPlayerEventsLogger);
	new QueueConsumerPlayer(player1, true, connectionFactory);

	AutonomousPlayer player2 = new AutonomousPlayer(player2Identifier, gameTable, autonomousPlayerEventsLogger);
	new QueueConsumerPlayer(player2, true, connectionFactory);

	QueueProducerPlayer queueProducerPlayer1 = new QueueProducerPlayer(player1Identifier, connectionFactory);
	registryPlayerFactory.addPlayer(queueProducerPlayer1);

	QueueProducerPlayer queueProducerPlayer2 = new QueueProducerPlayer(player2Identifier, connectionFactory);
	registryPlayerFactory.addPlayer(queueProducerPlayer2);

	PlayerInvitation playerInvitation = new PlayerInvitation(player1Identifier, player2Identifier, 56);
	gameTable.invitePlayerAndReturnGameUuid(playerInvitation);
    }

}
