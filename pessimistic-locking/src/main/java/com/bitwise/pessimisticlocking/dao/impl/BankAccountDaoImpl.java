package com.bitwise.pessimisticlocking.dao.impl;

import com.bitwise.pessimisticlocking.dao.BankAccountDao;
import com.bitwise.pessimisticlocking.entity.BankAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.math.BigDecimal;

@AllArgsConstructor
@Component
public class BankAccountDaoImpl implements BankAccountDao {

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
     * This method updates a bank account object. Created for experimental purposes to illustrate the pessimistic lock concept.
     * If this method is called while 'updateBalanceByAmount' is running for the same bank account entity (unique by id)
     * it will wait for the latter to release the lock before merging the entity.
     *
     * @param bankAccount bank account to update
     * @return updated bank account
     */
    @Override
    public BankAccount update(BankAccount bankAccount) {
        BankAccount existingBankAccount = entityManager.find(BankAccount.class, bankAccount.getId(), LockModeType.PESSIMISTIC_WRITE);

        existingBankAccount.setBalance(bankAccount.getBalance());

        return entityManager.merge(existingBankAccount);
    }

    /**
     * This method updates a bank account's balance by specified amount. Created for experimental purposes to illustrate the pessimistic lock concept.
     * @param id the bank account id
     * @param amount the amount to add to the current balance
     * @return updated bank account
     */
    @Override
    public BankAccount updateBalanceByAmount(Long id, BigDecimal amount) {
        BankAccount bankAccount = entityManager.find(BankAccount.class, id, LockModeType.PESSIMISTIC_WRITE);

        try {
            Thread.sleep(SLEEP_TIME_MS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        bankAccount.setBalance(bankAccount.getBalance().add(amount));

        return entityManager.merge(bankAccount);
    }
}
