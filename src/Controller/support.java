package Controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class support {

    @FXML
    private WebView webView;

    @FXML
    private void mail() {
        try {
            String recipient = "bookstore.java.1403@gmail.com";
            String subject = "پشتیبانی فروشگاه کتاب";
            String body = "سلام، لطفا پیام خود را اینجا بنویسید.";

            String mailtoLink = String.format("mailto:%s?subject=%s&body=%s",
                    recipient,
                    subject.replace(" ", "%20"),
                    body.replace(" ", "%20"));

            Desktop.getDesktop().browse(new URI(mailtoLink));

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        loadMap();
    }

    private void loadMap() {
        WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        String leafletMapHtml = "<html>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <style>html, body, #map { height: 100%; margin: 0; }</style>\n" +
                "    <link rel='stylesheet' href='https://unpkg.com/leaflet@1.7.1/dist/leaflet.css' />\n" +
                "    <script src='https://unpkg.com/leaflet@1.7.1/dist/leaflet.js'></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id='map'></div>\n" +
                "    <script>\n" +
                "        document.addEventListener('DOMContentLoaded', function() {\n" +
                "            var map = L.map('map').setView([38.057797143629436, 46.33841156959533], 15);\n" +
                "            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {\n" +
                "                attribution: '&copy; OpenStreetMap contributors'\n" +
                "            }).addTo(map);\n" +
                "            L.marker([38.057797143629436, 46.33841156959533]).addTo(map)\n" +
                "                .bindPopup('Location: 38.057797143629436, 46.33841156959533')\n" +
                "                .openPopup();\n" +
                "        });\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";

        webEngine.loadContent(leafletMapHtml, "text/html");
    }
}
