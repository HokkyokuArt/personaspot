package artico.personaspot.domain.common;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

public abstract class SuperServiceTest<T extends SuperEntity> extends SuperTest<T> {


    @Test
    void testGetOneByUUID() {
        assertThat(service.getOneByUUID(save().getUuid())).isNotNull();
    }

    @Test
    void testGetPageable() {
        List<T> list = service.getPageable(0, 1, "ASC", "uuid");
        assertThat(list).isNotNull();
        if (repository.count() > 1) {
            assertThat(list).hasSize(1);
        }
    }

    @Test
    void testSave() {
        long before = repository.count();
        save();
        long after = repository.count();
        assertThat(after).isEqualTo(before + 1);
    }

    @Test
    void testUpdate() {
        T save = save();
        long before = repository.count();
        T update = service.update(save.getUuid(), save);
        long after = repository.count();
        assertThat(update.getUuid()).isEqualTo(save.getUuid());
        assertThat(after).isEqualTo(before);
    }

    @Test
    void testDelete() {
        T save = save();
        long before = repository.count();
        service.delete(save.getUuid());
        long after = repository.count();
        assertThat(after).isEqualTo(before - 1);
    }

    private T save() {
        return repository.save(getEntityInstance());
    }
}