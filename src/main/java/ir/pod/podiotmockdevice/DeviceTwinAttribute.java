package ir.pod.podiotmockdevice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Ali Mojahed on 9/26/2021
 * @project smart-gas-meter
 **/

@Getter
@Setter
@NoArgsConstructor
public class DeviceTwinAttribute implements Serializable {
    private Map<String, Object> desired;
    private Map<String, Object> reported;
}
