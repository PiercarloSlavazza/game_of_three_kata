package kata.game_of_three;

import java.util.UUID;

public interface GameTable {

    UUID invitePlayerAndReturnGameUuid(PlayerInvitation playerInvitation);
    void acceptMove(Move move);

}
