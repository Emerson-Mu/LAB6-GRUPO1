package edu.pucp.gtics.lab6_gtics_20211.controller;

import edu.pucp.gtics.lab6_gtics_20211.entity.User;
import edu.pucp.gtics.lab6_gtics_20211.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = {"/user/signIn"})
    public String signIn(){
        return "user/signIn";
    }

    @GetMapping("/user/signInRedirect")
    public String signInRedirect(Authentication auth, HttpSession session){
        User user = userRepository.findByCorreo(auth.getName());
        session.setAttribute("user", user);

        String rol ="";
        for (GrantedAuthority role : auth.getAuthorities()) {
            rol = role.getAuthority();
            break;
        }
        if (rol.equals("ADMIN")) {
            return "redirect:/juegos/lista";
        }else {
            return "redirect:/juegos/lista";
        }

    }

    @GetMapping("/user/signUp")
    public String signUp(@ModelAttribute("user") User user){
        return "user/signUp";
    }

    @PostMapping("/user/save")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, RedirectAttributes attr) {
        if(bindingResult.hasErrors()){
            return "user/signUp";
        } else {
            try {
                attr.addFlashAttribute("msg","Usuario Registrado Exitosamente. Puede Iniciar Sesión.");
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                /*user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setEnable(1);
                user.setRoleId(2);*/
                userRepository.save(user);
                return "redirect:/";
            } catch (DataIntegrityViolationException ex) {
                bindingResult.rejectValue("username", "typeMismatch"); // pass an error message to the view
                return "user/signUp";
            }
        }
    }
}
