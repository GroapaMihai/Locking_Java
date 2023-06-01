package com.bitwise.optimisticlocking.dao.impl;

import com.bitwise.optimisticlocking.dao.BankAccountDao;
import com.bitwise.optimisticlocking.entity.BankAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.math.BigDecimal;

@AllArgsConstructor
@Component
public class BankAccountDaoImpl implements BankAccountDao {

    // Sleep time before merging a Bank Account entity.
    // Useful to have a time gap to send simultaneously multiple updates on same entity (via POSTMAN).
    private static final int SLEEP_TIME_MS = 5000;

    private EntityManager entityManager;

    @Override
    public BankAccount getById(Long id) {
        return entityManager.find(BankAccount.class, id);
    }

    @Override
    public BankAccount create(BankAccount bankAccount) {
        entityManager.persist(bankAccount);

        return bankAccount;
    }

    /**
     * This method updates a bank account object. Created for experimental purposes to illustrate the optimistic lock concept.
     * If this method is called simultaneously with 'updateBalanceByAmount' for the same bank account entity (unique by id)
     * only the first one to reach the 'merge' instruction will succeed and the other will throw 'ObjectOptimisticLockingFailureException'
     * due to concurrent modifications (version field was modified inside other transaction).
     *
     * @param bankAccount bank account to update
     * @return updated bank account
     */
    @Override
    public BankAccount update(BankAccount bankAccount) {
        BankAccount existingBankAccount = entityManager.find(BankAccount.class, bankAccount.getId(), LockModeType.OPTIMISTIC);

        try {
            Thread.sleep(SLEEP_TIME_MS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        existingBankAccount.setBalance(bankAccount.getBalance());

        return entityManager.merge(existingBankAccount);
    }

    /**
     * This method updates a bank account's balance by specified amount. Created for experimental purposes to illustrate the optimistic lock concept.
     * If this method is called simultaneously with 'update' for the same bank account entity (unique by id)
     * only the first one to reach the 'merge' instruction will succeed and the other will throw 'ObjectOptimisticLockingFailureException'
     * due to concurrent modifications (version field was modified inside other transaction).
     * @param id the bank account id
     * @param amount the amount to add to the current balance
     * @return updated bank account
     */
    @Override
    public BankAccount updateBalanceByAmount(Long id, BigDecimal amount) {
        BankAccount bankAccount = entityManager.find(BankAccount.class, id, LockModeType.OPTIMISTIC);

        try {
            Thread.sleep(SLEEP_TIME_MS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        bankAccount.setBalance(bankAccount.getBalance().add(amount));

        return entityManager.merge(bankAccount);
    }
}
