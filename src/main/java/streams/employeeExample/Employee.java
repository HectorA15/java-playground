package streams.employeeExample;

public class Employee {
    private final long id;
    private final String name;
    private final String department;
    private final double salary;

    public Employee(long id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "[" +
                "id: " + id +
                ", name: '" + name + '\'' +
                ", department: '" + department + '\'' +
                ", salary: " + salary +
                ']';
    }
}