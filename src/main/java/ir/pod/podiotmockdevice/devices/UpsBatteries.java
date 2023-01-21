package ir.pod.podiotmockdevice.devices;

import ir.pod.podiotmockdevice.JsonProcessException;
import ir.pod.podiotmockdevice.UpdateDeviceTwinDto;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author S-Serajzadeh
 * @Date 9/11/22
 * @Project PANEL IOT-Bank
 */

//    "name": "Ups_batteries",
//    "id": "irok1uwb3t5"

public class UpsBatteries implements Sensor {

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

    public UpsBatteries(String deviceId, String clientId) {
        this.clientId = clientId;
        this.deviceId = deviceId;
    }

    @Override
    public void report(MqttClient mqttClient, String reportedTopic) throws JsonProcessException, MqttException, InterruptedException {

    }
}
