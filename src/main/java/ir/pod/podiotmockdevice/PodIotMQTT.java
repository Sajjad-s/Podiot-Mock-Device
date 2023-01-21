//package ir.pod.podiotmockdevice;
//
///**
// * @author S-Serajzadeh
// * @Date 9/4/22
// * @Project PANEL IOT-Bank
// */
//
//import ir.pod.podiotmockdevice.devices.Device;
//import ir.pod.podiotmockdevice.devices.WaterMeter;
//import lombok.extern.log4j.Log4j2;
//import org.eclipse.paho.client.mqttv3.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Log4j2
//public class PodIotMQTT {
//
//    static final String CONTENT = "$requestId\":\"AirQuality\",\"deviceTwinDocument\":{\"attributes\":{\"desired\"";
//
//    static final String subscribeTopicDesired = "dvcout/DEVICE_ID/CLIENT_ID/twin/update/desired";
//    static final String subscribeTopicReported = "dvcout/DEVICE_ID/CLIENT_ID/twin/update/reported";
//
//    static final String subscribeTopicOrigin = "dvcout/DEVICE_ID/CLIENT_ID/#";
//
//    static final String subscribeTopicRejected = "dvcout/DEVICE_ID/CLIENT_ID/twin/response/rejected";
//
//    static final String reportedTopic = "dvcasy/twin/update/reported";
//
//    static final String MQTT_SERVER = "tcp://iot-mqtt.pod.ir:1883";
//
//    public static void main(String[] args) throws MqttException, JsonProcessException, InterruptedException {
//
//        List<Device> listDevices = new ArrayList<>();
//        listDevices.add(new WaterMeter("0qcvlldfemr", "90NCYF3KNBVRSCXDF5ORE4F")); //Smart Relay
////        listDevices.add(new SmartRelay("b8i3sgiczpp", "TIO5HPDBWWUEAL9ZLJTHWPK")); //Water Meter
//
//        for (Device device : listDevices) {
//
//            MqttClient mqttClient = new MqttClient(MQTT_SERVER, device.getClientId());
//
//            MqttConnectOptions mqttConnectionOptions = new MqttConnectOptions();
//            mqttConnectionOptions.setCleanSession(true);
//            mqttConnectionOptions.setAutomaticReconnect(true);
//            mqttConnectionOptions.setConnectionTimeout(10);
//
//            mqttClient.connect(mqttConnectionOptions);
//            do {
//                Thread.sleep(2000);
//                System.out.println("🥰");
//            } while (!mqttClient.isConnected());
//
//            mqttClient.subscribe(
//                    subscribeTopicDesired
//                            .replace("DEVICE_ID", device.getDeviceId())
//                            .replace("CLIENT_ID", device.getClientId())
//                    , (topic, message) -> {
//                        System.out.println(" ***** subscribeTopicOrigin device {} ***** ".replace("{}", device.getDeviceId()));
//
//
//                        byte[] payload = message.getPayload();
//                        String stringPayload = new String(payload);
//
//                        try {
//
//                            AsyncObjectWrapper wrapper = JsonUtility.getObject(
//                                    stringPayload,
//                                    AsyncObjectWrapper.class);
//
//                            UpdateDeviceTwinDto dto = JsonUtility.getObject(
//                                    wrapper.getContent(),
//                                    UpdateDeviceTwinDto.class);
//
//                            System.out.println(dto.toString());
//
//                            device.processInput(mqttClient, subscribeTopicOrigin, dto);
//
//                        } catch (Exception e) {
//                            log.error("sdkljfkjldsf");
//                        }
//                    });
//            System.out.println("*");
//
////          <  Thread.sleep(2000);
////            MqttClient mqttClient1 = new MqttClient(MQTT_SERVER, device.getClientId());
////            mqttClient1.connect(mqttConnectionOptions);
////
////            do {
////                Thread.sleep(2000);
////                System.out.println("😘");
////            } while (!mqttClient1.isConnected());
////
////            mqttClient1.subscribe(
////                    subscribeTopicOrigin
////                            .replace("DEVICE_ID", device.getDeviceId())
////                            .replace("CLIENT_ID", device.getClientId())
////                    , (topic, message) -> {
////                        System.out.println(" ***** messageRejected device {} ***** ".replace("{}", device.getDeviceId()));
////                        byte[] payload = message.getPayload();
////                        // ... payload handling omitted
////                        String stringPayload = new String(payload);
////
////                        try {
////
////                            AsyncObjectWrapper wrapper = JsonUtility.getObject(
////                                    stringPayload,
////                                    AsyncObjectWrapper.class);
////
////                            UpdateDeviceTwinDto dto = JsonUtility.getObject(
////                                    wrapper.getContent(),
////                                    UpdateDeviceTwinDto.class);
////
////                            device.processInput(mqttClient, reportedTopic, dto);
////
////                        } catch (Exception e) {
////                            log.error("sdkljfkjldsf");
////                        }
////                    });
//        }
//
//        System.out.println("Done 👍  😏");
//
//    }
//
//}
