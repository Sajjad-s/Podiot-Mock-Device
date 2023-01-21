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

//    "name": "Power_meter",
//    "id": "du5lws7qfd5"

public class PowerMeter implements Sensor {

    String clientId;

    String deviceId;

    UpdateDeviceTwinDto updateDeviceTwinDto = new UpdateDeviceTwinDto();

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public PowerMeter(String deviceId, String clientId) {
        this.clientId = clientId;
        this.deviceId = deviceId;
    }

    @Override
    public void report(MqttClient mqttClient, String reportedTopic) throws JsonProcessException, MqttException, InterruptedException {
        UpdateDeviceTwinDto instantiate = DeviceUtility.instantiate();

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "power", RandomGenerator.generateString("0", "25", "30", "40", "50", "60", "70", "80", "90", "100"));
        System.out.println("PowerMeter" +" "+ "Reported: " + "power" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("power"));


        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "linkquality", RandomGenerator.generateRandom(100));
        System.out.println("PowerMeter" +" "+ "Reported: " + "linkquality" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("linkquality"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "last_seen", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        System.out.println("PowerMeter" +" "+ "Reported: " + "last_seen" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("last_seen"));

        mqttClient.publish(reportedTopic, new MqttMessage(JsonUtility.getStringJson(instantiate).getBytes(StandardCharsets.UTF_8)));
    }

}
