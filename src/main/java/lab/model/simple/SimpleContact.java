package lab.model.simple;

import lab.model.Contact;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(exclude = "id")
public class SimpleContact implements Contact, Serializable {
    private long id;
    private Type type;
    private String value;
}
