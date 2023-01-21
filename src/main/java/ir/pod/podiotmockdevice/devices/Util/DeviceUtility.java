package ir.pod.podiotmockdevice.devices.Util;

import ir.pod.podiotmockdevice.DeviceTwinAttribute;
import ir.pod.podiotmockdevice.DeviceTwinDocumentVO;
import ir.pod.podiotmockdevice.RandomGenerator;
import ir.pod.podiotmockdevice.UpdateDeviceTwinDto;
import org.modelmapper.internal.util.Lists;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author S-Serajzadeh
 * @Date 9/11/22
 * @Project PANEL IOT-Bank
 */
public class DeviceUtility {
    public static UpdateDeviceTwinDto desiredToReported(Map<String, Object> desired, boolean linkQuality, boolean lastSeen, String... values) {
        Map<String, Object> reportedAttributes = new HashMap<>();
        for (String desiredValue : values) {
            if (desired.containsKey(desiredValue)) {
                reportedAttributes.put(desiredValue, desired.get(desiredValue));
            }
        }

        if (lastSeen) {
            reportedAttributes.put("last_seen", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }

        if (linkQuality) {
            reportedAttributes.put("linkquality", RandomGenerator.generateRandom(100));
        }
        
        
        DeviceTwinAttribute deviceTwinAttribute = new DeviceTwinAttribute();
        deviceTwinAttribute.setReported(reportedAttributes);

        DeviceTwinDocumentVO newDeviceTwinDocumentVO = new DeviceTwinDocumentVO();
        newDeviceTwinDocumentVO.setAttributes(deviceTwinAttribute);

        UpdateDeviceTwinDto requestDto = new UpdateDeviceTwinDto();
        requestDto.setDeviceTwinDocument(newDeviceTwinDocumentVO);

        return requestDto;
    }

    public static void selectiveInput(Map<String, Object> desired, Map<String, Object> reported, String key, Object... values) {
        if (desired.containsKey(key) && Lists.from(Arrays.stream(values).iterator()).contains(desired.get(key))) {
            reported.put(key, desired.get(key));
        }
    }

    public static UpdateDeviceTwinDto instantiate(){

        DeviceTwinAttribute deviceTwinAttribute = new DeviceTwinAttribute();
        deviceTwinAttribute.setReported(new HashMap<>());

        DeviceTwinDocumentVO newDeviceTwinDocumentVO = new DeviceTwinDocumentVO();
        newDeviceTwinDocumentVO.setAttributes(deviceTwinAttribute);

        UpdateDeviceTwinDto requestDto = new UpdateDeviceTwinDto();
        requestDto.setDeviceTwinDocument(newDeviceTwinDocumentVO);

        return requestDto;
    }

}
