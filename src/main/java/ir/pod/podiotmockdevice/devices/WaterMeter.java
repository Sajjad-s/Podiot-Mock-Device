package ir.pod.podiotmockdevice.devices;

import ir.pod.podiotmockdevice.*;
import ir.pod.podiotmockdevice.devices.Util.DeviceUtility;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

/**
 * @author S-Serajzadeh
 * @Date 9/5/22
 * @Project PANEL IOT-Bank
 */

//    "name": "Water_meter",
//    "id": "o3cnandrdjd"

public class WaterMeter implements Sensor {


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

    public WaterMeter(String deviceId, String clientId) {
        this.clientId = clientId;
        this.deviceId = deviceId;
    }

    @Override
    public void report(MqttClient mqttClient, String reportedTopic) throws JsonProcessException, MqttException, InterruptedException {

        UpdateDeviceTwinDto instantiate = DeviceUtility.instantiate();

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "water", RandomGenerator.generateRandom(999999999));
        System.out.println("WaterMeter" +" "+ "Reported: " + "water" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("water"));

        mqttClient.publish(reportedTopic, new MqttMessage(JsonUtility.getStringJson(instantiate).getBytes(StandardCharsets.UTF_8)));
    }

}
