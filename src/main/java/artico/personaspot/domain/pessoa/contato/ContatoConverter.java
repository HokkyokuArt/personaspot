package artico.personaspot.domain.pessoa.contato;

import artico.personaspot.domain.pessoa.*;
import artico.personaspot.view.pessoa.contato.*;
import org.springframework.stereotype.*;

@Component
public class ContatoConverter {

    public ContatoEntity dtoToEntity(ContatoDTO dto, PessoaEntity pessoa) {
        ContatoEntity toReturn = new ContatoEntity(dto.getNome(), dto.getTelefone(), dto.getEmail(), pessoa);
        toReturn.setUuid(dto.getUuid());
        toReturn.setVersion(dto.getVersion());
        return toReturn;
    }

    public ContatoDTO entityToDto(ContatoEntity e) {
        ContatoDTO toReturn = new ContatoDTO(e.getNome(), e.getTelefone(), e.getEmail());
        toReturn.uuid = e.getUuid();
        toReturn.version = e.getVersion();
        return toReturn;
    }
}
