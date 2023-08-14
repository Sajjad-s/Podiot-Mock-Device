package ir.pod.podiotmockdevice;

import ir.pod.podiotmockdevice.devices.*;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Log4j2

public class PodiotMockDeviceApplication {

    static final String subscribeTopicDesired = "dvcout/DEVICE_ID/CLIENT_ID/twin/update/desired";
    private static final String MQTT_SERVER = "tcp://iot-mqtt.pod.ir:1883";
    private static final String reportedTopic = "dvcasy/twin/update/reported";
    static final String subscribeTopicReported = "dvcout/DEVICE_ID/CLIENT_ID/twin/update/reported";
    static final String subscribeTopicOrigin = "dvcout/DEVICE_ID/CLIENT_ID/#";
    static final String subscribeTopicRejected = "dvcout/DEVICE_ID/CLIENT_ID/twin/response/rejected";
    private static final String CONTENT = "$requestId\":\"AirQuality\",\"deviceTwinDocument\":{\"attributes\":{\"desired\"";
    private static Iterable<? extends Device> listDevices = initializeDevices();


    public static void main(String[] args) throws MqttException, InterruptedException {
        SpringApplication.run(PodiotMockDeviceApplication.class, args);
        List<Device> listDevices = initializeDevices();

        for (Device device : listDevices) {
            connectAndSetupDevice(device);
        }

        log.info("################## Waiting for incoming messages ##################");
    }

    private static List<Device> initializeDevices() {
        List<Device> listDevices = new ArrayList<>();

//        listDevices.add(new IrTransmitter("ktunhbi5j7c", "CBSTBJQH3IPDZR2BY3HNMIK"));

//        listDevices.add(new SmartRelay("ro5qfc4leeb", "677KXSMW74D261W680396O8"));

        listDevices.add(new DoorSensor("2vriuq7njz7", "NMBJ8JGPPKOOI0O5INREI5B"));
//
//        listDevices.add(new Thermostat("azmtolm7i2u", "2TBOK3W0ITJE3LX437XULH3"));
//
//        listDevices.add(new PowerSwitch("22xza8wflbn", "IMMNA153Z7K1BI3DX2I4S4S"));
//
//        listDevices.add(new SecuritySystem("22az6p6oui3", "MR0CMQ4LKXIBMQ8HSE4DXXF"));
//
//        listDevices.add(new AirQuality("oeob5pteetk", "DZG55QAH5HQSSPFETV0OA7H"));
//
//        listDevices.add(new SecurityCamera("peg7k230v3n", "54413VUXYW7JQ601B1C6SC3"));
//
//        listDevices.add(new PowerMeter("ikdxl6z0r0e", "A14EZ2GV7A2YAUPFBZP55KB"));
//
//        listDevices.add(new MovementDetection("7rinzm131en", "BG9BKSNLCDRRHCQY99KNVGD"));
//
//        listDevices.add(new WaterMeter("9q4jvnhj3s4", "C5GCH0D3CJYP4D0PJB6PD1Z"));
        // Add other devices...

        return listDevices;
    }

    private static void connectAndSetupDevice(Device device) throws MqttException, InterruptedException {
        new Thread(() -> {
            try (MqttClient mqttClient = new MqttClient(MQTT_SERVER, device.getClientId())) {
                MqttConnectOptions mqttConnectionOptions = new MqttConnectOptions();
                mqttConnectionOptions.setCleanSession(true);
                mqttConnectionOptions.setAutomaticReconnect(true);

                setupMqttCallback(mqttClient, device);
                mqttClient.connect(mqttConnectionOptions);
                Thread.sleep(2000);
                while (!mqttClient.isConnected()) {
                    Thread.sleep(1000);
                    mqttClient.subscribe(
                            subscribeTopicDesired
                                    .replace("DEVICE_ID", device.getDeviceId())
                                    .replace("CLIENT_ID", device.getClientId()));

                    log.info("################## Device ({}) Connected ##################", device.getDeviceId());
                }
                if (device instanceof Sensor) {
                    startSensorThread((Sensor) device, mqttClient);
                }
            } catch (Exception e) {
                log.error("################## Exception Happened Connection Failed  ⛔ ##################", e);
            }
        });
    }

    private static void setupMqttCallback(MqttClient mqttClient, Device device) throws MqttException {
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                log.error("Connection lost for device: {}", device.getDeviceId());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                log.info("Received message on topic: {}", topic);

                String stringPayload = new String(message.getPayload());

                try {
                    AsyncObjectWrapper wrapper = JsonUtility.getObject(
                            stringPayload,
                            AsyncObjectWrapper.class);

                    UpdateDeviceTwinDto dto = JsonUtility.getObject(
                            wrapper.getContent(),
                            UpdateDeviceTwinDto.class);

                    log.info("Device twin update content: {}", wrapper.getContent());

                    if (device instanceof Actuator) {
                        ((Actuator) device).processInput(mqttClient, reportedTopic, dto);
                    }
                } catch (JsonProcessException e) {
                    log.error("Json Parse Exception", e);
                } catch (MqttException e) {
                    log.error("MQTT Exception", e);
                } catch (Exception e) {
                    log.error("Unknown Exception", e);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                try {
                    if (token != null && token.getMessage() != null) {
                        log.info("Message delivered successfully: {}", new String(token.getMessage().getPayload()));
                    }
                } catch (MqttException e) {
                    log.error("Message delivery Error", e);
                    throw new RuntimeException(e);
                }
            }
        });


    }

    private static void startSensorThread(Sensor sensor, MqttClient mqttClient) {
        new Thread(() -> {
            while (true) {
                log.info("################## Sensors information published Type: {} DeviceId: {} ##################",
                        sensor.getClass().getSimpleName(), sensor.getDeviceId());

                try {
                    sensor.report(mqttClient, reportedTopic);
                    Thread.sleep(10000);
                } catch (Exception e) {
                    log.error("################## Sensor Thread Exception ##################", e);
                }
            }
        }).start();
    }
}