package ir.pod.podiotmockdevice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Ali Mojahed on 9/26/2021
 * @project smart-gas-meter
 **/

@Getter
@Setter
@NoArgsConstructor
public class DeviceTwinDocumentVO implements Serializable {
    private DeviceTwinAttribute attributes;
}
