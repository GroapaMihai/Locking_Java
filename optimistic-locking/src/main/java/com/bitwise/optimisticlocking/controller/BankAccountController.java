package com.bitwise.optimisticlocking.controller;

import com.bitwise.optimisticlocking.entity.BankAccount;
import com.bitwise.optimisticlocking.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@AllArgsConstructor
@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {

    private BankAccountService bankAccountService;

    @GetMapping("{id}")
    public BankAccount getById(@PathVariable Long id) {
        return bankAccountService.getById(id);
    }

    @PostMapping
    public BankAccount create(@RequestBody BankAccount bankAccount) {
        return bankAccountService.create(bankAccount);
    }

    @PutMapping
    public BankAccount update(@RequestBody BankAccount bankAccount) {
        return bankAccountService.update(bankAccount);
    }

    @PutMapping("{id}/updateBalanceByAmount/{amount}")
    public BankAccount updateBalanceByAmount(@PathVariable Long id, @PathVariable BigDecimal amount) {
        return bankAccountService.updateBalanceByAmount(id, amount);
    }
}
