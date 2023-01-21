package ir.pod.podiotmockdevice;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@ToString
public class UpdateDeviceTwinDto implements Serializable {

    private DeviceTwinDocumentVO deviceTwinDocument;
}
