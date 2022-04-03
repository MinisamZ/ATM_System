package com.example.ATM.System.service;

import com.example.ATM.System.model.User;
import com.example.ATM.System.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }
    public User findById(Long id) {
        return bankAccountRepository.getOne(id);
    }

    public List<User> findAll() {
        return bankAccountRepository.findAll();
    }

    public User saveUser(User user) {
        return bankAccountRepository.save(user);
    }
    public User findByCardNumber(String s){
        return bankAccountRepository.findByCardNumber(s);
    }
    public User verification(String cardNumber, String password){
        return bankAccountRepository.verification(cardNumber, password);
    }
    public void deleteById(Long id) {
        bankAccountRepository.deleteById(id);
    }
}
