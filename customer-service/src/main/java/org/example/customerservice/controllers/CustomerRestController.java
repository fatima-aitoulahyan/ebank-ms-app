package org.example.customerservice.controllers;

import org.example.customerservice.entities.Customer;
import org.example.customerservice.repository.CustomerRepository;
import org.example.customerservice.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerRestController {
    private CustomerService customerService;
    public CustomerRestController(CustomerService customerService){
        this.customerService= customerService;
    }
    @GetMapping("/customers")
    public List<Customer> allCustomers(){
        return customerService.allCustomers();
    }
    @GetMapping("/customers/{id}")
    public Customer findCustomerById(@PathVariable Long id){
        return customerService.findCustomerById(id);
    }
    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }
}
