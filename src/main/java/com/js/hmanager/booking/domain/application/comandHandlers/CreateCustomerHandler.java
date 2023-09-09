package com.js.hmanager.booking.domain.application.comandHandlers;

import com.js.hmanager.booking.domain.application.comands.CreateCustomerCommand;
import com.js.hmanager.booking.domain.model.customer.Cpf;
import com.js.hmanager.booking.domain.model.customer.Customer;
import com.js.hmanager.booking.domain.model.customer.CustomerRepository;
import com.js.hmanager.sharad.domainExceptions.ConflictEntityDomainException;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateCustomerHandler {

    String id;
    private final CustomerRepository customerRepository;

    public CreateCustomerHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @CommandHandler
    public UUID handle(CreateCustomerCommand command) {
        Cpf customerCpf = new Cpf(command.cpf());
        boolean existsCustomer = customerRepository.existsByCpf(customerCpf);

        if (existsCustomer) {
            throw new ConflictEntityDomainException(
                    "The customer with cpf: '%s' already exists".formatted(customerCpf.value())
            );
        }

        Customer customer = new Customer(
                command.name(),
                customerCpf,
                command.email(),
                command.telephone(),
                command.address().toDomainAddress()
        );

        customerRepository.save(customer);

        return customer.getId();
    }
}
