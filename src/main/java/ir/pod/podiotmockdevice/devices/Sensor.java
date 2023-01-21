package ir.pod.podiotmockdevice.devices;

import ir.pod.podiotmockdevice.JsonProcessException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author S-Serajzadeh
 * @Date 9/12/22
 * @Project PANEL IOT-Bank
 */
public interface Sensor extends Device {

    void report(MqttClient mqttClient, String reportedTopic) throws JsonProcessException, MqttException, InterruptedException;



}
