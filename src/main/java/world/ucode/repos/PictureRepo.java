package world.ucode.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import world.ucode.domain.Pictures;
import world.ucode.domain.Profile;

import java.util.List;

@Repository
public interface PictureRepo extends CrudRepository<Pictures, Long> {
    List<Pictures> findByProfile(Profile profile);
}
