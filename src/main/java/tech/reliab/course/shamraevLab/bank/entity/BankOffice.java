package tech.reliab.course.shamraevLab.bank.entity;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.ToString;

import tech.reliab.course.shamraevLab.bank.entity.Bank;
import tech.reliab.course.shamraevLab.bank.enums.BankOfficeStatus;

@ToString
@Data
public class BankOffice {
    private int id;
    private String name;
    private String address;
    private BankOfficeStatus status;
    private boolean canPlaceAtm;
    private boolean canIssueLoan;
    private boolean cashWithdrawal;
    private boolean cashDeposit;
    private double officeMoney;
    private double rentCost;
    private int bankId;

    @Min(value = 0, message = "The number of ATMs must be greater than or equal to 0")
    private int atmCount;

    public BankOffice(String name,
                      String address,
                      boolean canPlaceAtm,
                      boolean canIssueLoan,
                      boolean cashWithdrawal,
                      boolean cashDeposit,
                      double rentCost,
                      Bank bank) {
        this.name = name;
        this.address = address;
        this.canPlaceAtm = canPlaceAtm;
        this.canIssueLoan = canIssueLoan;
        this.cashWithdrawal = cashWithdrawal;
        this.cashDeposit = cashDeposit;
        this.rentCost = rentCost;
        this.bankId = bank.getId();
    }
}
