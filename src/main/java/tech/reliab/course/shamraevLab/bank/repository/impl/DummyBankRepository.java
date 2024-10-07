package tech.reliab.course.shamraevLab.bank.repository.impl;

import lombok.RequiredArgsConstructor;
import tech.reliab.course.shamraevLab.bank.entity.Bank;
import tech.reliab.course.shamraevLab.bank.repository.BankRepository;
import tech.reliab.course.shamraevLab.bank.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class DummyBankRepository implements BankRepository {
    private final List<Bank> banks = new ArrayList<>();

    private final UserRepository userRepository;

    @Override
    public void registerBank(Bank bank) {
        if (getBankById(bank.getId()).isPresent()) return;
        banks.add(bank);
    }

    @Override
    public Optional<Bank> getBankById(int id) {
        Predicate<Bank> filterById = bank -> bank.getId() == id;
        return banks.stream()
                .filter(filterById)
                .findFirst();
    }

    @Override
    public List<Bank> getAllBanks() {
        return new ArrayList<>(banks);
    }

    @Override
    public void updateBank(int id, String name) {
        Bank bank = getBankIfExists(id);
        bank.setName(name);
    }

    @Override
    public void deleteBank(int id) {
        Bank bank = getBankIfExists(id);
        banks.remove(bank);
        userRepository.deleteBank(bank);
    }

    @Override
    public Bank getBankIfExists(int id) throws NoSuchElementException {
        return getBankById(id).orElseThrow(() -> new NoSuchElementException("Bank was not found"));
    }

    @Override
    public int addOffice(int id) {
        var bank = getBankIfExists(id);
        bank.setOfficeCount(bank.getOfficeCount() + 1);
        return bank.getOfficeCount();
    }

    @Override
    public int addAtm(int id) {
        var bank = getBankIfExists(id);
        bank.setAtmCount(bank.getAtmCount() + 1);
        return bank.getAtmCount();
    }

    @Override
    public int addEmployee(int id) {
        var bank = getBankIfExists(id);
        bank.setEmployeeCount(bank.getEmployeeCount() + 1);
        return bank.getEmployeeCount();
    }

    @Override
    public int addClient(int id) {
        var bank = getBankIfExists(id);
        bank.setClientCount(bank.getClientCount() + 1);
        return bank.getClientCount();
    }

    @Override
    public int removeOffice(int id) {
        var bank = getBankIfExists(id);
        bank.setOfficeCount(bank.getOfficeCount() - 1);
        return bank.getOfficeCount();
    }

    @Override
    public int removeAtm(int id) {
        var bank = getBankIfExists(id);
        bank.setAtmCount(bank.getAtmCount() - 1);
        return bank.getAtmCount();
    }

    @Override
    public int removeEmployee(int id) {
        var bank = getBankIfExists(id);
        bank.setEmployeeCount(bank.getEmployeeCount() - 1);
        return bank.getEmployeeCount();
    }

    @Override
    public int removeClient(int id) {
        var bank = getBankIfExists(id);
        bank.setClientCount(bank.getClientCount() - 1);
        return bank.getClientCount();
    }
}