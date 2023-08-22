package com.js.hmanager.booking.infra.customer.data;

import com.js.hmanager.booking.domain.model.customer.Cpf;
import com.js.hmanager.booking.domain.model.customer.Customer;
import com.js.hmanager.booking.domain.model.customer.CustomerRepository;
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
        save(new CustomerModel(
                        customer.getId(),
                        customer.getName(),
                        customer.getCpf().value(),
                        customer.getAddress().street(),
                        customer.getAddress().number(),
                        customer.getAddress().neighborhood(),
                        customer.getAddress().zipCode(),
                        customer.getAddress().City(),
                        customer.getAddress().state(),
                        customer.getAddress().country()
                )
        );
    }
}
