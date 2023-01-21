package ir.pod.podiotmockdevice.devices;

import ir.pod.podiotmockdevice.JsonProcessException;
import ir.pod.podiotmockdevice.JsonUtility;
import ir.pod.podiotmockdevice.UpdateDeviceTwinDto;
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

//    "name": "IR_transmitter",
//    "id": "sjhkj9l7llf"

public class IrTransmitter implements Actuator {

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

    public IrTransmitter(String deviceId, String clientId) {
        this.clientId = clientId;
        this.deviceId = deviceId;
    }


    @Override
    public void processInput(MqttClient mqttClient,
                             String reportedTopic,
                             UpdateDeviceTwinDto updateDeviceTwinDtoInput) throws JsonProcessException, MqttException, LineUnavailableException {
        Toolkit.getDefaultToolkit().beep();
        SoundUtils.tone(1000,500);        UpdateDeviceTwinDto updateDeviceTwinDto =
                DeviceUtility.desiredToReported(
                        updateDeviceTwinDtoInput.getDeviceTwinDocument().getAttributes().getDesired(),
                        true,
                        true,
                        "occupied_heating_setpoint",
                        "occupied_cooling_setpoint",
                        "fan_mode",
                        "write");

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
}
