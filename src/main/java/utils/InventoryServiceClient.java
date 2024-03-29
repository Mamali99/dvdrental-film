package utils;


import jakarta.inject.Named;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Named
public class InventoryServiceClient {
    private static final String STORE_SERVICE_URL = "http://localhost:8082/inventories/film/";

    private final Client client;

    public InventoryServiceClient() {
        this.client = ClientBuilder.newClient();
    }

    public boolean getInventoriesByFilmId(int filmId) {

        WebTarget target = client.target(STORE_SERVICE_URL + filmId);

        Response response = target.request(MediaType.APPLICATION_JSON).get();
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return true;
        }
        else {
            return false;
        }

    }

}
