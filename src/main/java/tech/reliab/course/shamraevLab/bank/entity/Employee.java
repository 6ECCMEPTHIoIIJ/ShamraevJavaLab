package tech.reliab.course.shamraevLab.bank.entity;

import java.time.LocalDate;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Employee {
    private int id;
    private String fullName;
    private LocalDate birthDate;
    private String position;
    private Bank bank;
    private boolean remoteWork;
    private BankOffice bankOffice;
    private boolean canIssueLoans;
    private double salary;

    public Employee(String fullName, LocalDate birthDate, String position, Bank bank, boolean remoteWork, BankOffice bankOffice, boolean canIssueLoans, double salary) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.position = position;
        this.bank = bank;
        this.remoteWork = remoteWork;
        this.bankOffice = bankOffice;
        this.canIssueLoans = canIssueLoans;
        this.salary = salary;
    }
}
