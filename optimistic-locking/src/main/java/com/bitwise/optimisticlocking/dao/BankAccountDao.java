package com.bitwise.optimisticlocking.dao;

import com.bitwise.optimisticlocking.entity.BankAccount;

import java.math.BigDecimal;

public interface BankAccountDao {

    BankAccount getById(Long id);

    BankAccount create(BankAccount bankAccount);

    BankAccount update(BankAccount bankAccount);

    BankAccount updateBalanceByAmount(Long id, BigDecimal amount);
}
