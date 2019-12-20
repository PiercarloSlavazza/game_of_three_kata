package kata.game_of_three.shell;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.HelpRequestedException;
import com.lexicalscope.jewel.cli.Option;
import com.rabbitmq.client.ConnectionFactory;
import kata.game_of_three.GameTable;
import kata.game_of_three.Player;
import kata.game_of_three.PlayerIdentifier;
import kata.game_of_three.PlayerInvitation;
import kata.game_of_three.impl.GameTableClient;
import kata.game_of_three.impl.autonomous_player.AutonomousPlayer;
import kata.game_of_three.impl.autonomous_player.AutonomousPlayerEventsLogger;
import kata.game_of_three.impl.io_player.IOPlayer;
import kata.game_of_three.impl.queue.QueueConsumerPlayer;
import kata.game_of_three.rest_api.client.ApiClient;
import kata.game_of_three.rest_api.client.GameofthreegametableApi;
import net.dongliu.gson.GsonJava8TypeAdapterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

interface PlayGameOfThreeShellConfig {

    @SuppressWarnings("unused") @Option(helpRequest = true, description = "display help", shortName = "h")
    boolean getHelp();

    @Option(description = "The id of the current player")
    String getPlayerId();

    @Option(description = "The id of the player invited to join the game; optional, if you just want to be invited")
    String getOpponentId();
    boolean isOpponentId();

    @Option(description = "The URL of the Game Table Server, e.g. http://127.0.0.1:8700/game_of_three_rest_api")
    String getGameTableRestApiUrl();

    @Option(description = "The host of the RabbitMQ game events queue")
    String getRabbitMQHost();

    @Option(description = "The port of the RabbitMQ game events queue (optional)")
    Integer getRabbitMQPort();
    Boolean isRabbitMQPort();

    @Option(description = "Flag to enable autonomous play (optional, default false)")
    Boolean getAutoPlay();
    Boolean isAutoPlay();

    @Option(description = "Game inception number (optional, default is random)")
    Integer getInception();
    boolean isInception();
}

public class PlayGameOfThreeShell {

    private final static Logger log = LoggerFactory.getLogger(PlayGameOfThreeShell.class);

    private static Player buildAutonomousPlayer(PlayerIdentifier playerIdentifier, GameTable gameTable) {
        return new AutonomousPlayer(playerIdentifier, gameTable, new AutonomousPlayerEventsLogger());
    }

    private static Player buildIOPlayer(PlayerIdentifier playerIdentifier, GameTable gameTable) {
        return new IOPlayer(playerIdentifier, gameTable, System.in, System.out);
    }

    private static Player buildPlayer(boolean autoPlay, PlayerIdentifier playerIdentifier, GameTable gameTable) {
        return autoPlay ? buildAutonomousPlayer(playerIdentifier, gameTable) : buildIOPlayer(playerIdentifier, gameTable);
    }

    private static GameofthreegametableApi buildGameTableRestApi(String gameTableRestApiUrl) {
	Gson gson = new GsonBuilder().
			registerTypeAdapterFactory(new GsonJava8TypeAdapterFactory()).
			setDateFormat("YYYY-MM-dd'T'HH:mm:ss.sssZ").
			create();

	ApiClient gameTableApiClient = new ApiClient();
	gameTableApiClient.setBasePath(gameTableRestApiUrl);
	gameTableApiClient.getJSON().setGson(gson);
	gameTableApiClient.getHttpClient().setReadTimeout(60, TimeUnit.SECONDS);
	gameTableApiClient.getHttpClient().setReadTimeout(10, TimeUnit.SECONDS);

	return new GameofthreegametableApi(gameTableApiClient);
    }

    public static void main(String[] args) {

        try {
	    PlayGameOfThreeShellConfig config = CliFactory.parseArguments(PlayGameOfThreeShellConfig.class, args);
	    log.info("start|configs" + config.toString());

	    GameTable gameTable = new GameTableClient(buildGameTableRestApi(config.getGameTableRestApiUrl()));

	    ConnectionFactory connectionFactory = new ConnectionFactory();
	    connectionFactory.setHost(config.getRabbitMQHost());
	    if (config.isRabbitMQPort())
		connectionFactory.setPort(config.getRabbitMQPort());

	    PlayerIdentifier playerIdentifier = new PlayerIdentifier(config.getPlayerId());

	    boolean autoPlay = config.isAutoPlay() ? config.getAutoPlay() : false;
	    Player player = buildPlayer(autoPlay, playerIdentifier, gameTable);

	    /*
	    If player has been started without an opponent id, just do not close automatically the connections as the game ends
	    because the player might be involved in some other games, or can be invited one more time.
	     */
	    boolean closeQueueConnectionOnEndGame = config.isOpponentId();
	    new QueueConsumerPlayer(player, closeQueueConnectionOnEndGame, connectionFactory);

	    if (config.isOpponentId()) {
		PlayerIdentifier opponentIdentifier = new PlayerIdentifier(config.getOpponentId());
		int inception = config.isInception() ? config.getInception() : (new Random().nextInt() & Integer.MAX_VALUE);
		PlayerInvitation playerInvitation = new PlayerInvitation(playerIdentifier, opponentIdentifier, inception);
		System.out.println(player.getIdentifier().getId() + " > starting game against [" + opponentIdentifier.getId() + "] with inception " + inception);
		gameTable.invitePlayerAndReturnGameUuid(playerInvitation);
	    } else {
		System.out.println(player.getIdentifier().getId() + " > waiting to be invited to a game...");
	    }
	} catch (HelpRequestedException helpRequestedException) {
	    System.out.println(helpRequestedException.getMessage());
	}
    }

}
