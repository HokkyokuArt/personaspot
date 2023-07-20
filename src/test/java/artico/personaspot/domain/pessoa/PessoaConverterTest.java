package artico.personaspot.domain.pessoa;

import artico.personaspot.domain.common.*;
import artico.personaspot.view.pessoa.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static artico.personaspot.domain.pessoa.PessoaDataProvider.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PessoaConverterTest {
    PessoaConverter converter = new PessoaConverter();

    @Test
    void testDtoToEntity() {
        PessoaDTO articoDTO = converter.entityToDto(artico());
        PessoaEntity convertedEntity = converter.dtoToEntity(articoDTO);
        assertThat(convertedEntity.getNome()).isEqualTo(articoDTO.getNome());
        assertThat(convertedEntity.getCpf()).isEqualTo(articoDTO.getCpf());
        assertThat(convertedEntity.getDataNascimento()).isEqualTo(articoDTO.getDataNascimento());
        assertThat(convertedEntity.getContatos()).hasSize(1);
    }

    @Test
    void testEntityToDto() {
        PessoaEntity artico = artico();
        artico.setUuid(UUID.randomUUID());
        artico.setVersion(0);
        PessoaDTO convertedDTO = converter.entityToDto(artico);

        assertThat(convertedDTO.getVersion()).isEqualTo(artico.getVersion());
        assertThat(convertedDTO.getUuid()).isEqualTo(artico.getUuid());
        assertThat(convertedDTO.getNome()).isEqualTo(artico.getNome());
        assertThat(convertedDTO.getCpf()).isEqualTo(Utils.normalizeCPF(artico.getCpf()));
        assertThat(convertedDTO.getDataNascimento()).isEqualTo(artico.getDataNascimento());
        assertThat(convertedDTO.getContatos()).hasSize(1);

    }
}