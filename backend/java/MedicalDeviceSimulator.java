package backend.java;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MedicalDeviceSimulator {
    private static final String[] DEVICE_IDS = {"ECG_001", "BP_002", "SPO2_003"};
    private static final String[] METRICS = {"heart_rate", "blood_pressure", "oxygen_saturation"};
    private static final Random RANDOM = new Random();
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(DEVICE_IDS.length);

        for (String deviceId : DEVICE_IDS) {
            executor.scheduleAtFixedRate(() -> sendData(deviceId), 0, 1, TimeUnit.SECONDS);
        }
    }

    private static void sendData(String deviceId) {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String metric = METRICS[RANDOM.nextInt(METRICS.length)];
            double value = generateValue(metric);
            String data = String.format("%s,%s,%.2f", deviceId, metric, value);

            out.println(data);
            System.out.println("Sent: " + data);

        } catch (Exception e) {
            System.err.println("Error sending data for " + deviceId + ": " + e.getMessage());
        }
    }

    private static double generateValue(String metric) {
        switch (metric) {
            case "heart_rate":
                return 60 + RANDOM.nextDouble() * 40; // 60-100 bpm
            case "blood_pressure":
                return 80 + RANDOM.nextDouble() * 60; // 80-140 mmHg (simplification)
            case "oxygen_saturation":
                return 95 + RANDOM.nextDouble() * 5; // 95-100%
            default:
                return RANDOM.nextDouble() * 100;
        }
    }
}
