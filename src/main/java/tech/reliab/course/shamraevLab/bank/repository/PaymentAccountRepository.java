package tech.reliab.course.shamraevLab.bank.repository;

import tech.reliab.course.shamraevLab.bank.entity.Bank;
import tech.reliab.course.shamraevLab.bank.entity.PaymentAccount;
import tech.reliab.course.shamraevLab.bank.entity.User;

import java.util.List;
import java.util.Optional;

public interface PaymentAccountRepository {
    PaymentAccount createPaymentAccount(User user, Bank bank);

    Optional<PaymentAccount> getPaymentAccountById(int id);

    List<PaymentAccount> getAllPaymentAccounts();

    List<PaymentAccount> getAllPaymentAccountsByUserId(int userId);

    void updatePaymentAccount(int id, Bank bank);

    void deletePaymentAccount(int id);
}