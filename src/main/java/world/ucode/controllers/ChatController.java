package world.ucode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import world.ucode.domain.Mes;
import world.ucode.domain.Registration;
import world.ucode.repos.MesRepo;
import world.ucode.repos.RegistrationRepo;

import java.util.Iterator;

@Controller
public class ChatController {
    @Autowired
    MesRepo mesRepo;

    @Autowired
    RegistrationRepo registrationRepo;

    @GetMapping("/chat")
    public String getReceiver(@AuthenticationPrincipal Registration registration, Model model) {
        Iterable<Registration> reg = registrationRepo.findAll();
        Iterator<Registration> iterator = reg.iterator();
        while(iterator.hasNext()) {
            Registration r = iterator.next();
            if (r.getUsername().equals(registration.getUsername()) == true)
                iterator.remove();
        }
        model.addAttribute("users", reg);
        return "chat";
    }

    @GetMapping("/chat/list")
    public String makeMes(@AuthenticationPrincipal Registration registration,
                              @RequestParam("username") String username, Model model) {
        Iterable<Registration> reg = registrationRepo.findAll();
        Iterator<Registration> iterator = reg.iterator();
        while(iterator.hasNext()) {
            Registration r = iterator.next();
            if (r.getUsername().equals(registration.getUsername()) == true)
                iterator.remove();
        }
        model.addAttribute("users", reg);
        Iterable<Mes> mess = mesRepo.findBySenderAndReceiver(registration, username);
        model.addAttribute("mess", mess);
        return "chat";
    }
}
