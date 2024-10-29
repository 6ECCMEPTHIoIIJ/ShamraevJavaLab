package tech.reliab.course.shamraevLab.bank.service.impl;

import lombok.RequiredArgsConstructor;
import tech.reliab.course.shamraevLab.bank.repository.PaymentAccountRepository;
import tech.reliab.course.shamraevLab.bank.repository.UserRepository;
import tech.reliab.course.shamraevLab.bank.service.UserService;
import tech.reliab.course.shamraevLab.bank.repository.CreditAccountRepository;

import java.util.Scanner;

@RequiredArgsConstructor
public class DummyUserService implements UserService {
    private final UserRepository userRepository;
    private final CreditAccountRepository creditAccountRepository;
    private final PaymentAccountRepository paymentAccountRepository;

    @Override
    public void requestUserInfo() {
        var users = userRepository.getAllUsers();
        for (var user : users) {
            System.out.println(user);
        }


        var scanner = new Scanner(System.in);

        System.out.println("Введите ID пользователя");
        var id = scanner.nextInt();

        var user = userRepository.getUserById(id);

        var isUserNotPresent = user.isEmpty();
        if (isUserNotPresent) return;

        var creditAccounts = creditAccountRepository.getCreditAccountByUserId(user.get().getId());
        var paymentAccounts = paymentAccountRepository.getAllPaymentAccountsByUserId(user.get().getId());

        System.out.println(user);
        System.out.println(creditAccounts);
        System.out.println(paymentAccounts);
    }
}
