package ir.pod.podiotmockdevice.devices;

import ir.pod.podiotmockdevice.JsonProcessException;
import ir.pod.podiotmockdevice.UpdateDeviceTwinDto;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author S-Serajzadeh
 * @Date 9/5/22
 * @Project PANEL IOT-Bank
 */
public interface Device {
    String getDeviceId();

    String getClientId();
}


