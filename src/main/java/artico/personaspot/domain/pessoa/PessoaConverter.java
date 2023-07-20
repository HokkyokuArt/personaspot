package artico.personaspot.domain.pessoa;

import artico.personaspot.domain.common.*;
import artico.personaspot.domain.pessoa.contato.*;
import artico.personaspot.view.pessoa.*;
import artico.personaspot.view.pessoa.contato.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Component
public class PessoaConverter implements Converter<PessoaEntity, PessoaDTO> {

    @Override
    public PessoaEntity dtoToEntity(PessoaDTO dto) {
        ContatoConverter contatoConverter = new ContatoConverter();
        PessoaEntity toReturn = new PessoaEntity(dto.getNome(), dto.getCpf(), dto.getDataNascimento(), new ArrayList<>());
        List<ContatoEntity> contatos = dto.getContatos().stream()
                .map(c -> contatoConverter.dtoToEntity(c, toReturn)
                ).collect(Collectors.toList());
        toReturn.setContatos(contatos);
        return toReturn;
    }

    @Override
    public PessoaDTO entityToDto(PessoaEntity e) {
        ContatoConverter contatoConverter = new ContatoConverter();
        List<ContatoDTO> contatos = e.getContatos().stream().map(contatoConverter::entityToDto).toList();
        PessoaDTO toReturn = new PessoaDTO(e.getNome(), e.getCpf(), e.getDataNascimento(), contatos);
        toReturn.uuid = e.getUuid();
        toReturn.version = e.getVersion();
        return toReturn;
    }
}
