package org.example.ebankservice.service;

import org.example.ebankservice.entities.BankAccount;
import org.example.ebankservice.repository.EBankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private EBankAccountRepository bankAccountRepository;
    public EbankService(EBankAccountRepository bankAccountRepository){
        this.bankAccountRepository=bankAccountRepository;
    }
    public List<BankAccount> getAllBankAccount(){
        return bankAccountRepository.findAll();
    }
    public BankAccount getBankAccountById(String id){
        return bankAccountRepository.findById(id).orElseThrow(()->new RuntimeException("bank Account NOt Found"));

    }
    public BankAccount save(BankAccount bankAccount){
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreateAt(new Date());
        return bankAccountRepository.save(bankAccount);
    }
}
