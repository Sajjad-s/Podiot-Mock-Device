package ir.pod.podiotmockdevice.devices;

import ir.pod.podiotmockdevice.*;
import ir.pod.podiotmockdevice.devices.Util.DeviceUtility;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author S-Serajzadeh
 * @Date 9/10/22
 * @Project PANEL IOT-Bank
 */

//    "name": "Movment_detection",
//    "id": "h5fefwpzh4n"

public class MovementDetection implements Sensor {

    String deviceId;

    String clientId;

    UpdateDeviceTwinDto updateDeviceTwinDto = new UpdateDeviceTwinDto();

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public MovementDetection(String deviceId, String clientId) {
        this.deviceId = deviceId;
        this.clientId = clientId;
    }

    @Override
    public void report(MqttClient mqttClient, String reportedTopic) throws JsonProcessException, MqttException, InterruptedException {
        UpdateDeviceTwinDto instantiate = DeviceUtility.instantiate();

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "status", RandomGenerator.generateString("CLOSE", "OPEN"));
        System.out.println("MovementDetection" +" "+ "Reported: " + "status" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("status"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "illuminance_lux", RandomGenerator.generateRandom(400));
        System.out.println("MovementDetection" +" "+ "Reported: " + "illuminance_lux" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("illuminance_lux"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "linkquality", RandomGenerator.generateRandom(100));
        System.out.println("MovementDetection" +" "+ "Reported: " + "linkquality" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("linkquality"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "last_seen", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        System.out.println("MovementDetection" +" "+ "Reported: " + "last_seen" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("last_seen"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "battery", RandomGenerator.generateRandom(100));
        System.out.println("MovementDetection" +" "+ "Reported: " + "battery" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("battery"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "voltage", RandomGenerator.generateRandom(1000));
        System.out.println("MovementDetection" +" "+ "Reported: " + "voltage" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("voltage"));

        mqttClient.publish(reportedTopic, new MqttMessage(JsonUtility.getStringJson(instantiate).getBytes(StandardCharsets.UTF_8)));

    }


}
