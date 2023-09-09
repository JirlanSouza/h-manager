package com.js.hmanager.booking.domain.application.comandHandlers;

import com.js.hmanager.booking.domain.application.comands.CreateCustomerAddressDto;
import com.js.hmanager.booking.domain.application.comands.CreateCustomerCommand;
import com.js.hmanager.booking.domain.model.customer.Cpf;
import com.js.hmanager.booking.domain.model.customer.Customer;
import com.js.hmanager.booking.domain.model.customer.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCustomerHandlerTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CreateCustomerHandler createCustomerHandler;

    @Test
    @DisplayName("Should create a new customer")
    void createNewCustomer() {
        CreateCustomerCommand command = new CreateCustomerCommand(
                "Joe jho", "111.444.777-35", "joejho@hmanager.com", "1296969696",
                new CreateCustomerAddressDto(
                        "test street",
                        "100",
                        "neighborhood",
                        "12312-123",
                        "City",
                        "State",
                        "Brasil"
                )
        );

        when(customerRepository.existsByCpf(isA(Cpf.class))).thenReturn(false);

        UUID customerId = createCustomerHandler.handle(command);

        assertNotNull(customerId);
        verify(customerRepository, times(1)).save(isA(Customer.class));
    }

}