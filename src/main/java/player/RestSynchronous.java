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
                    "Message error: " + response.errorBody());
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

        /**
         * Ejemplo de POST Player
         */
        Player player = new Player();
        player.setName("John");
        player.setSurname("Doe");
        player.setPoints(50);
        Call<Player> callPlayer = playerService.createPlayer(player);
        Response<Player> responsePlayer= callPlayer.execute();

        if(responsePlayer.isSuccessful()) {
            Player playerResp = responsePlayer.body();
            System.out.println("Status code: " + responsePlayer.code() + System.lineSeparator() +
                    "POST player: " + playerResp);


            /**
             * Ejemplo de PUT player
             * Vamos a actualizar el Player que acabamos de crear usando
             * la response.
             */
            playerResp.setPoints(100);
            callPlayer = playerService.updatePlayer(playerResp);
            responsePlayer= callPlayer.execute();

            System.out.println("Status code: " + responsePlayer.code() + System.lineSeparator() +
                    "PUT player: " + responsePlayer.body());

            /**
             * Ejemplo de DELETE player
             */
            Call<Void> callDelete= playerService.deletePlayer(playerResp.getId());
            Response<Void> responseDelete= callDelete.execute();

            System.out.println("DELETE status code: " + responseDelete.code());

            /**
             * GET All para comprobar si se ha eliminado
             */
            call = playerService.getAllPlayer();
            response= call.execute();
            System.out.println("Comprobación del delete " +
                    "Status code: " + response.code() + System.lineSeparator() +
                    "GET players: " + response.body());




        } else {
            System.out.println("Status code: " + responsePlayer.code() +
                    "Message error: " + responsePlayer.errorBody());
        }

        /**
         * Ejemplo GET de un jugador
         */
        callPlayer = playerService.getPlayer(1L);
        responsePlayer = callPlayer.execute();

        if(responsePlayer.isSuccessful()) {
            System.out.println("GET ONE->Status code: " + responsePlayer.code() +
                    " Player: " + responsePlayer.body() );
        }





    }
}
