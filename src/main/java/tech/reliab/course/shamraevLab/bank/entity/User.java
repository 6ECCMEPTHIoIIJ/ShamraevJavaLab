package tech.reliab.course.shamraevLab.bank.entity;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class User {
    private int id;
    private String fullName;
    private LocalDate birthDate;
    private String job;
    private double monthlyIncome;
    private int creditRating;
    private List<Bank> banks = new ArrayList<>();
    private List<CreditAccount> creditAccounts = new ArrayList<>();
    private List<PaymentAccount> paymentAccounts = new ArrayList<>();

    public User(String fullName, LocalDate birthDate, String job) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.job = job;
    }
}
