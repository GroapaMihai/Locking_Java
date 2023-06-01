package com.bitwise.optimisticlocking.service.impl;

import com.bitwise.optimisticlocking.dao.BankAccountDao;
import com.bitwise.optimisticlocking.entity.BankAccount;
import com.bitwise.optimisticlocking.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountDao bankAccountDao;

    @Override
    public BankAccount getById(Long id) {
        return bankAccountDao.getById(id);
    }

    @Override
    public BankAccount create(BankAccount bankAccount) {
        return bankAccountDao.create(bankAccount);
    }

    @Override
    public BankAccount update(BankAccount bankAccount) {
        return bankAccountDao.update(bankAccount);
    }

    @Override
    public BankAccount updateBalanceByAmount(Long id, BigDecimal amount) {
        return bankAccountDao.updateBalanceByAmount(id, amount);
    }
}
