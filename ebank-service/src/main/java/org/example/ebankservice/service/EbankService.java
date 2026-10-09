package org.example.ebankservice.service;

import org.example.ebankservice.Fiegn.CustomerRestClient;
import org.example.ebankservice.entities.BankAccount;
import org.example.ebankservice.model.Customer;
import org.example.ebankservice.repository.EBankAccountRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private EBankAccountRepository bankAccountRepository;
    private CustomerRestClient customerRestClient;
    public EbankService(EBankAccountRepository bankAccountRepository , CustomerRestClient customerRestClient){
        this.customerRestClient=customerRestClient;
        this.bankAccountRepository=bankAccountRepository;
    }
    @McpTool(description = "get all Bank Account")
    public List<BankAccount> getAllBankAccount(){
        return bankAccountRepository.findAll();
    }
    @McpTool(description = "get account by id")
    public BankAccount getBankAccountById(@McpToolParam(description = "the ID of Account ") String id){
        BankAccount bankAccount=  bankAccountRepository.findById(id).orElseThrow(()->new RuntimeException("bank Account NOt Found"));
        Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());
        bankAccount.setCustomer(customer);
        return bankAccount;

    }
    @McpTool(description = "save new Bank account")
    public BankAccount save(@McpToolParam(description = "the bank account to save") BankAccount bankAccount){
        try {
            Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());
            bankAccount.setCustomer(customer);
            bankAccount.setId(UUID.randomUUID().toString());
            bankAccount.setCreateAt(new Date());
            return bankAccountRepository.save(bankAccount);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
