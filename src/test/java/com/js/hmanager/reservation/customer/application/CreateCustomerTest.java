package com.js.hmanager.reservation.customer.application;

import com.js.hmanager.reservation.customer.domain.Cpf;
import com.js.hmanager.reservation.customer.domain.Customer;
import com.js.hmanager.reservation.customer.domain.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCustomerTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateCustomer createCustomer;

    @Test
    @DisplayName("Should create a new customer")
    void createNewCustomer() {
        CreateCustomerDto command = new CreateCustomerDto(
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

        UUID customerId = createCustomer.execute(command);

        assertThat(customerId).isNotNull();
        verify(customerRepository, times(1)).save(isA(Customer.class));
    }

}