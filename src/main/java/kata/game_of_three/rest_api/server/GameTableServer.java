package kata.game_of_three.rest_api.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jackson.DiscoverableSubtypeResolver;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import kata.game_of_three.impl.GameTableImpl;
import kata.game_of_three.impl.InMemoryGames;

import java.util.UUID;

public class GameTableServer extends Application<GameTableServerConfiguration> {

    public static void main(String[] args) throws Exception {
	new GameTableServer().run(args);
    }

    @Override
    public String getName() {
	return "game-of-three";
    }

    @Override
    public void initialize(Bootstrap<GameTableServerConfiguration> bootstrap) {

	bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
										new EnvironmentVariableSubstitutor(false)));

	bootstrap.addBundle(new SwaggerBundle<GameTableServerConfiguration>() {
	    @Override
	    protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(GameTableServerConfiguration configuration) {
		return configuration.swaggerBundleConfiguration;
	    }
	});

	bootstrap.addBundle(new Java8Bundle());

	ObjectMapper objectMapper = new ObjectMapper()
			.registerModule(new GuavaModule())
			.setSubtypeResolver(new DiscoverableSubtypeResolver())
			.registerModule(new Jdk8Module());
	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	bootstrap.setObjectMapper(objectMapper);
    }

    @Override public void run(GameTableServerConfiguration gameTableServerConfiguration, Environment environment) {

	InMemoryGames games = new InMemoryGames();
	GameTableImpl gameTable = new GameTableImpl(games, null, UUID::randomUUID);
	GameTableResource gameTableResource = new GameTableResource(gameTable);
	environment.jersey().register(gameTableResource);

	environment.healthChecks().register("is_alive", new AliveHealthCheck());
    }
}
