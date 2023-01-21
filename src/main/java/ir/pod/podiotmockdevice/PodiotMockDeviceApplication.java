package ir.pod.podiotmockdevice;

import ir.pod.podiotmockdevice.devices.*;
import lombok.SneakyThrows;
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

    public static void main(String[] args) throws MqttException, JsonProcessException, InterruptedException {
        SpringApplication.run(PodiotMockDeviceApplication.class, args);
        List<Device> listDevices = new ArrayList<>();

//        listDevices.add(new DoorSensor("u5k2tmafcdd", "QCRLWQQ7G337QJY3OKU1DBC")); //1- Door
//        listDevices.add(new DoorSensor("ktzgfcbdmtv", "TZYHA4HLMXU1UHTXCH63OO9")); //1- Door
//        listDevices.add(new DoorSensor("m92o9ugirf5", "VUX6VUZ7PKHSFPAMBOSPNDG")); //1- Door
//        listDevices.add(new DoorSensor("m92o9ugirf5", "VUX6VUZ7PKHSFPAMBOSPNDG")); //1- Door
//        listDevices.add(new DoorSensor("ksav801thf8", "MUQOQ3BYS1DCY2NR2K1PMW5")); //1- Door Branch ID: 1055


        listDevices.add(new DoorSensor("voa0gzio4bg", "CH8C9A5W492CLM5FBE121I7")); //1- Door Branch ID: 1605


//        listDevices.add(new MovementDetection("5q6i4rxdzgq", "5F39E9QQVG1SDW4E4D2G570")); //2- Movement
//        listDevices.add(new MovementDetection("5q6i4rxdzgq", "5F39E9QQVG1SDW4E4D2G570")); //2- Movement
//        listDevices.add(new MovementDetection("zvsweft2dnc", "WMF022L9DZ8OEXEZFRF93QT")); //2- Movement Branch ID: 1055

//        listDevices.add(new Thermostat("vaz2nky4l90", "GZPFDM65J3JCGOBUQ3HVRBK")); //3- Thermostat
//        listDevices.add(new Thermostat("sxw8p7ng94m", "WMF022L9DZ8OEXEZFRF93QT")); //3- Thermostat Branch ID: 1055
        listDevices.add(new Thermostat("rxvzjfbe5o9", "YESG7D57RAHGV80MWAL1CKT")); //3- Thermostat Branch ID: 1055

//        listDevices.add(new IrTransmitter("yvv22yvs7i5", "LPREA4JZ3NRXZSDY0NE3TWH")); //4- IR Transmitter
        listDevices.add(new IrTransmitter("lu6pulvp1qh", "LN1T91SCI93G02IYL6GQQG2")); //4- IR Transmitter Branch ID: 1055

//        listDevices.add(new SmartRelay("dkv4znx5f5u", "U0IE9A1UKBO32JNP4TJWAD2")); //5- Smart Relay
//        listDevices.add(new SmartRelay("p4a0ixquz37", "D5SDJNQVN1LHE5JU0KRDINE")); //5- Smart Relay
//        listDevices.add(new SmartRelay("i40eettzyra", "CBP6DG3B5F4IQ8N7D37DDSN")); //5- Smart Relay
//        listDevices.add(new SmartRelay("yp9glkogz8b", "DO5H2JTJE0XLS6B9S6EIAZJ")); //5- Smart Relay Branch ID: 1055

//        listDevices.add(new WaterMeter("ua5tgtbohup", "1NZX22WYT7OCXUEHUQBWF4O")); //6- Water Meter
//        listDevices.add(new WaterMeter("jmylxn99xfq", "45QAET7MJ1LD3MLT4ISW2ET")); //6- Water Meter Branch ID: 1055

//        listDevices.add(new PowerMeter("9y80na1bfse", "KYFTUBRB44Y62IK07YM059A")); //7- Power Meter
//        listDevices.add(new PowerMeter("mxpdn0ie1xc", "ZPDMMHQ0G71D9WYGEV2WKVX")); //7- Power Meter Branch ID: 1055

//        listDevices.add(new AirQuality("nwp1w2qupkt", "ZQH1JVMC9N9FTALO7O5X2PS")); //9- Air Quality
//        listDevices.add(new AirQuality("6lvcjovztze", "AXSZ71QO8SUV49DY3N6Q8CE")); //9- Air Quality Branch ID: 1055

//        listDevices.add(new SecuritySystem("r8quk5rxoia", "NZCJ35GLKJ9YLANX1ZRC9U5")); //11- Security System Interface
//        listDevices.add(new SecuritySystem("x7d1ngsrjar", "M6GLDF0C4W7J7WD0UNLQ7D0")); //11- Security System Interface Branch ID: 1055


//        listDevices.add(new SecurityCamera("roecpzy02fp", "CTEIFOBPS3BFVDBYGB54G6N")); //12- Security Camera Server
//        listDevices.add(new SecurityCamera("i1r1zb5cuz2", "4UV2Z7UQGZJXZ0DAPSXD52O")); //12- Security Camera Server Branch ID: 1055


//        listDevices.add(new PowerSwitch("wejh7voxsd1", "TU2O5RL6QRUVMTYKOQBVMVT")); //13- PowerSwitch
//        listDevices.add(new PowerSwitch("1ns23r8mjwx", "QX87HC8XGGWOZATK1RQB8IC")); //13- PowerSwitch Branch ID: 1055
        listDevices.add(new PowerSwitch("1z7wilg4i8f", "TXEKL1ET8GT8MOPOFJ3VM4P")); //13- PowerSwitch Branch ID: 1605 طوس کهن



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


