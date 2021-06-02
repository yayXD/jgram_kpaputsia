package world.ucode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import world.ucode.controllers.ControllerUtils;
import world.ucode.domain.Registration;
import world.ucode.service.RegistrationService;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/registration")
    public String main() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid Registration registration, BindingResult bindingResult, Model model) {
//    public String addUser(@RequestParam String username, @RequestParam String password,
//                          @RequestParam String password2, @RequestParam String email, Model model) {
        if(bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            //model.addAttribute("error", "Вы не правильно заполнили поля формы");
            model.addAttribute("error", errorsMap);
        } else {
            if (registration.getUsername() != null && registration.getPassword() != null) {
                if (registration.getPassword().equals(registration.getPassword2()) == true) {
                    if (registrationService.addUser(registration)) {
                        model.addAttribute("mes", "Вы зарегистрированы");
                    } else
                        model.addAttribute("mes", "Этот логин уже существует");
                } else
                    model.addAttribute("mes", "Поля пароль и подтверждение пароля отличаются");
            } else
                model.addAttribute("mes", "Заполните все поля формы");
        }
        return "registration";
    }
}

