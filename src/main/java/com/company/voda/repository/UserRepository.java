package com.company.voda.repository;

import com.company.voda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);
    void deleteById(int id);
    User findByActivationCode(String activationCode);


}
