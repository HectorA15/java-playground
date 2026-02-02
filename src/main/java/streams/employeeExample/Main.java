package streams.employeeExample;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>(List.of(
                new Employee(1, "Héctor", "Engineering", 45000),
                new Employee(2, "María", "Engineering", 50000),
                new Employee(3, "Luis", "Sales", 35000),
                new Employee(4, "Ana", "Sales", 37000),
                new Employee(5, "Carlos", "HR", 33000),
                new Employee(6, "Sophia", "Engineering", 55000)
        ));

        EmployeeAnalisis analyze = new EmployeeAnalisis();
        System.out.println("\n================================ Filtered by department ==================================");
        analyze.byDepartment(employees, "Engineering").forEach(System.out::println);

        System.out.println("\n====================== Average salary by department (Engineering) ========================");
        System.out.println(analyze.averageSalary(employees, "Engineering"));

        System.out.println("\n============================== Employee with best salary =================================");
        System.out.println(analyze.highestPaid(employees));

        System.out.println("\n============================= Names sorted alphabetically ================================");
        analyze.sortedNames(employees).forEach(System.out::println);

        System.out.println("\n================================ Grouped by department ===================================");
        analyze.groupedByDepartments(employees).forEach((dept, list) -> {
            System.out.println(dept + ": " + analyze.sortedNames(list));
        });
        System.out.println("\n====================== Quantity of employees of each department ==========================");
        analyze.employeesByDepartment(employees).forEach((dept, qty) -> {
            System.out.println(dept + ": " + qty);
        });

        System.out.println("\n============================== top 3 most paid employees =================================");
        analyze.topBySalary(employees, 3).forEach(e ->
                System.out.println(e.getName() + " - $" + e.getSalary())
        );
    }
}
