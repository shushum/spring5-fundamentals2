package lab.model.simple;

import lab.model.Bar;
import lab.model.CustomerBrokenException;
import lab.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bar")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApuBar implements Bar {

	public lab.model.Squishee sellSquishee(Person person)  {
        if (person.isBroke())
            throw new CustomerBrokenException();

        System.out.println("Here is your SimpleSquishee");
        return () -> "Usual SimpleSquishee";
    }
}