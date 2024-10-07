package tech.reliab.course.shamraevLab.bank.entity;

import lombok.Data;
import lombok.ToString;
import tech.reliab.course.shamraevLab.bank.enums.BankAtmStatus;

@Data
@ToString
public class BankAtm {
    private int id;
    private String name;
    private String address;
    private BankAtmStatus status;
    private Bank bank;
    private BankOffice location;
    private Employee employee;
    private boolean cashWithdrawal;
    private boolean cashDeposit;
    private double atmMoney;
    private double maintenanceCost;

    public BankAtm(String name, String address, Bank bank, BankOffice location, Employee employee, boolean cashWithdrawal, boolean cashDeposit, double maintenanceCost) {
        this.name = name;
        this.address = address;
        this.bank = bank;
        this.location = location;
        this.employee = employee;
        this.cashWithdrawal = cashWithdrawal;
        this.cashDeposit = cashDeposit;
        this.maintenanceCost = maintenanceCost;
    }
}
