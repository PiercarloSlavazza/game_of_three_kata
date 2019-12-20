package kata.game_of_three.rest_api.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

@SuppressWarnings("WeakerAccess") public class GameTableServerConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    public String rabbitMQHost;
    public Integer rabbitMQPort;
}
