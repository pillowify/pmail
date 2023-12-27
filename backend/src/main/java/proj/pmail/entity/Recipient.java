package proj.pmail.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Recipient {
    private String name;
    private String address;
}

