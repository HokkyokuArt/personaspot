package artico.personaspot.domain.common;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@MappedSuperclass
public abstract class SuperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID uuid;

    @Version
    @Column
    protected Integer version;

    @PrePersist
    private void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
