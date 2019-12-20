package kata.game_of_three.shell;

import com.rabbitmq.client.ConnectionFactory;
import kata.game_of_three.PlayerInvitation;
import kata.game_of_three.impl.GameTableImpl;
import kata.game_of_three.impl.InMemoryGames;
import kata.game_of_three.impl.PlayerIdentifierImpl;
import kata.game_of_three.impl.RegistryPlayerFactory;
import kata.game_of_three.impl.io_player.IOPlayer;
import kata.game_of_three.impl.queue.QueueConsumerPlayer;
import kata.game_of_three.impl.queue.QueueProducerPlayer;

import java.util.UUID;

public class RunIOQueuedGameShell {

    public static void main(String[] args) {

	RegistryPlayerFactory registryPlayerFactory = new RegistryPlayerFactory();
	InMemoryGames games = new InMemoryGames();
	GameTableImpl gameTable = new GameTableImpl(games, registryPlayerFactory, UUID::randomUUID);

	ConnectionFactory connectionFactory = new ConnectionFactory();
	connectionFactory.setHost("localhost");

	PlayerIdentifierImpl player1Identifier = new PlayerIdentifierImpl("P1");
	PlayerIdentifierImpl player2Identifier = new PlayerIdentifierImpl("P2");

	IOPlayer player1 = new IOPlayer(player1Identifier, gameTable, System.in, System.out);
	new QueueConsumerPlayer(player1, connectionFactory);

	IOPlayer player2 = new IOPlayer(player2Identifier, gameTable, System.in, System.out);
	new QueueConsumerPlayer(player2, connectionFactory);

	QueueProducerPlayer queueProducerPlayer1 = new QueueProducerPlayer(player1Identifier, connectionFactory);
	registryPlayerFactory.addPlayer(queueProducerPlayer1);

	QueueProducerPlayer queueProducerPlayer2 = new QueueProducerPlayer(player2Identifier, connectionFactory);
	registryPlayerFactory.addPlayer(queueProducerPlayer2);

	PlayerInvitation playerInvitation = new PlayerInvitation(player1Identifier, player2Identifier, 56);
	gameTable.invitePlayerAndReturnGameUuid(playerInvitation);
    }

}
