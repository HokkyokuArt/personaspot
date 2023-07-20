package artico.personaspot.view.common;

import artico.personaspot.domain.common.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static artico.personaspot.domain.common.Utils.*;

public abstract class SuperRestController<T extends SuperEntity, DTO extends SuperDTO, DL extends SuperDTO> {
    @Autowired
    public SuperService<T> service;
    @Autowired
    public Converter<T, DTO> converter;
    private final Class<?> dtoListClass;

    public SuperRestController() {
        this.dtoListClass = inferGenericType(this.getClass(), 2);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DTO> getOne(@PathVariable UUID uuid) {
        return ResponseEntity.ok(converter.entityToDto(service.getOneByUUID(uuid)));
    }

    @GetMapping
    public ResponseEntity<Page<DL>> listAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize,
                                            @RequestParam(defaultValue = "ASC") String sortDirection,
                                            @RequestParam(defaultValue = "uuid") String field) {
        if ("id".equals(field)) {
            field = "uuid";
        }
        List<T> pageable = service.getPageable(page, pageSize, sortDirection, field);
        return ResponseEntity.ok(new PageImpl<>(getDtoList(pageable)));
    }

    @PostMapping
    public ResponseEntity<DTO> save(@RequestBody @Valid DTO dto) {
        T entity = converter.dtoToEntity(dto);
        T saved = service.save(entity);
        return new ResponseEntity<>(converter.entityToDto(saved), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<DTO> update(@RequestBody @Valid DTO dto, @PathVariable @NotNull UUID uuid) {
        T entity = converter.dtoToEntity(dto);
        T updated = service.update(uuid, entity);
        return ResponseEntity.ok(converter.entityToDto(updated));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    private List<DL> getDtoList(List<T> list) {
        List<DL> toReturn = new ArrayList<>();
        for (T t : list) {
            try {
                DL dto = (DL) dtoListClass.getConstructors()[0].newInstance(t);
                toReturn.add(dto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return toReturn;
    }
}
