package com.bitwise.optimisticlocking.service;

import com.bitwise.optimisticlocking.entity.BankAccount;

import java.math.BigDecimal;

public interface BankAccountService {

    BankAccount getById(Long id);

    BankAccount create(BankAccount bankAccount);

    BankAccount update(BankAccount bankAccount);

    BankAccount updateBalanceByAmount(Long id, BigDecimal amount);
}
