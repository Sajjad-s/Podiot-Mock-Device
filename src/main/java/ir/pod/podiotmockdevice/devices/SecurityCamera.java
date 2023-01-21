package ir.pod.podiotmockdevice.devices;

import ir.pod.podiotmockdevice.JsonProcessException;
import ir.pod.podiotmockdevice.JsonUtility;
import ir.pod.podiotmockdevice.RandomGenerator;
import ir.pod.podiotmockdevice.UpdateDeviceTwinDto;
import ir.pod.podiotmockdevice.devices.Util.DeviceUtility;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

/**
 * @author S-Serajzadeh
 * @Date 9/11/22
 * @Project PANEL IOT-Bank
 */

//    "name": "Security_camera_servers"
//    "id": "6sltft1y2yx"

public class SecurityCamera implements Sensor {

    String deviceId;
    String clientId;


    UpdateDeviceTwinDto updateDeviceTwinDto = new UpdateDeviceTwinDto();

    public SecurityCamera(String deviceId, String clientId) {
        this.deviceId = deviceId;
        this.clientId = clientId;
    }

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void report(MqttClient mqttClient, String reportedTopic) throws JsonProcessException, MqttException, InterruptedException {
        UpdateDeviceTwinDto instantiate = DeviceUtility.instantiate();

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "cpu_temp", RandomGenerator.generateRandom(100));
        System.out.println("SecurityCamera" +" "+ "Reported: " + "cpu_temp" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("cpu_temp"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "rack_temp", RandomGenerator.generateRandom(1000));
        System.out.println("SecurityCamera" +" "+ "Reported: " + "rack_temp" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("rack_temp"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "cpu_usage", RandomGenerator.generateRandom(100));
        System.out.println("SecurityCamera" +" "+ "Reported: " + "cpu_usage" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("cpu_usage"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "ram_usage", RandomGenerator.generateRandom(100));
        System.out.println("SecurityCamera" +" "+ "Reported: " + "ram_usage" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("ram_usage"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "storage_left", RandomGenerator.generateString("EMPTY", "FULL", "20", "40", "60", "80", "100"));
        System.out.println("SecurityCamera" +" "+ "Reported: " + "storage_left" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("storage_left"));

        mqttClient.publish(reportedTopic, new MqttMessage(JsonUtility.getStringJson(instantiate).getBytes(StandardCharsets.UTF_8)));
    }
}
