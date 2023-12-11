import com.mukul.cloud.configuration.ConnPropertiesConfiguration;
import com.mukul.cloud.configuration.ConnProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "jms.properties[0].name=property1",
        "jms.properties[0].value=value1",
        "jms.properties[1].name=property2",
        "jms.properties[1].value=value2"
})
public class ConnPropertiesConfigurationTest {

    @Autowired
    private ConnPropertiesConfiguration connPropertiesConfiguration;

    @Test
    public void testPropertiesConfiguration() {
        // Given
        List<ConnProperty> properties = connPropertiesConfiguration.getProperties();

        // Then
        assertNotNull(properties);
        assertEquals(2, properties.size());

        ConnProperty property1 = properties.get(0);
        assertEquals("property1", property1.getName());
        assertEquals("value1", property1.getValue());

        ConnProperty property2 = properties.get(1);
        assertEquals("property2", property2.getName());
        assertEquals("value2", property2.getValue());
    }
}
