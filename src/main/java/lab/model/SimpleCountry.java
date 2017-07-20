package lab.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

@Value
@EqualsAndHashCode(exclude = "id")
public class SimpleCountry implements Country, Serializable {
	private int id;
    private String name;
    private String codeName;
}
