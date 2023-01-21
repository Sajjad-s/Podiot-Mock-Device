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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author S-Serajzadeh
 * @Date 9/11/22
 * @Project PANEL IOT-Bank
 */

//    "name": "Air_quality",
//    "id": "hj42pyw1nn3"

public class AirQuality implements Sensor {
    String clientId;

    String deviceId;

    int intervalTime;

    UpdateDeviceTwinDto updateDeviceTwinDto = new UpdateDeviceTwinDto();

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public AirQuality(String deviceId, String clientId) {
        this.clientId = clientId;
        this.deviceId = deviceId;

        this.intervalTime = intervalTime;
    }

    @Override
    public void report(MqttClient mqttClient, String reportedTopic) throws JsonProcessException, MqttException, InterruptedException {

         UpdateDeviceTwinDto instantiate = DeviceUtility.instantiate();

            instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                    "co2", RandomGenerator.generateRandom(400));
            System.out.println("AirQuality" +" "+ "Reported: " + "CO2" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("co2"));


            instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                    "temperature", RandomGenerator.generateRandom(100));
            System.out.println("AirQuality" +" "+ "Reported: " + "temperature" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("temperature"));


            instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                    "humidity", RandomGenerator.generateRandom(1000));
            System.out.println("AirQuality" +" "+ "Reported: " + "humidity" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("humidity"));

            instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                    "linkquality", RandomGenerator.generateRandom(100));
            System.out.println("AirQuality" +" "+ "Reported: " + "linkquality" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("linkquality"));

            instantiate.getDeviceTwinDocument().getAttributes().getReported().put(
                    "last_seen", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            System.out.println("AirQuality" +" "+ "Reported: " + "last_seen" + ": " +instantiate.getDeviceTwinDocument().getAttributes().getReported().get("last_seen"));

            mqttClient.publish(reportedTopic, new MqttMessage(JsonUtility.getStringJson(instantiate).getBytes(StandardCharsets.UTF_8)));

    }
}