package ir.pod.podiotmockdevice.devices;

import ir.pod.podiotmockdevice.*;
import ir.pod.podiotmockdevice.devices.Util.DeviceUtility;
import ir.pod.podiotmockdevice.devices.Util.SoundUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.nio.charset.StandardCharsets;

/**
 * @author S-Serajzadeh
 * @Date 9/11/22
 * @Project PANEL IOT-Bank
 */


//    "name": "Thermostat",
//    "id": "4papiddb99b"

public class Thermostat implements Actuator, Sensor {


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


    public Thermostat(String deviceId, String clientId) {
        this.clientId = clientId;
        this.deviceId = deviceId;
    }

    @Override
    public void processInput(MqttClient mqttClient,
                             String reportedTopic,
                             UpdateDeviceTwinDto updateDeviceTwinDtoInput) throws JsonProcessException, MqttException, LineUnavailableException {
        Toolkit.getDefaultToolkit().beep();
        SoundUtils.tone(1000,500);
        UpdateDeviceTwinDto updateDeviceTwinDto =
                DeviceUtility.desiredToReported(
                        updateDeviceTwinDtoInput.getDeviceTwinDocument().getAttributes().getDesired(),
                        true,
                        true,
                        "occupied_heating_setpoint",
                        "occupied_cooling_setpoint"
                );

        DeviceUtility.selectiveInput(
                updateDeviceTwinDtoInput.getDeviceTwinDocument().getAttributes().getDesired(),
                updateDeviceTwinDto.getDeviceTwinDocument().getAttributes().getReported(),
                "system_mode",
                "cool", "heat", "fan only", "off");

        DeviceUtility.selectiveInput(
                updateDeviceTwinDtoInput.getDeviceTwinDocument().getAttributes().getDesired(),
                updateDeviceTwinDto.getDeviceTwinDocument().getAttributes().getReported(),
                "fan_mode",
                1, 2, 3, 4, 5);

        mqttClient.publish(reportedTopic, new MqttMessage(JsonUtility.getStringJson(updateDeviceTwinDto).getBytes(StandardCharsets.UTF_8)));

    }


    @Override
    public void report(MqttClient mqttClient, String reportedTopic) throws JsonProcessException, MqttException {
        UpdateDeviceTwinDto instantiate = DeviceUtility.instantiate();
        instantiate.getDeviceTwinDocument().getAttributes().getReported().put("local_temperature", RandomGenerator.generateRandom(100));
        System.out.println("Thermostat" +" "+ "Reported: " + "local_temperature" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("local_temperature"));

        instantiate.getDeviceTwinDocument().getAttributes().getReported().put("humidity", RandomGenerator.generateRandom(100));
        System.out.println("Thermostat" +" "+ "Reported: " + "humidity" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("humidity"));

        mqttClient.publish(reportedTopic, new MqttMessage(JsonUtility.getStringJson(instantiate).getBytes(StandardCharsets.UTF_8)));
    }
}
