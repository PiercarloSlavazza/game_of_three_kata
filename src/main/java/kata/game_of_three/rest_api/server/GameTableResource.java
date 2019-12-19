package kata.game_of_three.rest_api.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import kata.game_of_three.GameTable;
import kata.game_of_three.Move;
import kata.game_of_three.PlayerInvitation;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Api("/game_of_three/game_table")
@Path("/game_of_three/game_table")
@SwaggerDefinition(
		info = @Info(
				version = "v1.0",
				title = "Game Of Three Game Table API"
		)
)
public class GameTableResource {

    private final GameTable gameTable;

    GameTableResource(GameTable gameTable) {
	this.gameTable = gameTable;
    }

    @POST
    @Path("/player_invitation")
    public Response invitePlayer(PlayerInvitation playerInvitation) {
        gameTable.invitePlayer(playerInvitation);
        return Response.ok().build();
    }

    @POST
    @Path("/accept_move")
    public Response acceptMove(Move move) {
        gameTable.acceptMove(move);
	return Response.ok().build();
    }
}
