package tech.reliab.course.shamraevLab.bank.repository;

import tech.reliab.course.shamraevLab.bank.entity.Bank;
import tech.reliab.course.shamraevLab.bank.entity.CreditAccount;
import tech.reliab.course.shamraevLab.bank.entity.PaymentAccount;
import tech.reliab.course.shamraevLab.bank.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User createUser(String fullName, LocalDate birthDate, String job);

    Optional<User> getUserById(int id);

    List<User> getAllUsers();

    void updateUser(int id, String name);

    void deleteUser(int id);

    void addCreditAccount(CreditAccount creditAccount, User user);

    void addPaymentAccount(PaymentAccount paymentAccount, User user);

    void addBank(Bank bank, User user);

    void deleteCreditAccount(CreditAccount creditAccount, User user);

    void deletePaymentAccount(PaymentAccount paymentAccount, User user);

    void deleteBank(Bank bank);

    List<User> getUsersByBank(Bank bank);
}