package com.js.hmanager.reservation.customer.data;

import com.js.hmanager.reservation.customer.domain.Cpf;
import com.js.hmanager.reservation.customer.domain.Customer;
import com.js.hmanager.reservation.customer.domain.CustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerModel, UUID>, CustomerRepository {

    default boolean exists(UUID id) {
        return existsById(id);
    }

    default boolean existsByCpf(Cpf cpf) {
        return existsByCpf(cpf.value());
    }

    boolean existsByCpf(String cpfValue);

    default void save(Customer customer) {
        save(CustomerModel.builder()
                .id(customer.getId())
                .name(customer.getName())
                .cpf(customer.getCpf().value())
                .email(customer.getEmail())
                .telephone(customer.getTelephone())
                .addressStreet(customer.getAddress().street())
                .addressNumber(customer.getAddress().houseNumber())
                .addressNeighborhood(customer.getAddress().neighborhood())
                .addressZipCode(customer.getAddress().zipCode())
                .addressCity(customer.getAddress().City())
                .addressState(customer.getAddress().state())
                .addressCountry(customer.getAddress().country())
                .customerSince(customer.getCustomerSince())
                .build()
        );
    }
}
