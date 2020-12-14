package se.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import se.domain.User;
import se.repository.UserRepository;
import se.repository.UserRepositoryImpl;
import se.service.FileService;
import se.service.FileServiceImpl;

import javax.validation.Valid;

@Controller
public class Authorization {
    static UserRepository users = new UserRepositoryImpl();

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login_success_handler")
    public String loginSuccessHandler(@RequestBody @Valid User user,
                                      Model model) {

        User userEntity = null;
        try {
            userEntity = users.getByLogPass(user.getLogin(), user.getPassword());
        } catch (Exception e) {
            System.out.println("no user in data base");
        }
        if (userEntity == null)
            return "registration";

        model.addAttribute("token", "jopa");
        return "welcome";
    }

    @GetMapping("/login_failure_handler")
    public String loginFailureHandler() {

        return "login";
    }

    @PostMapping("/login_failure_handler")
    public String postloginFailureHandler(Model model) {
        model.addAttribute("error", "bad login and password");
        return "login";
    }

    @GetMapping("/logout_success")
    public String logout_success() {
        return "logout_success";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(@RequestBody @Valid User user, BindingResult bindingResult,
                          Model model) {

        System.out.println("registration!!!  " + user.getLogin() + " " + user.getPassword());
        if (users.getUserByUsername(user.getLogin()) != null) {
            model.addAttribute("error", "username is already exist");
            return "registration";
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        System.out.println(user.getPassword());
        System.out.println(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        users.save(user);

        return "redirect:/files";
    }


}
