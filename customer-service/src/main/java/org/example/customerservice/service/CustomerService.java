package org.example.customerservice.service;

import org.example.customerservice.entities.Customer;
import org.example.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }
    public List<Customer> allCustomers(){
        return customerRepository.findAll();
    }
    public Customer findCustomerById(Long id){
        return customerRepository.findById(id).orElseThrow(()->new RuntimeException("customer Not Found"));
    }
    public Customer addCustomer(Customer customer){
        return customerRepository.save(customer);
    }
}
