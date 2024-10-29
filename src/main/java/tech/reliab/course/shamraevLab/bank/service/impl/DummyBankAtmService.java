package tech.reliab.course.shamraevLab.bank.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import tech.reliab.course.shamraevLab.bank.entity.Bank;
import tech.reliab.course.shamraevLab.bank.entity.CreditAccount;
import tech.reliab.course.shamraevLab.bank.entity.PaymentAccount;
import tech.reliab.course.shamraevLab.bank.entity.User;
import tech.reliab.course.shamraevLab.bank.repository.*;
import tech.reliab.course.shamraevLab.bank.service.BankAtmService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DummyBankAtmService implements BankAtmService, InitializingBean {
    private static final int BANKS_COUNT = 5;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final BankOfficeRepository bankOfficeRepository;
    private final EmployeeRepository employeeRepository;
    private final BankAtmRepository bankAtmRepository;
    private final PaymentAccountRepository paymentAccountRepository;
    private final CreditAccountRepository creditAccountRepository;

    @Override
    public void requestBankInfo() {
        var scanner = new Scanner(System.in);
        log.info("Введите ID банка");
        int id = scanner.nextInt();
        var bank = bankRepository.getBankById(id);
        if (bank.isEmpty()) {
            log.warn("Банк не найден");
            return;
        }

        log.info("[[ Информация о банке ]]");
        log.info(bank.get().toString());
        log.info(bankAtmRepository.getAllBankAtmsByBank(bank.get()).toString());
        log.info(bankOfficeRepository.getAllBankOfficesByBank(bank.get()).toString());
        log.info(employeeRepository.getAllEmployeesByBank(bank.get()).toString());
        log.info(userRepository.getUsersByBank(bank.get()).toString());
    }


    private void initializeBankInfo(String bankName) {
        final var bank = new Bank.BankBuilder().setName(bankName).createBank();
        bankRepository.registerBank(bank);

        int employeeCount = 5;
        int officeCount = 5;
        int atmCount = 5;

        List<User> users = Arrays.asList(
                userRepository.createUser(
                        "Шамраев Александр Анатольевич",
                        LocalDate.now(),
                        "Фронтендер/Бэкендер/Фембой/Лапочка"
                ),
                userRepository.createUser(
                        "Степанов Степан Николаевич",
                        LocalDate.now(),
                        "Базадатер/Гений/Миллиардер/Не нормис"
                ),
                userRepository.createUser(
                        "Притчин Иван Сергевич",
                        LocalDate.now(),
                        "Программист/Миллиардер/Филантроп"
                )
        );


        var offices = IntStream.range(0, officeCount).boxed().map((index) -> bankOfficeRepository.createBankOffice(
                String.format("Самый лучший офис в мире %d", index),
                "Кутузовский 35",
                true,
                true,
                true,
                true,
                500,
                bank
        )).toList();


        var employee = IntStream.range(0, employeeCount).boxed().map((index) -> employeeRepository.createEmployee(
                String.format("Иванова Ирина%d Валерьевна", index),
                LocalDate.now(),
                "Прислуга1",
                bank,
                false,
                offices.get(index % offices.size()),
                true,
                30000
        )).toList();

        var bankAtms = IntStream.range(0, atmCount).boxed().map((index) ->
                bankAtmRepository.createBankAtm(
                        "Лучший в мире банкомат",
                        "Кутузовский 35",
                        bank,
                        offices.get(index % offices.size()),
                        employee.get(index % employee.size()),
                        true,
                        true,
                        500
                )).toList();


        for (var user : users) {
            PaymentAccount paymentAccount = paymentAccountRepository.createPaymentAccount(user, bank);

            CreditAccount creditAccount = creditAccountRepository.createCreditAccount(
                    user,
                    bank,
                    LocalDate.now(),
                    8,
                    500000,
                    3,
                    employee.get(user.getId() % employee.size()),
                    paymentAccount
            );

            paymentAccountRepository.createPaymentAccount(user, bank);

            creditAccountRepository.createCreditAccount(
                    user,
                    bank,
                    LocalDate.now(),
                    12,
                    50002,
                    5,
                    employee.get(user.getId() % employee.size()),
                    paymentAccount
            );
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        var bankNames = IntStream.range(0, BANKS_COUNT).boxed().map((index) -> String.format("Имба банк %d", index));
        bankNames.forEach(this::initializeBankInfo);
    }
}
