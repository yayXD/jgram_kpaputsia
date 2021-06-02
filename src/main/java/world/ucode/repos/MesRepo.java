package world.ucode.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import world.ucode.domain.Mes;
import world.ucode.domain.Registration;

@Repository
public interface MesRepo extends CrudRepository<Mes, Long> {
    Iterable<Mes> findBySenderAndReceiver(Registration reristration, String receiver);
}
