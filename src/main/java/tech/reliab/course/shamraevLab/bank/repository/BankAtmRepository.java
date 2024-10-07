package tech.reliab.course.shamraevLab.bank.repository;

import tech.reliab.course.shamraevLab.bank.entity.Bank;
import tech.reliab.course.shamraevLab.bank.entity.BankAtm;
import tech.reliab.course.shamraevLab.bank.entity.BankOffice;
import tech.reliab.course.shamraevLab.bank.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface BankAtmRepository {
    BankAtm createBankAtm(String name,
                          String address, Bank bank, BankOffice location, Employee employee,
                          boolean cashWithdrawal, boolean cashDeposit, double maintenanceCost);

    Optional<BankAtm> getBankAtmById(int id);

    List<BankAtm> getAllBankAtms();

    List<BankAtm> getAllBankAtmsByBank(Bank bank);

    void updateBankAtm(int id, String name);

    void deleteBankAtm(int id);
}