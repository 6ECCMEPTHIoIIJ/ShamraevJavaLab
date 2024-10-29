package tech.reliab.course.shamraevLab.bank.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tech.reliab.course.shamraevLab.bank.entity.Bank;
import tech.reliab.course.shamraevLab.bank.entity.PaymentAccount;
import tech.reliab.course.shamraevLab.bank.entity.User;
import tech.reliab.course.shamraevLab.bank.repository.BankRepository;
import tech.reliab.course.shamraevLab.bank.repository.PaymentAccountRepository;
import tech.reliab.course.shamraevLab.bank.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DummyPaymentAccountRepository implements PaymentAccountRepository {
    private static int paymentAccountId;

    private final List<PaymentAccount> paymentAccounts = new ArrayList<>();
    private final UserRepository userRepository;
    private final BankRepository bankRepository;

    @Override
    public PaymentAccount createPaymentAccount(User user, Bank bank) {
        PaymentAccount paymentAccount = new PaymentAccount(user, bank);
        paymentAccount.setId(paymentAccountId++);
        paymentAccounts.add(paymentAccount);
        userRepository.addPaymentAccount(paymentAccount, user);
        userRepository.addBank(bank, user);
        bankRepository.addClient(bank.getId());
        return paymentAccount;
    }

    @Override
    public Optional<PaymentAccount> getPaymentAccountById(int id) {
        return paymentAccounts.stream()
                .filter(paymentAccount -> paymentAccount.getId() == id)
                .findFirst();
    }

    @Override
    public List<PaymentAccount> getAllPaymentAccounts() {
        return List.copyOf(paymentAccounts);
    }

    @Override
    public List<PaymentAccount> getAllPaymentAccountsByUserId(int userId) {
        return paymentAccounts.stream()
                .filter(account -> account.getUser().getId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public void updatePaymentAccount(int id, Bank bank) {
        PaymentAccount paymentAccount = getPaymentAccountIfExists(id);
        paymentAccount.setBank(bank);
    }

    @Override
    public void deletePaymentAccount(int id) {
        PaymentAccount paymentAccount = getPaymentAccountIfExists(id);
        paymentAccounts.remove(paymentAccount);
        userRepository.deletePaymentAccount(paymentAccount, paymentAccount.getUser());
    }

    private PaymentAccount getPaymentAccountIfExists(int id) {
        return getPaymentAccountById(id).orElseThrow(() -> new NoSuchElementException("PaymentAccount was not found"));
    }
}
