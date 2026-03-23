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
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AsyncClient {
    private final String baseUrl;
    private final HttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();


    public AsyncClient(String baseUrl) {
        this.baseUrl = baseUrl.replaceAll("/$", "");
        this.httpClient = HttpClient.newBuilder().build();
    }


    private CompletableFuture<Map<String, Object>> getJsonAsync(String url) {
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        return httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .thenApply(resp -> {
                    if (resp.statusCode() < 200 || resp.statusCode() >= 300) {
                        throw new RuntimeException("HTTP " + resp.statusCode() + " for " + url);
                    }
                    try {
                        return mapper.readValue(resp.body(), new TypeReference<Map<String, Object>>() {
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }


    public CompletableFuture<Settings> fetchMaps() {
        String link = String.format("%s/settings.json", baseUrl);
        return getJsonAsync(link).thenApply(Settings::fromResponse);
    }


    public CompletableFuture<MarkerCollection> fetchMarkerCollection(String world) {
        String link = String.format("%s/maps/%s/live/markers.json", baseUrl, world);
        return getJsonAsync(link).thenApply(MarkerCollection::fromResponse);
    }


    public CompletableFuture<PlayerCollection> fetchPlayerCollection(String world) {
        String link = String.format("%s/maps/%s/live/players.json", baseUrl, world);
        return getJsonAsync(link).thenApply(PlayerCollection::fromResponse);
    }


    public CompletableFuture<Collection> fetchCollection(String world) {
        CompletableFuture<MarkerCollection> m = fetchMarkerCollection(world);
        CompletableFuture<PlayerCollection> p = fetchPlayerCollection(world);
        return m.thenCombine(p, Collection::new);
    }
}
