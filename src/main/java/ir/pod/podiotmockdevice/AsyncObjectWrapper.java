package ir.pod.podiotmockdevice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AsyncObjectWrapper implements Serializable {

    private long id;
    private long senderId;
    private int type;

    private String content;

}
