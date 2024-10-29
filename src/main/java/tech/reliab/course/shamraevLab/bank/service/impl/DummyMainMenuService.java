package tech.reliab.course.shamraevLab.bank.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import tech.reliab.course.shamraevLab.bank.service.BankAtmService;
import tech.reliab.course.shamraevLab.bank.service.MainMenuService;
import tech.reliab.course.shamraevLab.bank.service.UserService;

import java.util.Scanner;


@Service
@Slf4j
@RequiredArgsConstructor
public class DummyMainMenuService implements MainMenuService, ApplicationListener<ApplicationReadyEvent> {
    private final ApplicationContext context;

    private final BankAtmService bankAtmService;
    private final UserService userService;

    private static Option option;

    @Override
    public void run() {
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

        CloseApplication();
    }

    private static boolean SelectOption() {
        var scanner = new Scanner(System.in);
        log.info("[[ Главное меню ]]");
        log.info("Выберите действие:");
        log.info("  1. Вывести информацию о банке");
        log.info("  2. Вывести информацию о пользователе");
        log.info("  3. Выйти");
        option = Option.valueOf(scanner.nextInt());
        return option != Option.EXIT;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("ДОБРО ПОЖАЛОВАТЬ");
        run();
    }

    private void CloseApplication() {
        log.info("ДО СВИДАНИЯ");
        if (context instanceof ConfigurableApplicationContext) {
            ((ConfigurableApplicationContext) context).close();
        }
    }


    @Getter
    enum Option {
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
