package fr.ynov.collection.service;

import com.google.gson.*;
import fr.ynov.collection.model.JeuVideo;
import fr.ynov.collection.model.Support;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RawgImporter {

    private static final String API_KEY = "008f4f8748d042f484b985132a0f54d4";
    private static final String BASE_URL = "https://api.rawg.io/api/games?page_size=10&key=" + API_KEY;

    public List<JeuVideo> fetchTopGames() {
        List<JeuVideo> jeux = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray results = json.getAsJsonArray("results");

            for (JsonElement element : results) {
                JsonObject game = element.getAsJsonObject();

                String titre = game.get("name").getAsString();
                String dateSortie = game.get("released").getAsString();
                int annee = Integer.parseInt(dateSortie.substring(0, 4));

                String editeur = "Inconnu";
                if (game.has("publishers") && game.get("publishers").getAsJsonArray().size() > 0) {
                    editeur = game.get("publishers").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                }

                double note = game.has("metacritic") && !game.get("metacritic").isJsonNull()
                        ? game.get("metacritic").getAsDouble() : 0;

                String jaquette = game.has("background_image") && !game.get("background_image").isJsonNull()
                        ? game.get("background_image").getAsString() : null;

                String plateforme = game.get("platforms").getAsJsonArray()
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("platform")
                        .get("name").getAsString();

                Support support = new Support(plateforme);
                JeuVideo jeu = new JeuVideo(titre, editeur, "Inconnu", annee, support);
                jeu.setNoteMetacritic(note);
                jeu.setJaquette(jaquette);
                jeux.add(jeu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jeux;
    }
}
