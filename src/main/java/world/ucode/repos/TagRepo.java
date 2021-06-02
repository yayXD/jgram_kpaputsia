package world.ucode.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import world.ucode.domain.Tag;

@Repository
public interface TagRepo extends CrudRepository<Tag, Long> {
    Tag findByTagName(String tagName);
}
