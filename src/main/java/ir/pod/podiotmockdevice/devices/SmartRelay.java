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
 * @Date 9/4/22
 * @Project PANEL IOT-Bank
 */

//    "name": "Smart_realy",
//    "id": "uztz81f5oo3"

public class SmartRelay implements Actuator {


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

    public SmartRelay(String deviceId, String clientId) {
        this.clientId = clientId;
        this.deviceId = deviceId;


    }

    @Override
    public void processInput(MqttClient client,
                             String reportedTopic,
                             UpdateDeviceTwinDto updateDeviceTwinDtoInput) throws JsonProcessException, MqttException, LineUnavailableException {
        System.out.println("Beep");
        Toolkit.getDefaultToolkit().beep();

        SoundUtils.tone(1000,500);

        UpdateDeviceTwinDto updateDeviceTwinDto =
                DeviceUtility.desiredToReported(
                        updateDeviceTwinDtoInput.getDeviceTwinDocument().getAttributes().getDesired(),
                        true,
                        true);

        DeviceUtility.selectiveInput(
                updateDeviceTwinDtoInput.getDeviceTwinDocument().getAttributes().getDesired(),
                updateDeviceTwinDto.getDeviceTwinDocument().getAttributes().getReported(),
                "state",
                "ON", "OFF");

        client.publish(reportedTopic, new MqttMessage(JsonUtility.getStringJson(updateDeviceTwinDto).getBytes(StandardCharsets.UTF_8)));

    }

}
