package kata.game_of_three.rest_api.server;

import com.codahale.metrics.health.HealthCheck;

public class AliveHealthCheck extends HealthCheck {

    @Override
    protected Result check() {
	return Result.healthy();
    }

}
