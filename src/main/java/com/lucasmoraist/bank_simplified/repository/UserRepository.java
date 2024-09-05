package com.lucasmoraist.bank_simplified.repository;

import com.lucasmoraist.bank_simplified.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
