package streams.employeeExample;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeAnalisis {

    public List<Employee> byDepartment(List<Employee> employees, String department) {
        return employees.stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .toList();
    }

    public double averageSalary(List<Employee> employees, String department) {
        return employees.stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
    }

    public Employee highestPaid(List<Employee> employees) {
        return employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);
    }

    public List<String> sortedNames(List<Employee> employees) {
        return employees.stream()
                .map(Employee::getName)
                .sorted()
                .toList();
    }

    public Map<String, List<Employee>> groupedByDepartments(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }


    public Map<String, Long> employeesByDepartment(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.counting()
                ));
    }

    public List<Employee> topBySalary(List<Employee> employees, int quantity) {
        return employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .limit(quantity)
                .toList();
    }
}
