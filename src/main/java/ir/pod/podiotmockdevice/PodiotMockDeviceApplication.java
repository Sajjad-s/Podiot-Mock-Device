package ir.pod.podiotmockdevice;

import ir.pod.podiotmockdevice.devices.*;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Log4j2

public class PodiotMockDeviceApplication {

    static final String CONTENT = "$requestId\":\"AirQuality\",\"deviceTwinDocument\":{\"attributes\":{\"desired\"";

    static final String subscribeTopicDesired = "dvcout/DEVICE_ID/CLIENT_ID/twin/update/desired";
    static final String subscribeTopicReported = "dvcout/DEVICE_ID/CLIENT_ID/twin/update/reported";

    static final String subscribeTopicOrigin = "dvcout/DEVICE_ID/CLIENT_ID/#";

    static final String subscribeTopicRejected = "dvcout/DEVICE_ID/CLIENT_ID/twin/response/rejected";

    static final String reportedTopic = "dvcasy/twin/update/reported";

    static final String MQTT_SERVER = "tcp://iot-mqtt.pod.ir:1883";

    public static void main(String[] args) throws MqttException, InterruptedException {
        SpringApplication.run(PodiotMockDeviceApplication.class, args);
        List<Device> listDevices = new ArrayList<>();

        listDevices.add(new PowerSwitch("22xza8wflbn", "IMMNA153Z7K1BI3DX2I4S4S")); //13- PowerSwitch Branch ID: 1605 طوس کهن -- invalid



        for (Device device : listDevices) {

            MqttClient mqttClient = new MqttClient(MQTT_SERVER, device.getClientId());

            MqttConnectOptions mqttConnectionOptions = new MqttConnectOptions();
            mqttConnectionOptions.setCleanSession(true);
            mqttConnectionOptions.setAutomaticReconnect(true);
//            mqttConnectionOptions.setConnectionTimeout(10);

            try {
                mqttClient.connect(mqttConnectionOptions);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("##################      Exception Happened Connection Failed      ##################");
            }
            do {
                Thread.sleep(2000);
                System.out.println("##################                  ReCONNECT                     ##################");

            } while (!mqttClient.isConnected());

            mqttClient.subscribe(
                    subscribeTopicDesired
                            .replace("DEVICE_ID", device.getDeviceId())
                            .replace("CLIENT_ID", device.getClientId())
                    , (topic, message) -> {
                        System.out.println("##################     subscribeTopicOrigin for Device:  {}      ################## "
                                .replace(" {} ", device.getDeviceId()));


                        byte[] payload = message.getPayload();
                        String stringPayload = new String(payload);

                        try {

                            AsyncObjectWrapper wrapper = JsonUtility.getObject(
                                    stringPayload,
                                    AsyncObjectWrapper.class);

                            UpdateDeviceTwinDto dto = JsonUtility.getObject(
                                    wrapper.getContent(),
                                    UpdateDeviceTwinDto.class);

                            System.out.println(wrapper.getContent());

                            if (device instanceof Actuator) {
                                ((Actuator) device).processInput(mqttClient, reportedTopic, dto);
                            }
                        } catch (Exception e) {
                            log.error("sdkljfkjldsf");
                            e.printStackTrace();
                        }
                    });
            System.out.println("##################        Device ({})  Connected         ##################"
                    .replace("{}", device.getDeviceId()));


            if (device instanceof Sensor) {
                new Thread(() -> {
                    while (!"god is dead".isEmpty()) {
                        System.out.println("##################    Sensors information published      ##################");
                        try {
                            ((Sensor) device).report(mqttClient, reportedTopic);
                            Thread.sleep(10000);
                        } catch (JsonProcessException e) {
                            throw new RuntimeException(e);
                        } catch (MqttException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
        }
        System.out.println("##################            Waiting for get message 🤬          ##################");

    }

}


