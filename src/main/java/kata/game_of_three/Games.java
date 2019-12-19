package kata.game_of_three;

import java.util.Optional;
import java.util.UUID;

public interface Games {

    void endGame(UUID gameUuid);
    Optional<Game> getGame(UUID gameUuid);

}
