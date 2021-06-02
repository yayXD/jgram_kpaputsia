package world.ucode.repos;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import world.ucode.domain.Registration;

@Repository
public interface RegistrationRepo extends CrudRepository<Registration, Long> {
    Registration findByUsername(String username);
    Iterable<Registration> findAll();
}
