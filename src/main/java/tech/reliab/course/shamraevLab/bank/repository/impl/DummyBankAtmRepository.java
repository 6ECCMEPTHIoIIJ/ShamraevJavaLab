package tech.reliab.course.shamraevLab.bank.repository.impl;

import lombok.RequiredArgsConstructor;
import tech.reliab.course.shamraevLab.bank.entity.Bank;
import tech.reliab.course.shamraevLab.bank.entity.BankAtm;
import tech.reliab.course.shamraevLab.bank.enums.BankAtmStatus;
import tech.reliab.course.shamraevLab.bank.entity.BankOffice;
import tech.reliab.course.shamraevLab.bank.entity.Employee;
import tech.reliab.course.shamraevLab.bank.repository.BankAtmRepository;
import tech.reliab.course.shamraevLab.bank.repository.BankRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DummyBankAtmRepository implements BankAtmRepository {
    private static int bankAtmId;

    private final List<BankAtm> bankAtms = new ArrayList<>();
    private final BankRepository bankRepository;

    private final static Random random = new Random();

    @Override
    public BankAtm createBankAtm(String name, String address, Bank bank, BankOffice location, Employee employee, boolean cashWithdrawal, boolean cashDeposit, double maintenanceCost) {
        BankAtm bankAtm = new BankAtm(name, address, bank, location, employee,
                cashWithdrawal, cashDeposit, maintenanceCost);
        bankAtm.setId(bankAtmId++);
        bankAtm.setStatus(generateStatus());
        bankAtm.setAtmMoney(generateAtmMoney(bank));
        bankRepository.addAtm(bank.getId());
        bankAtms.add(bankAtm);
        return bankAtm;
    }

    @Override
    public Optional<BankAtm> getBankAtmById(int id) {
        return bankAtms.stream().filter(bankAtm -> bankAtm.getId() == id).findFirst();
    }

    @Override
    public List<BankAtm> getAllBankAtms() {
        return List.copyOf(bankAtms);
    }

    @Override
    public List<BankAtm> getAllBankAtmsByBank(Bank bank) {
        return bankAtms.stream()
                .filter(bankAtm -> bankAtm.getBank().getId() == bank.getId())
                .collect(Collectors.toList());
    }

    @Override
    public void updateBankAtm(int id, String name) {
        BankAtm bankAtm = getBankAtmIfExists(id);
        bankAtm.setName(name);
    }

    @Override
    public void deleteBankAtm(int id) {
        BankAtm bankAtm = getBankAtmIfExists(id);
        bankAtms.remove(bankAtm);
        Bank bank = bankAtm.getBank();
        bankRepository.removeAtm(bank.getId());
    }

    private BankAtmStatus generateStatus() {
        return Math.random() > 0.5 ? BankAtmStatus.WORKING : BankAtmStatus.NOT_WORKING;
    }

    private double generateAtmMoney(Bank bank) {
        return random.nextDouble(bank.getTotalMoney());
    }

    private BankAtm getBankAtmIfExists(int id) {
        return getBankAtmById(id).orElseThrow(() -> new NoSuchElementException("BankAtm was not found"));
    }
}
