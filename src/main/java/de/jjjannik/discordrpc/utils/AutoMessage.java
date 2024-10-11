package de.jjjannik.discordrpc.utils;

import lombok.Getter;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Getter
public class AutoMessage {
    private static final String AUTH = "Your own discord token";
    private final String channel;
    private final String message;
    private final Timer timer;
    private int timesSent;

    /**
     * Creates a new automatic discord message object
     *
     * @param channelId The id of the channel where the message should send into
     * @param message   The content of the message
     */
    public AutoMessage(long channelId, String message) {
        this.message = message;
        this.channel = "https://discord.com/api/v9/channels/" + channelId + "/messages";

        timer = new Timer();
    }

    /**
     * Runs the auto message task
     *
     * @param period    In which period the message should send in Milliseconds
     * @param delay     Delay in Milliseconds after which the timer should start
     */
    public void run(long delay, long period) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendRequest(channel);
                timesSent++;
            }
        },new Date(), period); //one hour and 1 min -> 61 * 60000L
    }

    /**
     * Runs the auto message task with a maximum of sent messages
     *
     * @param period    In which period the message should send in Milliseconds
     * @param delay     Delay in Milliseconds after which the timer should start
     * @param maxSentAmount The amount of messages should be sent
     */
    public void run(long delay, long period, int maxSentAmount) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendRequest(channel);
                timesSent++;
                if (timesSent == maxSentAmount) AutoMessage.this.cancel();
            }
        },delay, period); //one hour and 1 min -> 61 * 60000L
    }

    /**
     * Cancels the task
     */
    public void cancel() {
        timer.cancel();
    }

    /**
     * @return The channel api link
     */
    public String getChannelLink() {
        return this.channel;
    }

    /**
     * Sends the message task to the Discord Message API
     *
     * @param targetURL The link of the channel where the message should send in
     */
    private void sendRequest(String targetURL) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("authorization", AUTH);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setChunkedStreamingMode(100);

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            try (OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
                osw.write("{\"content\":\""+ message +"\"}");
                osw.flush();
            }

            System.out.println(connection.getResponseCode() + ":" + connection.getResponseMessage());

            /*int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    System.out.println(response);
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }
    }
}