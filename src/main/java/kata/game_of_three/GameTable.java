package kata.game_of_three;

import java.util.UUID;

public interface GameTable {

    UUID invitePlayer(PlayerInvitation playerInvitation);
    void acceptMove(Move move);

}
