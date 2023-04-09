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

        listDevices.add(new IrTransmitter("xd51v1hydmj", "96T4JAIGD4VANIGWG0TMFM6"));
        listDevices.add(new IrTransmitter("ktunhbi5j7c", "CBSTBJQH3IPDZR2BY3HNMIK"));

        listDevices.add(new SmartRelay("ft4wq19lkdw", "LL28CHAXWXGE2KLEQ89OSHD"));
        listDevices.add(new SmartRelay("9ukgpyjsm6p", "V7T99RHL214OQLLILS6ECHY"));
        listDevices.add(new SmartRelay("ro5qfc4leeb", "677KXSMW74D261W680396O8"));

        listDevices.add(new DoorSensor("dxxpivhirws", "XA0DG4PC1L3ZQBNURV92QKF"));
        listDevices.add(new DoorSensor("6cgu891dk7o", "WJDK3JD2XB2LG003IZXYCFD"));
        listDevices.add(new DoorSensor("2vriuq7njz7", "NMBJ8JGPPKOOI0O5INREI5B"));

        listDevices.add(new Thermostat("cfsdkffc050", "895G9VSV5DTODZ5B4SZXI9U"));
        listDevices.add(new Thermostat("azmtolm7i2u", "2TBOK3W0ITJE3LX437XULH3"));

        listDevices.add(new PowerSwitch("22xza8wflbn", "IMMNA153Z7K1BI3DX2I4S4S"));

        listDevices.add(new SecuritySystem("b54xbwda7fv", "ZWQIC89MAHMEHL60SA6UGOM"));
        listDevices.add(new SecuritySystem("22az6p6oui3", "MR0CMQ4LKXIBMQ8HSE4DXXF"));

        listDevices.add(new AirQuality("oeob5pteetk", "DZG55QAH5HQSSPFETV0OA7H"));

        listDevices.add(new SecurityCamera("6wh2r8oxazl", "S97AQYQR6IQO1XVJ8JW2JDG"));
        listDevices.add(new SecurityCamera("u2k9fbfvmnw", "F4SYP755REMIIOAVV1UM19Q"));
        listDevices.add(new SecurityCamera("peg7k230v3n", "54413VUXYW7JQ601B1C6SC3"));

        listDevices.add(new PowerMeter("ikdxl6z0r0e", "A14EZ2GV7A2YAUPFBZP55KB"));

        listDevices.add(new MovementDetection("7rinzm131en", "BG9BKSNLCDRRHCQY99KNVGD"));

        listDevices.add(new WaterMeter("9q4jvnhj3s4", "C5GCH0D3CJYP4D0PJB6PD1Z"));





        for (Device device : listDevices) {

            MqttClient mqttClient = new MqttClient(MQTT_SERVER, device.getClientId());

            MqttConnectOptions mqttConnectionOptions = new MqttConnectOptions();
            mqttConnectionOptions.setCleanSession(true);
            mqttConnectionOptions.setAutomaticReconnect(true);
//            mqttConnectionOptions.setConnectionTimeout(10);
            device.getClass().getName();
            try {
                mqttClient.connect(mqttConnectionOptions);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("##################      Exception Happened Connection Failed  ⛔   ##################");
            }
            do {
                Thread.sleep(2000);
                System.out.println("##################                                ReCONNECT                                 🟡 ##################");

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
                        } catch (JsonProcessException e) {
                            System.out.println("##################      Json Parse Exception Happened Connection Failed  ⛔   ##################");
                            throw new RuntimeException(e);
                        } catch (MqttException e) {
                            System.out.println("##################          MQTT Exception Happened Connection Failed    ⛔   ##################");
                            throw new RuntimeException(e);
                        } catch (Exception e) {
                            System.out.println("##################          Unknown Exception Happened Connection Failed    ⛔   ##################");
                            e.printStackTrace();
                        }
                    });
            System.out.println("##################                      Device ({})  Connected                     🟢 ##################"
                    .replace("{}", device.getDeviceId()));


            if (device instanceof Sensor) {
                new Thread(() -> {
                    while (!"god is dead".isEmpty()) {
                        System.out.println("##################     Sensors information published Type: " + device.getClass().getSimpleName() + " DeviceId: " + device.getDeviceId()  + "    ##############");
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
        System.out.println("##################                         Waiting for get message                       🟡 ##################");

    }

}


