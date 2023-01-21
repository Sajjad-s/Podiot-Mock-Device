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

//    "name": "Security_system_interface_board"
//    "id": "wjmna63vfyo"

public class SecuritySystem implements Sensor {

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

    public  SecuritySystem(String deviceId, String clientId) {
        this.clientId = clientId;
        this.deviceId = deviceId;
    }

    @Override
    public void report(MqttClient mqttClient, String reportedTopic) throws JsonProcessException, MqttException, InterruptedException {
        UpdateDeviceTwinDto instantiate = DeviceUtility.instantiate();


        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "system", RandomGenerator.generateString("ARM", "DISARM"));
        System.out.println("SecuritySystem" +" "+ "Reported: " + "system" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("system"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "status", RandomGenerator.generateString("SAFE", "ALARM"));
        System.out.println("SecuritySystem" +" "+ "Reported: " + "status" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("status"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                "zones", RandomGenerator.generateIncreasingRandoms(10,100));
        System.out.println("SecuritySystem" +" "+ "Reported: " + "zones" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("zones"));


        mqttClient.publish(reportedTopic, new MqttMessage(JsonUtility.getStringJson(instantiate).getBytes(StandardCharsets.UTF_8)));
    }
}
