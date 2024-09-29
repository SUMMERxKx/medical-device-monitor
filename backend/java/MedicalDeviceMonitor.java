package backend.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MedicalDeviceMonitor {
    private final ConcurrentLinkedQueue<DeviceData> dataQueue = new ConcurrentLinkedQueue<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final int PORT = 8080;
    private final String PYTHON_SERVER_URL = "http://localhost:5000/process_data";

    public void start() {
        scheduler.scheduleAtFixedRate(this::processData, 0, 1, TimeUnit.SECONDS);
        scheduler.submit(this::listenForDevices);
    }

    private void listenForDevices() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Listening for devices on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                scheduler.submit(() -> handleDeviceConnection(clientSocket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDeviceConnection(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                DeviceData data = DeviceData.fromString(line);
                if (data != null) {
                    receiveData(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveData(DeviceData data) {
        dataQueue.offer(data);
    }

    private void processData() {
        while (!dataQueue.isEmpty()) {
            DeviceData data = dataQueue.poll();
            sendToPythonServer(data);
            System.out.println("Processing data: " + data);
        }
    }

    private void sendToPythonServer(DeviceData data) {
        try {
            URL url = new URL(PYTHON_SERVER_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String jsonInputString = String.format("{\"deviceId\":\"%s\",\"metricName\":\"%s\",\"value\":%f}",
                    data.getDeviceId(), data.getMetricName(), data.getValue());

            try (java.io.OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Python server response: " + response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MedicalDeviceMonitor monitor = new MedicalDeviceMonitor();
        monitor.start();
    }
}