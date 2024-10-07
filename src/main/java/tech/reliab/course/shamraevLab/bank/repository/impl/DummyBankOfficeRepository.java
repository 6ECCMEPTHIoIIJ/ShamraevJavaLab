package tech.reliab.course.shamraevLab.bank.repository.impl;

import lombok.RequiredArgsConstructor;
import tech.reliab.course.shamraevLab.bank.entity.Bank;
import tech.reliab.course.shamraevLab.bank.entity.BankOffice;
import tech.reliab.course.shamraevLab.bank.enums.BankOfficeStatus;
import tech.reliab.course.shamraevLab.bank.repository.BankOfficeRepository;
import tech.reliab.course.shamraevLab.bank.repository.BankRepository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DummyBankOfficeRepository implements BankOfficeRepository {
    private static int bankOfficeId = 0;

    private final List<BankOffice> bankOffices = new ArrayList<>();
    private final BankRepository bankRepository;

    @Override
    public BankOffice createBankOffice(String name, String address, boolean canPlaceAtm, boolean canIssueLoan, boolean cashWithdrawal, boolean cashDeposit, double rentCost, Bank bank) {
        BankOffice bankOffice = new BankOffice(name, address, canPlaceAtm, canIssueLoan,
                cashWithdrawal, cashDeposit, rentCost, bank);
        bankOffice.setId(bankOfficeId++);
        bankOffice.setStatus(generateStatus());
        bankOffice.setOfficeMoney(generateOfficeMoney(bank));
        bankOffices.add(bankOffice);
        bankRepository.addOffice(bank.getId());
        return bankOffice;
    }

    @Override
    public Optional<BankOffice> getBankOfficeById(int id) {
        return bankOffices.stream()
                .filter(bankOffice -> bankOffice.getId() == id)
                .findFirst();
    }

    @Override
    public List<BankOffice> getAllBankOffices() {
        return new ArrayList<>(bankOffices);
    }

    @Override
    public List<BankOffice> getAllBankOfficesByBank(Bank bank) {
        return bankOffices.stream()
                .filter(bankOffice -> bankOffice.getBankId() == bank.getId())
                .collect(Collectors.toList());
    }

    @Override
    public void updateBankOffice(int id, String name) {
        BankOffice bankOffice = getBankOfficeIfExists(id);
        bankOffice.setName(name);
    }

    @Override
    public void deleteBankAtm(int officeId, int bankId) {
        BankOffice bankOffice = getBankOfficeIfExists(officeId);
        bankOffices.remove(bankOffice);
        bankRepository.removeOffice(bankId);
    }

    private BankOfficeStatus generateStatus() {
        return Math.random() > 0.5 ? BankOfficeStatus.WORKING : BankOfficeStatus.NOT_WORKING;
    }

    private BankOffice getBankOfficeIfExists(int id) {
        return getBankOfficeById(id).orElseThrow(() -> new NoSuchElementException("BankOffice was not found"));
    }

    private double generateOfficeMoney(Bank bank) {
        return new Random().nextDouble(bank.getTotalMoney());
    }
}
