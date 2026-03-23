package io.github.acopier.jbluemap.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.acopier.jbluemap.Settings;
import io.github.acopier.jbluemap.collections.Collection;
import io.github.acopier.jbluemap.collections.MarkerCollection;
import io.github.acopier.jbluemap.collections.PlayerCollection;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private final String baseUrl;
    private final HttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();


    public Client(String baseUrl) {
        this.baseUrl = baseUrl.replaceAll("/$", "");
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }


    private Map<String, Object> getJson(String url) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() < 200 || resp.statusCode() >= 300) {
            throw new IOException("HTTP " + resp.statusCode() + " for " + url);
        }
        return mapper.readValue(resp.body(), new TypeReference<>() {
        });
    }


    private Map<String, Object> getMarkersJson(String world) throws IOException, InterruptedException {
        String link = String.format("%s/maps/%s/live/markers.json", baseUrl, world);
        return getJson(link);
    }


    private Map<String, Object> getPlayersJson(String world) throws IOException, InterruptedException {
        String link = String.format("%s/maps/%s/live/players.json", baseUrl, world);
        return getJson(link);
    }


    public List<String> fetchMaps() throws IOException, InterruptedException {
        String link = String.format("%s/settings.json", baseUrl);
        Map<String, Object> settingsResponse = getJson(link);
        Settings settings = Settings.fromResponse(settingsResponse);
        return settings.maps();
    }


    public MarkerCollection fetchMarkerCollection(String world) throws IOException, InterruptedException {
        Map<String, Object> markersResponse = getMarkersJson(world);
        return MarkerCollection.fromResponse(markersResponse);
    }


    public PlayerCollection fetchPlayerCollection(String world) throws IOException, InterruptedException {
        Map<String, Object> playersResponse = getPlayersJson(world);
        return PlayerCollection.fromResponse(playersResponse);
    }


    public Collection fetchCollection(String world) throws Exception {
        ExecutorService ex = Executors.newFixedThreadPool(2);
        try {
            CompletableFuture<MarkerCollection> markersF = CompletableFuture.supplyAsync(() -> {
                try {
                    return fetchMarkerCollection(world);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, ex);
            CompletableFuture<PlayerCollection> playersF = CompletableFuture.supplyAsync(() -> {
                try {
                    return fetchPlayerCollection(world);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, ex);
            MarkerCollection mc = markersF.get();
            PlayerCollection pc = playersF.get();
            return new Collection(mc, pc);
        } finally {
            ex.shutdown();
        }
    }
}