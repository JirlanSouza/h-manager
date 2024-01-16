package com.js.hmanager.booking.domain.application.customer;

import com.js.hmanager.booking.domain.model.customer.Cpf;
import com.js.hmanager.booking.domain.model.customer.Customer;
import com.js.hmanager.booking.domain.model.customer.CustomerRepository;
import com.js.hmanager.common.domainExceptions.ConflictEntityDomainException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateCustomer {

    String id;
    private final CustomerRepository customerRepository;

    public CreateCustomer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public UUID execute(CreateCustomerDto customerData) {
        Cpf customerCpf = new Cpf(customerData.cpf());
        boolean existsCustomer = customerRepository.existsByCpf(customerCpf);

        if (existsCustomer) {
            throw new ConflictEntityDomainException(
                    "The customer with cpf: '%s' already exists".formatted(customerCpf.value())
            );
        }

        Customer customer = new Customer(
                customerData.name(),
                customerCpf,
                customerData.email(),
                customerData.telephone(),
                customerData.address().toDomainAddress()
        );

        customerRepository.save(customer);

        return customer.getId();
    }
}
