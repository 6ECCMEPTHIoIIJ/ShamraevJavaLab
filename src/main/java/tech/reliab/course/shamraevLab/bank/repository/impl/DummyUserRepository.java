package tech.reliab.course.shamraevLab.bank.repository.impl;

import lombok.RequiredArgsConstructor;
import tech.reliab.course.shamraevLab.bank.entity.Bank;
import tech.reliab.course.shamraevLab.bank.entity.CreditAccount;
import tech.reliab.course.shamraevLab.bank.entity.PaymentAccount;
import tech.reliab.course.shamraevLab.bank.entity.User;
import tech.reliab.course.shamraevLab.bank.repository.UserRepository;

import java.time.LocalDate;
import java.util.*;

public class DummyUserRepository implements UserRepository {
    private static final int MAX_MONTHLY_INCOME = 10000;
    private static final double DIVIDER = 1000.0;
    private static final int FACTOR = 100;
    private static int usersId = 0;

    private static final Random random = new Random();
    private final List<User> users = new ArrayList<>();

    @Override
    public User createUser(String fullName, LocalDate birthDate, String job) {
        User user = new User(fullName, birthDate, job);
        user.setId(usersId++);
        user.setMonthlyIncome(generateMonthlyIncome());
        user.setCreditRating(generateCreditRating(user.getMonthlyIncome()));
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return List.copyOf(users);
    }

    @Override
    public void updateUser(int id, String name) {
        User user = getUserIfExists(id);
        user.setFullName(name);
    }

    @Override
    public void deleteUser(int id) {
        users.remove(getUserIfExists(id));
    }

    @Override
    public void addCreditAccount(CreditAccount creditAccount, User user) {
        List<CreditAccount> creditAccounts = user.getCreditAccounts();
        creditAccounts.add(creditAccount);
        user.setCreditAccounts(creditAccounts);
    }

    @Override
    public void addPaymentAccount(PaymentAccount paymentAccount, User user) {
        List<PaymentAccount> paymentAccounts = user.getPaymentAccounts();
        paymentAccounts.add(paymentAccount);
        user.setPaymentAccounts(paymentAccounts);
    }

    @Override
    public void addBank(Bank bank, User user) {
        List<Bank> banks = user.getBanks();
        banks.add(bank);
        user.setBanks(banks);
    }

    @Override
    public void deleteCreditAccount(CreditAccount creditAccount, User user) {
        List<CreditAccount> creditAccounts = user.getCreditAccounts();
        creditAccounts.remove(creditAccount);
        user.setCreditAccounts(creditAccounts);
    }

    @Override
    public void deletePaymentAccount(PaymentAccount paymentAccount, User user) {
        List<PaymentAccount> paymentAccounts = user.getPaymentAccounts();
        paymentAccounts.remove(paymentAccount);
        user.setPaymentAccounts(paymentAccounts);
    }

    @Override
    public void deleteBank(Bank bank) {
        users.forEach(user -> {
            var banks = user.getBanks();
            banks.remove(bank);
            user.setBanks(banks);
        });
    }

    @Override
    public List<User> getUsersByBank(Bank bank) {
        return users.stream()
                .filter(user ->
                        user.getBanks()
                                .stream()
                                .anyMatch((Bank userBank) -> userBank.getId() == bank.getId())).toList();

    }

    private int generateMonthlyIncome() {
        return random.nextInt(MAX_MONTHLY_INCOME + 1);
    }

    private int generateCreditRating(double monthlyIncome) {
        return (int) Math.ceil(monthlyIncome / DIVIDER) * FACTOR;
    }

    private User getUserIfExists(int id) {
        return getUserById(id).orElseThrow(() -> new NoSuchElementException("User was not found"));
    }

}
