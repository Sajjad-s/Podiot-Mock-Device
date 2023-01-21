package ir.pod.podiotmockdevice.devices;

import ir.pod.podiotmockdevice.JsonProcessException;
import ir.pod.podiotmockdevice.UpdateDeviceTwinDto;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import javax.sound.sampled.LineUnavailableException;

/**
 * @author S-Serajzadeh
 * @Date 9/12/22
 * @Project PANEL IOT-Bank
 */
public interface Actuator extends Device {

    void processInput(MqttClient client,
                      String reportedTopic,
                      UpdateDeviceTwinDto updateDeviceTwinDto) throws JsonProcessException, MqttException, LineUnavailableException;


}
