package tech.reliab.course.shamraevLab.bank.repository;

import tech.reliab.course.shamraevLab.bank.entity.Bank;

import java.util.List;
import java.util.Optional;

public interface BankRepository {
    void registerBank(Bank bank);

    Optional<Bank> getBankById(int id);

    List<Bank> getAllBanks();

    void updateBank(int id, String name);

    void deleteBank(int id);

    int addOffice(int id);

    int addAtm(int id);

    int addEmployee(int id);

    int addClient(int id);

    int removeOffice(int id);

    int removeAtm(int id);

    int removeEmployee(int id);

    int removeClient(int id);

    Bank getBankIfExists(int id);
}