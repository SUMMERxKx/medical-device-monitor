package backend.java;

public class DeviceData {
    private final String deviceId;
    private final String metricName;
    private final double value;

    public DeviceData(String deviceId, String metricName, double value) {
        this.deviceId = deviceId;
        this.metricName = metricName;
        this.value = value;
    }

    public static DeviceData fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length == 3) {
            return new DeviceData(parts[0], parts[1], Double.parseDouble(parts[2]));
        }
        return null;
    }

    @Override
    public String toString() {
        return deviceId + "," + metricName + "," + value;
    }

    // Add getters if needed
    public String getDeviceId() {
        return deviceId;
    }

    public String getMetricName() {
        return metricName;
    }

    public double getValue() {
        return value;
    }
}