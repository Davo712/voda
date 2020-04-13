package com.company.voda.service;

import com.company.voda.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void add(User user);
    void deleteById(int id);
    void update(User user);
    User getById(int id);
    boolean addUser(User user);
    boolean activateUser(String code);

}
