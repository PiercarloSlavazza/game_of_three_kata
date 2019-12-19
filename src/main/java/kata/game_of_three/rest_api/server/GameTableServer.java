package kata.game_of_three.rest_api.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jackson.DiscoverableSubtypeResolver;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class GameTableServer extends Application<GameTableServerConfiguration> {

    public static void main(String[] args) throws Exception {
	new GameTableServer().run(args);
    }

    @Override
    public String getName() {
	return "ne-anonymizer";
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
			/*
			These module are required by Dropwizard
			 */
			.registerModule(new GuavaModule())
			.setSubtypeResolver(new DiscoverableSubtypeResolver())
			/*
			These modules are required by the App
			 */
			.registerModule(new Jdk8Module());
	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	bootstrap.setObjectMapper(objectMapper);
    }

    @Override public void run(GameTableServerConfiguration gameTableServerConfiguration, Environment environment) {



	environment.healthChecks().register("is_alive", new AliveHealthCheck());
    }
}
