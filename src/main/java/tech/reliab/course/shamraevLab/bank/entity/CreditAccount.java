package tech.reliab.course.shamraevLab.bank.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString(exclude = {"user", "bank", "employee", "paymentAccount"})
public class CreditAccount {

    private int id;
    private User user;
    private Bank bank;
    private LocalDate startDate;
    private LocalDate endDate;
    private int loanTermMonths;
    private double loanAmount;
    private double monthlyPayment;
    private double interestRate;
    private Employee employee;
    private PaymentAccount paymentAccount;

    public CreditAccount(User user, Bank bank, LocalDate startDate, int loanTermMonths, double interestRate, Employee employee, PaymentAccount paymentAccount) {
        this.user = user;
        this.bank = bank;
        this.startDate = startDate;
        this.loanTermMonths = loanTermMonths;
        this.interestRate = interestRate;
        this.employee = employee;
        this.paymentAccount = paymentAccount;
    }
}