package player;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * Para probar este código hay que tener en marcha un backend Spring
 * que ofrezca un API REST de Jugadores y equipos.
 * https://github.com/alfredorueda/BaloncestoREST_API
 */
public class RestSynchronous {
    private static Retrofit retrofit;

    public static void main(String[] args) throws IOException {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlayerService playerService = retrofit.create(PlayerService.class);


        /**
         * Ejemplo de GET All Players
         */
        Call<List<Player>> call = playerService.getAllPlayer();
        Response<List<Player>> response= call.execute();

        if(response.isSuccessful()) {
            List<Player> playerList = response.body();
            System.out.println("Status code: " + response.code() + System.lineSeparator() +
                    "GET all players: " + playerList);
        } else {
            System.out.println("Status code: " + response.code() +
                    "Message error: " + response.message());
        }

        /**
         * Ejemplo de llamada a un endpoint inexistente
         */
        call = playerService.getError();
        response= call.execute();

        if(!response.isSuccessful()) {
            System.out.println("Status code: " + response.code() +
                    " Message error: " + response.raw() );
        }


    }
}
