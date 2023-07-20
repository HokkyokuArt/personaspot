package artico.personaspot.domain.common;

import artico.personaspot.view.common.*;

public interface Converter<T extends SuperEntity, DTO extends SuperDTO> {
    T dtoToEntity(DTO dto);

    DTO entityToDto(T entity);
}
