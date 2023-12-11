import com.mukul.cloud.entity.Employee;
import com.mukul.cloud.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetAllEmployees() {
        // Given
        Employee employee1 = new Employee(1L, "John Doe", "IT");
        Employee employee2 = new Employee(2L, "Jane Doe", "HR");
        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeRepository.findAll()).thenReturn(employees);

        // When
        List<Employee> result = employeeService.getAllEmployees();

        // Then
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
    }

    @Test
    public void testGetEmployeeById() {
        // Given
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John Doe", "IT");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // When
        Employee result = employeeService.getEmployeeById(employeeId);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testSaveEmployee() {
        // Given
        Employee employee = new Employee(1L, "John Doe", "IT");

        when(employeeRepository.save(employee)).thenReturn(employee);

        // When
        Employee result = employeeService.saveEmployee(employee);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testDeleteEmployee() {
        // Given
        Long employeeId = 1L;

        // When
        employeeService.deleteEmployee(employeeId);

        // Then
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}
