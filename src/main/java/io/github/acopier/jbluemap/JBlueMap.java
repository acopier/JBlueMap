package io.github.acopier.jbluemap;

import io.github.acopier.jbluemap.clients.AsyncClient;
import io.github.acopier.jbluemap.clients.Client;
import io.github.acopier.jbluemap.collections.Collection;
import io.github.acopier.jbluemap.collections.MarkerCollection;
import io.github.acopier.jbluemap.collections.PlayerCollection;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class JBlueMap {
    private final Client client;
    private final AsyncClient asyncClient;


    public JBlueMap(String baseUrl) {
        String baseurl = baseUrl.replaceAll("/$", "");
        this.client = new Client(baseurl);
        this.asyncClient = new AsyncClient(baseurl);
    }


    // synchronous helpers
    public List<String> fetchMaps() throws IOException, InterruptedException {
        return client.fetchMaps();
    }


    public MarkerCollection fetchMarkerCollection(String world) throws IOException, InterruptedException {
        return client.fetchMarkerCollection(world);
    }


    public PlayerCollection fetchPlayerCollection(String world) throws IOException, InterruptedException {
        return client.fetchPlayerCollection(world);
    }


    public Collection fetchCollection(String world) throws Exception {
        return client.fetchCollection(world);
    }


    // asynchronous helpers
    public CompletableFuture<Settings> fetchMapsAsync() {
        return asyncClient.fetchMaps();
    }


    public CompletableFuture<MarkerCollection> fetchMarkerCollectionAsync(String world) {
        return asyncClient.fetchMarkerCollection(world);
    }


    public CompletableFuture<PlayerCollection> fetchPlayerCollectionAsync(String world) {
        return asyncClient.fetchPlayerCollection(world);
    }


    public CompletableFuture<Collection> fetchCollectionAsync(String world) {
        return asyncClient.fetchCollection(world);
    }
}