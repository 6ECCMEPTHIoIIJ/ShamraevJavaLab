package tech.reliab.course.shamraevLab.bank;


import lombok.Getter;
import tech.reliab.course.shamraevLab.bank.repository.*;
import tech.reliab.course.shamraevLab.bank.repository.impl.*;
import tech.reliab.course.shamraevLab.bank.service.BankAtmService;
import tech.reliab.course.shamraevLab.bank.service.UserService;
import tech.reliab.course.shamraevLab.bank.service.impl.DummyBankAtmService;
import tech.reliab.course.shamraevLab.bank.service.impl.DummyUserService;

import java.io.IOException;
import java.util.Scanner;

public class ShamraevLabApplication {
    private static final int EXIT_CODE = 3;

    private static final UserRepository userRepository = new DummyUserRepository();
    private static final BankRepository bankRepository = new DummyBankRepository(userRepository);
    private static final BankOfficeRepository bankOfficeRepository = new DummyBankOfficeRepository(bankRepository);
    private static final EmployeeRepository employeeRepository = new DummyEmployeeRepository(bankRepository);
    private static final BankAtmRepository bankAtmRepository = new DummyBankAtmRepository(bankRepository);
    private static final PaymentAccountRepository paymentAccountRepository = new DummyPaymentAccountRepository(userRepository, bankRepository);
    private static final CreditAccountRepository creditAccountRepository = new DummyCreditAccountRepository(userRepository);
    private static final BankAtmService bankAtmService = new DummyBankAtmService(
            userRepository,
            bankRepository,
            bankOfficeRepository,
            employeeRepository,
            bankAtmRepository,
            paymentAccountRepository,
            creditAccountRepository
    );
    private static final UserService userService = new DummyUserService(
            userRepository,
            creditAccountRepository,
            paymentAccountRepository
    );

    private static Option option;

    public static void main(String[] args) throws IOException {
        bankAtmService.initializeBanks();
        while (SelectOption()) {
            switch (option) {
                case BANK_INFO:
                    bankAtmService.requestBankInfo();
                    break;
                case USER_INFO:
                    userService.requestUserInfo();
                    break;
            }

        }
    }

    private static boolean SelectOption() {
        var scanner = new Scanner(System.in);
        System.out.println("Выберите действие:");
        System.out.println("1. Вывести информацию о банке");
        System.out.println("2. Вывести информацию о пользователе");
        System.out.println("3. Выйти");
        option = Option.valueOf(scanner.nextInt());
        return option != Option.EXIT;
    }

    @Getter
    static enum Option {
        BANK_INFO(1),
        USER_INFO(2),
        EXIT(3);

        private final int value;

        Option(int value) {
            this.value = value;
        }

        public static Option valueOf(int value) {
            for (Option option : values()) {
                if (option.value == value) {
                    return option;
                }
            }
            throw new IllegalArgumentException("No such option");
        }
    }
}