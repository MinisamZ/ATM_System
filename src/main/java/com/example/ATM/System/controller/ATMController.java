package com.example.ATM.System.controller;

import com.example.ATM.System.model.User;
import com.example.ATM.System.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ATMController {
    private final BankService bankService;

    @Autowired
    public ATMController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/atm")
    public String findOneUser(@RequestParam(name = "name") String cardNumber, Model model) {
        System.out.println(cardNumber);
        User user = bankService.findByCardNumber(cardNumber);
        System.out.println(user);
        model.addAttribute("user", user);
        return "user/atm";
    }

    @GetMapping("/user/login")
    public String getLogin() {
        return "user/login";
    }

    @PostMapping("/user/login")
    public String login(User user, Model model) {
        if (null == bankService.verification(user.cardNumber, user.getPassword())) return "user/login";
        else {
            user = bankService.findByCardNumber(user.cardNumber);
            model.addAttribute("user", user);
            return "user/atm";
        }
    }

    @GetMapping("/user/login/{cardNumber}")
    public String login(@PathVariable("cardNumber") String cardNumber, Model model) {
        User user = bankService.findByCardNumber(cardNumber);
        model.addAttribute("user", user);
        System.out.println(user.toString());
        return "user/login";
    }

    @GetMapping("/user/balance/{cardNumber}")
    public String balance(@PathVariable("cardNumber") String cardNumber, Model model) {
        System.out.println("balance");
        User user = bankService.findByCardNumber(cardNumber);
        model.addAttribute("user", user);
        System.out.println(user.toString());
        return "user/balance";
    }

    @RequestMapping(value = "/main")
    public String getMainPage() {
        return "main-page";
    }

    @RequestMapping(value = "/list")
    public String getListOfAccounts(Model model) {
        List<User> users = bankService.findAll();
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/admin/users")
    public String findAll(Model model) {
        List<User> users = bankService.findAll();
        model.addAttribute("users", users);
        return "admin/user-list";
    }

    @GetMapping("/admin/user-create")
    public String createUserForm(User user) {
        return "admin/user-create";
    }

    @PostMapping("/admin/user-create")
    public String createUser(User user) {
        System.out.println(user.toString());
        bankService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        bankService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = bankService.findById(id);
        model.addAttribute("user", user);
        return "admin/user-update";
    }

    @PostMapping("/admin/user-update")
    public String updateUser(User user) {
        bankService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/withdrawMoney/{id}")
    public String withdrawMoneyForm(@PathVariable("id") Long id, Model model) {
        User user = bankService.findById(id);
        model.addAttribute("user", user);
        return "user/withdrawMoney";
    }

    @PostMapping("/user/withdrawMoney")
    public String withdrawMoney(User user) {
        int moneySum = user.getMoney();
        user = bankService.findById(user.getId());
        user.setMoney(user.getMoney() - moneySum);
        bankService.saveUser(user);
        return "user/login";
    }

    @GetMapping("/user/topUpAccount/{id}")
    public String topUpAccountForm(@PathVariable("id") Long id, Model model) {
        User user = bankService.findById(id);
        model.addAttribute("user", user);
        return "user/topUpAccount";
    }

    @PostMapping("/user/topUpAccount")
    public String topUpAccount(User user) {
        int moneySum = user.getMoney();
        user = bankService.findById(user.getId());
        user.setMoney(user.getMoney() + moneySum);
        bankService.saveUser(user);
        return "user/login";
    }

    @GetMapping("/user/changePin/{id}")
    public String changePinForm(@PathVariable("id") Long id, Model model) {
        User user = bankService.findById(id);
        model.addAttribute("user", user);
        return "user/changePin";
    }

    @PostMapping("/user/changePin")
    public String changePin(User user) {
        String moneySum = user.getPassword();
        user = bankService.findById(user.getId());
        user.setPassword(moneySum);
        bankService.saveUser(user);
        return "user/login";
    }

}
