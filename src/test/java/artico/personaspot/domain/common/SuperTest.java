package artico.personaspot.domain.common;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@SpringBootTest
@Transactional
public abstract class SuperTest<T extends SuperEntity> {

    protected abstract T getEntityInstance();

//    protected abstract UUID getUuid();
    @Autowired
    public SuperRepository<T> repository;

    @Autowired
    public SuperService<T> service;

}
