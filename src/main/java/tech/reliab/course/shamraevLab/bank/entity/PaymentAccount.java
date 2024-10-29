package tech.reliab.course.shamraevLab.bank.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"user", "bank"})
public class PaymentAccount {
    private int id;
    private User user;
    private Bank bank;
    private double balance;

    public PaymentAccount(User user, Bank bank) {
        this.user = user;
        this.bank = bank;
        this.balance = 0;
    }
}
