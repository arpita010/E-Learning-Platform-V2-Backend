package com.app.payment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepo extends CrudRepository<Payment, Long> {
  Optional<List<Payment>> findAllByUserEmail(String email);
}
