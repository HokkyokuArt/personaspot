package artico.personaspot.domain.pessoa.contato;

import artico.personaspot.domain.common.*;
import artico.personaspot.domain.pessoa.*;
import artico.personaspot.view.pessoa.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

import java.util.*;

import static artico.personaspot.domain.pessoa.PessoaDataProvider.*;
import static artico.personaspot.domain.pessoa.contato.ContatoDataProvider.*;
import static org.assertj.core.api.Assertions.*;

class ContatoConverterTest {

    ContatoConverter converter = new ContatoConverter();

    @Test
    void testDtoToEntity() {
        var dto = converter.entityToDto(elonMusk());
        var artico = artico();
        artico.setUuid(UUID.randomUUID());
        var converterdEntity = converter.dtoToEntity(dto, artico);

        assertThat(converterdEntity.getNome()).isEqualTo(dto.getNome());
        assertThat(converterdEntity.getTelefone()).isEqualTo(dto.getTelefone());
        assertThat(converterdEntity.getEmail()).isEqualTo(dto.getEmail());
        assertThat(converterdEntity.getPessoa()).isEqualTo(artico);
    }

    @Test
    void testEntityToDto() {
        var ent = elonMusk();
        ent.setUuid(UUID.randomUUID());
        ent.setVersion(0);
        var convertedDTO = converter.entityToDto(ent);

        assertThat(convertedDTO.getVersion()).isEqualTo(ent.getVersion());
        assertThat(convertedDTO.getUuid()).isEqualTo(ent.getUuid());
        assertThat(convertedDTO.getNome()).isEqualTo(ent.getNome());
        assertThat(convertedDTO.getTelefone()).isEqualTo(Utils.normalizeTelefone(ent.getTelefone()));
        assertThat(convertedDTO.getEmail()).isEqualTo(ent.getEmail());
    }
}