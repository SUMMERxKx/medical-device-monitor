package backend.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MedicalDeviceMonitor {
    private final ConcurrentLinkedQueue<DeviceData> dataQueue = new ConcurrentLinkedQueue<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final int PORT = 8080;

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
            // Send data to Python script for processing (implement in step 2)
            System.out.println("Processing data: " + data);
        }
    }

    public static void main(String[] args) {
        MedicalDeviceMonitor monitor = new MedicalDeviceMonitor();
        monitor.start();
    }
}