package artico.personaspot.domain.common;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;

import java.util.*;

public abstract class SuperService<T extends SuperEntity> {
    @Autowired
    protected SuperRepository<T> repository;

    public T getOneByUUID(UUID uuid) {
        return repository.findByUuid(uuid).orElseThrow();
    }

    public List<T> getPageable(int page, int pageSize, String sortDirection, String field) {
        Pageable pageable = PageRequest.of(page, pageSize).withSort(Sort.Direction.valueOf(sortDirection), field);
        Page<T> all = repository.findAll(pageable);
        return all.getContent();
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public T update(UUID uuid, T newEntity) {
        T oldEntity = getOneByUUID(uuid);
        return save(update(oldEntity, newEntity));
    }

    public abstract T update(T oldEntity, T newEntity);

    public void delete(UUID uuid) {
        T entity = repository.findByUuid(uuid).orElseThrow();
        repository.delete(entity);
    }
}
