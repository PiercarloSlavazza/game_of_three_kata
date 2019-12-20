package kata.game_of_three.shell;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lexicalscope.jewel.cli.CliFactory;
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

    @Option
    String getPlayerId();

    @Option
    String getOpponentId();
    boolean isOpponentId();

    @Option
    String getGameTableRestApiUrl();

    @Option
    String getRabbitMQHost();

    @Option
    Boolean getAutoPlay();
    Boolean isAutoPlay();

    @Option
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

	PlayGameOfThreeShellConfig config = CliFactory.parseArguments(PlayGameOfThreeShellConfig.class, args);
	log.info("start|configs" + config.toString());

	GameTable gameTable = new GameTableClient(buildGameTableRestApi(config.getGameTableRestApiUrl()));

	ConnectionFactory connectionFactory = new ConnectionFactory();
	connectionFactory.setHost(config.getRabbitMQHost());

	PlayerIdentifier playerIdentifier = new PlayerIdentifier(config.getPlayerId());

	Player player = buildPlayer(config.isAutoPlay() ? config.getAutoPlay() : false, playerIdentifier, gameTable);
	new QueueConsumerPlayer(player, connectionFactory);

	if (config.isOpponentId()) {
	    PlayerIdentifier opponentIdentifier = new PlayerIdentifier(config.getOpponentId());
	    int inception = config.isInception() ? config.getInception() : new Random().nextInt();
	    PlayerInvitation playerInvitation = new PlayerInvitation(playerIdentifier, opponentIdentifier, inception);
	    System.out.println(player.getIdentifier().getId() + " > starting game against [" + opponentIdentifier.getId() + "] with inception " + inception);
	    gameTable.invitePlayerAndReturnGameUuid(playerInvitation);
	} else {
	    System.out.println(player.getIdentifier().getId() + " > waiting to be invited to a game...");
	}
    }

}
