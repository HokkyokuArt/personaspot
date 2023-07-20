package artico.personaspot.domain.common;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

import java.util.*;

@NoRepositoryBean
public interface SuperRepository<T extends SuperEntity> extends JpaRepository<T, Long> {
    Optional<T> findByUuid(UUID uuid);
}
