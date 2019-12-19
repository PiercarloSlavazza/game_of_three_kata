package kata.game_of_three;

import java.util.Optional;

public interface PlayerFactory {

    Optional<Player> buildPlayer(PlayerIdentifier playerIdentifier);

}
