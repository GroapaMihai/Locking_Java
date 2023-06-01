package com.bitwise.pessimisticlocking.service;

import com.bitwise.pessimisticlocking.entity.BankAccount;

import java.math.BigDecimal;

public interface BankAccountService {

    BankAccount getById(Long id);

    BankAccount create(BankAccount bankAccount);

    BankAccount update(BankAccount bankAccount);

    BankAccount updateBalanceByAmount(Long id, BigDecimal amount);
}
