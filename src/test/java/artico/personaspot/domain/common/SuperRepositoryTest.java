package artico.personaspot.domain.common;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

public abstract class SuperRepositoryTest<T extends SuperEntity> extends SuperTest<T> {

    @Test
    public void testList() {
        List<T> all = repository.findAll();
        assertThat(all).isNotNull();
    }

    @Test
    public void testFindOne() {
        T one = repository.findByUuid(save().getUuid()).orElseThrow();
        assertThat(one).isNotNull();
    }

    @Test
    public void testSave() {
        long before = repository.count();
        T one = getEntityInstance();
        T saved = repository.save(one);
        long after = repository.count();
        assertThat(saved.getUuid()).isNotNull();
        assertThat(after).isEqualTo(before + 1);
    }

    @Test
    public void testUpdate() {
        T save = save();
        long before = repository.count();
        T byUuid = repository.findByUuid(save.getUuid()).orElseThrow();
        T saved = repository.save(byUuid);
        long after = repository.count();
        assertThat(saved).isEqualTo(byUuid);
        assertThat(after).isEqualTo(before);
    }

    @Test
    public void testDelete() {
        T save = save();
        long before = repository.count();
        T byUuid = repository.findByUuid(save.getUuid()).orElseThrow();
        repository.delete(byUuid);
        T excluded = repository.findByUuid(byUuid.getUuid()).orElse(null);
        long after = repository.count();
        assertThat(excluded).isNull();
        assertThat(after).isEqualTo(before - 1);
    }

    private T save() {
        return repository.save(getEntityInstance());
    }
}
