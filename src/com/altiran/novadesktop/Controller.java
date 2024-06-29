package com.altiran.novadesktop;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Controller {
    HashMap<String, String> map = new LinkedHashMap<>();
    ComboBox<String> dropDownVoices = initCombo();
    ListView<HBox> listView = new ListView<>();
    TextArea textBox;
    @FXML
    private BorderPane border;

    void refreshScene() throws InterruptedException, IOException {
        Thread.sleep(5000);
        System.out.println("Awake now fetch");
        loadData();
    }

    void playSound(String uri) {
        System.out.println("clicked" + uri);
        Media sound = new Media(uri);
        MediaPlayer mp = new MediaPlayer(sound);
        mp.play();
    }

    public void performPostCall(String requestURL, HashMap<String, String> postDataParams) {
        try {
            URL url = new URL(requestURL);
            JSONObject postData = getPostDataString(postDataParams);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000); // 15 seconds
            conn.setConnectTimeout(15000); // 15 seconds
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // Write POST data to connection
            try (DataOutputStream writer = new DataOutputStream(conn.getOutputStream())) {
                writer.write(postData.toString().getBytes());
            }

            // Get HTTP response code
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read response body
            StringBuilder response = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    responseCode >= 200 && responseCode < 300 ? conn.getInputStream() : conn.getErrorStream()))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
            }

            // Print response for debugging
            System.out.println("Response Content: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getPostDataString(HashMap<String, String> params) {
        JSONObject jsonObject = new JSONObject();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }

        return jsonObject;
    }

    String getData() throws IOException {
        URL url = new URL("https://opcb6awum8.execute-api.us-east-1.amazonaws.com/prod?postId=*");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Read response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Print response for debugging
        System.out.println(response);

        return response.toString();
    }

    public EventHandler<Event> createSolButtonHandler(String uri) {
        return event -> playSound(uri);
    }

    public EventHandler<Event> quitButtonHandler() {
        return event -> Platform.exit();
    }

    public EventHandler<Event> postButtonHandler() {
        return event -> {
            HashMap<String, String> tem = new HashMap<>();

            // Retrieve selected voice and text
            String voice = dropDownVoices.getValue();
            String text = textBox.getText();

            // Populate the temporary map
            tem.put("voice", map.getOrDefault(voice, ""));
            tem.put("text", text);

            // Print for debugging
            System.out.println(voice + " " + text);

            // Perform POST request
            performPostCall("https://opcb6awum8.execute-api.us-east-1.amazonaws.com/prod", tem);

            // Refresh the scene (assuming it handles UI refresh)
            try {
                refreshScene();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
                // Handle or log the exception as needed
            }
        };
    }

    protected void initMap() {
        // English - American
        map.put("Ivy [English - American]", "Ivy");
        map.put("Joanna [English - American]", "Joanna");
        map.put("Joey [English - American]", "Joey");
        map.put("Justin [English - American]", "Justin");
        map.put("Kendra [English - American]", "Kendra");
        map.put("Kimberly [English - American]", "Kimberly");
        map.put("Salli [English - American]", "Salli");

        // English - Australian
        map.put("Nicole [English - Australian]", "Nicole");
        map.put("Russell [English - Australian]", "Russell");

        // English - British
        map.put("Emma [English - British]", "Emma");
        map.put("Brian [English - British]", "Brian");
        map.put("Amy [English - British]", "Amy");

        // English - Indian
        map.put("Raveena [English - Indian]", "Raveena");

        // Bengali
        map.put("Rahul [Bengali]", "Rahul");
        map.put("Riya [Bengali]", "Riya");

        // English - Welsh
        map.put("Geraint [English - Welsh]", "Geraint");

        // Brazilian Portuguese
        map.put("Ricardo [Brazilian Portuguese]", "Ricardo");
        map.put("Vitoria [Brazilian Portuguese]", "Vitoria");

        // Dutch
        map.put("Lotte [Dutch]", "Lotte");
        map.put("Ruben [Dutch]", "Ruben");

        // French
        map.put("Mathieu [French]", "Mathieu");
        map.put("Celine [French]", "Celine");

        // Canadian French
        map.put("Chantal [Canadian French]", "Chantal");

        // German
        map.put("Marlene [German]", "Marlene");

        // Icelandic
        map.put("Dora [Icelandic]", "Dora");
        map.put("Karl [Icelandic]", "Karl");

        // Italian
        map.put("Carla [Italian]", "Carla");
        map.put("Giorgio [Italian]", "Giorgio");

        // Japanese
        map.put("Mizuki [Japanese]", "Mizuki");

        // Norwegian
        map.put("Liv [Norwegian]", "Liv");

        // Polish
        map.put("Maja [Polish]", "Maja");
        map.put("Jan [Polish]", "Jan");
        map.put("Ewa [Polish]", "Ewa");

        // Portuguese
        map.put("Cristiano [Portuquese]", "Cristiano");
        map.put("Ines [Portuquese]", "Ines");

        // Romanian
        map.put("Carmen [Romanian]", "Carmen");

        // Russian
        map.put("Maxim [Russian]", "Maxim");
        map.put("Tatyana [Russian]", "Tatyana");

        // Spanish
        map.put("Enrique [Spanish]", "Enrique");
        map.put("Penelope [US Spanish]", "Penelope");
        map.put("Miguel [US Spanish]", "Miguel");

        // Castilian Spanish
        map.put("Conchita [Castilian Spanish]", "Conchita");

        // Swedish
        map.put("Astrid [Swedish]", "Astrid");

        // Turkish
        map.put("Filiz [Turkish]", "Filiz");

        // Welsh
        map.put("Gwyneth [Welsh]", "Gwyneth");
    }

    private ComboBox<String> initCombo() {
        ComboBox<String> cb = new ComboBox<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            cb.getItems().add(entry.getKey());
        }

        cb.setPromptText("Select Voice");

        return cb;
    }

    private String clipText(String s) {
        final int MAX_LENGTH = 40;

        if (s.length() > MAX_LENGTH) {
            s = s.substring(0, MAX_LENGTH) + "...";
        }

        return "\"" + s + "\"";
    }

    public void loadData() throws IOException {
        // Retrieve data as JSON array
        JSONArray arr = new JSONArray(getData());

        // Clear existing items in the ListView
        listView.getItems().clear();

        // Iterate through JSON array and populate ListView
        for (int i = 0; i < arr.length(); i++) {
            // Retrieve JSON object for each item
            final JSONObject jsonObject = arr.getJSONObject(i);

            // Create an HBox for each item
            HBox hb = new HBox();
            hb.setSpacing(150);
            hb.setAlignment(Pos.CENTER);

            // Create Text nodes for voice and text fields
            Text voice = new Text(jsonObject.getString("voice"));
            voice.getStyleClass().add("white_text");
            voice.setTextAlignment(TextAlignment.LEFT);

            Text text = new Text(clipText(jsonObject.getString("text")));
            text.getStyleClass().add("white_text");

            // Create a 'Play' button for each item
            Button btn = new Button("Play");
            btn.getStyleClass().add("playButton");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, createSolButtonHandler(jsonObject.getString("url")));

            // Add components to HBox
            hb.getChildren().addAll(voice, text, btn);

            // Add HBox to ListView
            listView.getItems().add(hb);
        }

        // Refresh the ListView to reflect changes
        listView.refresh();
    }

    public void initialize() throws IOException {
        // Create a vertical box container
        VBox vBox = new VBox(20);
        vBox.getStyleClass().add("vbox");

        // Set the VBox as the center of the border pane
        border.setCenter(vBox);

        // Load initial data
        loadData();

        // Add a ListView to the VBox
        vBox.getChildren().add(listView);

        // Create a horizontal box for posting content
        HBox postBox = new HBox();

        // Create a text area for entering text
        textBox = new TextArea();
        textBox.getStyleClass().add("textField");
        textBox.setPromptText("Enter Text");

        // Initialize the necessary parts (like maps and dropdowns)
        initMap();
        dropDownVoices = initCombo();

        // Create a 'Say It' button
        Button postButton = new Button("Say It");
        postButton.getStyleClass().add("postButton");

        // Create a quit button
        Button quitButton = new Button();
        quitButton.getStyleClass().add("quit__button");
        quitButton.setMinSize(148, 148);
        quitButton.setMaxSize(148, 148);

        // Create a vertical box for quit actions
        VBox quitPostBox = new VBox(20);
        quitPostBox.getChildren().addAll(postButton, quitButton);
        quitPostBox.setAlignment(Pos.CENTER);

        // Add components to the postBox
        postBox.getChildren().addAll(dropDownVoices, textBox, quitPostBox);
        postBox.setSpacing(40);

        // Event handlers for buttons
        postButton.addEventHandler(MouseEvent.MOUSE_CLICKED, postButtonHandler());
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, quitButtonHandler());

        // Add postBox to the main VBox
        vBox.getChildren().add(postBox);
    }
}


