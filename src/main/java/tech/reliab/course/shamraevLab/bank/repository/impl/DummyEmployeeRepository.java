package tech.reliab.course.shamraevLab.bank.repository.impl;

import lombok.RequiredArgsConstructor;
import tech.reliab.course.shamraevLab.bank.entity.Bank;
import tech.reliab.course.shamraevLab.bank.entity.BankOffice;
import tech.reliab.course.shamraevLab.bank.entity.Employee;
import tech.reliab.course.shamraevLab.bank.repository.BankRepository;
import tech.reliab.course.shamraevLab.bank.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class DummyEmployeeRepository implements EmployeeRepository {
    private static int employeeId;

    private final List<Employee> employees = new ArrayList<>();
    private final BankRepository bankRepository;

    @Override
    public Employee createEmployee(String fullName, LocalDate birthDate, String position, Bank bank, boolean remoteWork, BankOffice bankOffice, boolean canIssueLoans, double salary) {
        Employee employee = new Employee(fullName,
                birthDate,
                position,
                bank,
                remoteWork,
                bankOffice,
                canIssueLoans,
                salary);

        employee.setId(employeeId++);
        employees.add(employee);
        bankRepository.addEmployee(bank.getId());
        return employee;
    }

    @Override
    public Optional<Employee> getEmployeeById(int id) {
        return employees.stream().filter(employee -> employee.getId() == id).findFirst();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return List.copyOf(employees);
    }

    @Override
    public List<Employee> getAllEmployeesByBank(Bank bank) {
        return employees.stream()
                .filter(employee -> employee.getBank().getId() == bank.getId())
                .collect(Collectors.toList());
    }

    @Override
    public void updateEmployee(int id, String name) {
        Employee employee = getEmployeeIfExists(id);
        employee.setFullName(name);
    }

    @Override
    public void deleteEmployee(int id) {
        employees.remove(getEmployeeIfExists(id));
    }

    private Employee getEmployeeIfExists(int id) {
        return getEmployeeById(id).orElseThrow(() -> new NoSuchElementException("Employee was not found"));
    }
}
