package world.ucode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import world.ucode.domain.Registration;
import world.ucode.repos.RegistrationRepo;
import world.ucode.domain.Role;

import java.util.Collection;
import java.util.Collections;

@Service
public class RegistrationService implements UserDetailsService {
    @Autowired
    private RegistrationRepo registrationRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Registration registration = registrationRepo.findByUsername(username);
        if(registration == null)
            throw new UsernameNotFoundException("User not found");
        return registration;
    }

    public boolean addUser(Registration registration) {
        Registration registration2 = registrationRepo.findByUsername(registration.getUsername());
        if(registration2 != null)
            return false;
        registration.setActive(true);
        registration.setRoles(Collections.singleton(Role.USER));
        registration.setPassword(passwordEncoder.encode(registration.getPassword()));
        registrationRepo.save(registration);
        return true;
    }
}
