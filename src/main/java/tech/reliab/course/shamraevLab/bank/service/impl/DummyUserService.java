package tech.reliab.course.shamraevLab.bank.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.shamraevLab.bank.repository.PaymentAccountRepository;
import tech.reliab.course.shamraevLab.bank.repository.UserRepository;
import tech.reliab.course.shamraevLab.bank.service.UserService;
import tech.reliab.course.shamraevLab.bank.repository.CreditAccountRepository;

import java.util.Scanner;

@Service
@Slf4j
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

        log.info("Введите ID пользователя");
        var id = scanner.nextInt();

        var user = userRepository.getUserById(id);

        if (user.isEmpty()) {
            log.warn("Пользователь не найден");
            return;
        }

        var creditAccounts = creditAccountRepository.getCreditAccountByUserId(user.get().getId());
        var paymentAccounts = paymentAccountRepository.getAllPaymentAccountsByUserId(user.get().getId());

        log.info("[[ Информация о пользователе ]]");
        log.info(user.get().toString());
        log.info(creditAccounts.toString());
        log.info(paymentAccounts.toString());
    }
}
