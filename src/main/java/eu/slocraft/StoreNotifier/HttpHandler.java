package eu.slocraft.StoreNotifier;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

import static org.bukkit.Bukkit.getLogger;

public class HttpHandler {
    private Main plugin;

    protected HttpHandler(Main plugin) {
        this.plugin = plugin;
    }

    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public static void main(String color, String message, Main plugin) throws IOException, InterruptedException {
        HttpHandler obj = new HttpHandler(plugin);
        obj.sendPOST(color, message);
    }

    private void sendPOST(String color, String message) throws IOException, InterruptedException {
        String json = "{" +
                "\"embeds\": [" +
                "{" +
                "\"title\": \"Obvestilo nakupa v spletni trgovini\"," +
                "\"description\": \"" + message + "\"," +
                "\"color\": \"" + color + "\"" +
                "}" +
                "]" +
                "}";

        //getLogger().info(json);
        String url = plugin.getConfig().getString("apikey_url");

        assert url != null;
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(url))
                .setHeader("User-Agent", "StoreNotifier Plugin")
                .header("Content-Type", "application/json")
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);

        //HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        //getLogger().info("Donacija komanda");
    }
}

/*
{
  "embeds": [
    {
      "title": "Obvestilo nakupa v spletni trgovini",
      "description": "{0} je kupil rank {1} za ceno {2} €!",
      "color": 8191850
    }
  ]
}
 */
