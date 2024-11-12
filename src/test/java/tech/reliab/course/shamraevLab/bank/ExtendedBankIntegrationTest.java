package tech.reliab.course.shamraevLab.bank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import tech.reliab.course.shamraevLab.bank.entity.*;
import tech.reliab.course.shamraevLab.bank.enums.BankAtmStatus;
import tech.reliab.course.shamraevLab.bank.enums.BankOfficeStatus;
import tech.reliab.course.shamraevLab.bank.repository.*;


import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ExtendedBankIntegrationTest {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankOfficeRepository officeRepository;

    @Autowired
    private BankAtmRepository atmRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Test
    public void testBankAssociationWithOfficesAndAtms() {
        Bank bank = new Bank.BankBuilder().setName("Test Bank").createBank();
        bankRepository.registerBank(bank);

        assertEquals(1, bankRepository.getAllBanks().size(), "Должен быть сохранен один банк");
        assertEquals("Test Bank", bankRepository.getAllBanks().getFirst().getName(), "Имя банка должно быть 'Test Bank'");
        assertNotNull(bankRepository.getBankById(bankRepository.getAllBanks().getFirst().getId()), "Банк должен быть найден по ID");



        officeRepository.createBankOffice("Head Office", "Main Street 1", true, true, true, true, 12.0, bank);
        assertEquals(1, officeRepository.getAllBankOffices().size(), "Должен быть сохранен один офис банка");
        assertEquals("Head Office", officeRepository.getAllBankOffices().getFirst().getName(), "Имя офиса должно быть 'Head Office'");
        assertNotNull(officeRepository.getBankOfficeById(officeRepository.getAllBankOffices().getFirst().getId()), "Офис должен быть найден по ID");
        Employee employee = new Employee("John Doe", LocalDate.of(1990, Month.JANUARY, 1), "Manager", bank, true, officeRepository.getAllBankOffices().getFirst(), true, 1000.0);



        atmRepository.createBankAtm("ATM 1", "Main Street 1", bank, officeRepository.getAllBankOffices().getFirst(), employee, true, true, 100.0);
        assertEquals(1, atmRepository.getAllBankAtms().size(), "Должен быть сохранен один банкомат");
        assertEquals("ATM 1", atmRepository.getAllBankAtms().getFirst().getName(), "Имя банкомата должно быть 'ATM 1'");
        assertNotNull(atmRepository.getBankAtmById(atmRepository.getAllBankAtms().getFirst().getId()), "Банкомат должен быть найден по ID");

        bankRepository.addOffice(bank.getId());
        assertEquals(Objects.requireNonNull(bankRepository.getBankById(bank.getId()).orElse(null)).getOfficeCount(), 1, "Банк должен содержать один офис");

        bankRepository.addAtm(bank.getId());
        assertEquals(Objects.requireNonNull(bankRepository.getBankById(bank.getId()).orElse(null)).getAtmCount(), 1, "Банк должен содержать один банкомат");

        bankRepository.addEmployee(bank.getId());
        assertEquals(Objects.requireNonNull(bankRepository.getBankById(bank.getId()).orElse(null)).getEmployeeCount(), 1, "Банк должен содержать одного сотрудника");
    }

    @Test
    public void testCreateCreditAccountForUser() {
        Bank bank = new Bank.BankBuilder().setName("Credit Bank").createBank();
        bankRepository.registerBank(bank);

        userRepository.createUser("Robert Smith", LocalDate.of(1980, Month.JANUARY, 1), "Engineer");
        User user = userRepository.getAllUsers().getFirst();
        assertEquals(1, userRepository.getAllUsers().size(), "Должен быть сохранен один пользователь");
        assertEquals("Robert Smith", Objects.requireNonNull(userRepository.getUserById(user.getId()).orElse(null)).getFullName(), "Имя пользователя должно быть 'Robert Smith'");
        assertNotNull(userRepository.getUserById(user.getId()), "Пользователь должен быть найден по ID");

        Employee employee = new Employee("John Doe", LocalDate.of(1990, Month.JANUARY, 1), "Manager", bank, true, null, true, 1000.0);
        PaymentAccount paymentAccount = new PaymentAccount(user, bank);
        creditAccountRepository.createCreditAccount(user, bank, LocalDate.now(), 3, 1000.0, 12.0, employee, paymentAccount);
        assertEquals(1, creditAccountRepository.getCreditAccountByUserId(user.getId()).size(), "Должен быть сохранен один кредитный аккаунт");
        assertEquals(1000.0, creditAccountRepository.getCreditAccountByUserId(user.getId()).getFirst().getLoanAmount(), "Сумма кредита должна быть 1000");
        assertEquals(user.getId(), creditAccountRepository.getCreditAccountByUserId(user.getId()).getFirst().getUser().getId(), "Кредитный аккаунт должен быть привязан к пользователю");
        assertEquals(bank.getId(), creditAccountRepository.getCreditAccountByUserId(user.getId()).getFirst().getBank().getId(), "Кредитный аккаунт должен быть привязан к банку");
        assertEquals(employee.getId(), creditAccountRepository.getCreditAccountByUserId(user.getId()).getFirst().getEmployee().getId(), "Кредитный аккаунт должен быть привязан к сотруднику");
        assertEquals(paymentAccount.getId(), creditAccountRepository.getCreditAccountByUserId(user.getId()).getFirst().getPaymentAccount().getId(), "Кредитный аккаунт должен быть привязан к платежному аккаунту");
        assertEquals(3, creditAccountRepository.getCreditAccountByUserId(user.getId()).getFirst().getLoanTermMonths(), "Срок кредита должен быть 3 месяца");
        assertEquals(12.0, creditAccountRepository.getCreditAccountByUserId(user.getId()).getFirst().getInterestRate(), "Процентная ставка должна быть 12.0");
    }

    @Test
    public void testBankOfficeAndAtmStatusCheck() {
        Bank bank = new Bank.BankBuilder().setName("Test Bank").createBank();
        bankRepository.registerBank(bank);

        officeRepository.createBankOffice("Head Office", "Main Street 1", true, true, true, true, 12.0, bank);
        officeRepository.updateBankOffice(officeRepository.getAllBankOffices().getFirst().getId(), BankOfficeStatus.WORKING);

        assertEquals(BankOfficeStatus.WORKING, officeRepository.getAllBankOffices().getFirst().getStatus(), "Статус офиса должен быть 'Working'");
        officeRepository.updateBankOffice(officeRepository.getAllBankOffices().getFirst().getId(), BankOfficeStatus.NOT_WORKING);
        assertEquals(BankOfficeStatus.NOT_WORKING, officeRepository.getAllBankOffices().getFirst().getStatus(), "Статус офиса долж быть 'Not Working'");

        atmRepository.createBankAtm("ATM 1", "Main Street 1", bank, officeRepository.getAllBankOffices().getFirst(), null, true, true, 100.0);
        atmRepository.updateBankAtm(atmRepository.getAllBankAtms().getFirst().getId(), BankAtmStatus.WORKING);
        assertEquals(BankAtmStatus.WORKING, atmRepository.getAllBankAtms().getFirst().getStatus(), "Статус банкомата должен быть 'Working'");
        atmRepository.updateBankAtm(atmRepository.getAllBankAtms().getFirst().getId(), BankAtmStatus.NOT_WORKING);
        assertEquals(BankAtmStatus.NOT_WORKING, atmRepository.getAllBankAtms().getFirst().getStatus(), "Статус банкомата должен быть 'Not Working'");

    }
}