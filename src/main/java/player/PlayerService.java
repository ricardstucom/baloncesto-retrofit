package player;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface PlayerService {
    @GET("/players")
    Call<List<Player>> getAllPlayer();

    @GET("/playersError")
    Call<List<Player>> getError();

    @POST("/players")
    Call<Player> createPlayer(@Body Player player);


    @PUT("/players")
    Call<Player> updatePlayer(@Body Player player);

    @DELETE("/players/{id}")
    Call<Void> deletePlayer(@Path("id") Long id);
}