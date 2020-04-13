package com.company.voda.service.impl;

import com.company.voda.model.User;
import com.company.voda.repository.UserRepository;
import com.company.voda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender emailSender;


    @Override
    @Transactional
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User getById(int id) {
        return userRepository.findById(id).get();
    }



    public boolean addUser(User user) {

        User user1 = userRepository.findByUsername(user.getUsername());

        if (user1 != null) {
            return false;
        }


        user.setActive(false);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getUsername());
                message.setSubject("Activate account");
                String message1 = String.format("Please visit next link for account activation:  http://localhost:8080/activate/%s",user.getActivationCode());
                message.setText(message1);
                emailSender.send(message);
            }
        }).start();



        return true;
    }

    @Override
    public boolean activateUser(String code) {


        User user = userRepository.findByActivationCode(code);
        

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);

        return true;
    }

}
