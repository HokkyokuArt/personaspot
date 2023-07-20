package artico.personaspot.domain.pessoa;

import artico.personaspot.domain.pessoa.contato.*;
import lombok.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.lang.reflect.*;
import java.time.*;
import java.util.*;

import static artico.personaspot.domain.pessoa.PessoaDataProvider.*;
import static artico.personaspot.domain.pessoa.contato.ContatoDataProvider.*;
import static org.assertj.core.api.Assertions.*;

public class PessoaTest {
    @SneakyThrows
    @Test
    void testUpdate() {
        ContatoRepository contatoRepository = Mockito.mock(ContatoRepository.class);
        Mockito.when(contatoRepository.findByUuid(Mockito.any())).thenReturn(Optional.of(getContato()));
        PessoaService pessoaService = new PessoaService();
        Field cr = pessoaService.getClass().getDeclaredField("contatoRepository");
        cr.setAccessible(true);
        cr.set(pessoaService, contatoRepository);

        PessoaEntity oldEntity = artico();
        PessoaEntity newEntity = artico();
        newEntity.setVersion(3);
        newEntity.setNome("ocitrA");
        newEntity.setCpf("");
        newEntity.setDataNascimento(LocalDate.of(1969, 7, 20));

        PessoaEntity update = pessoaService.update(oldEntity, newEntity);

        assertThat(update.getNome()).isNotEqualTo(artico().getNome());
        assertThat(update.getNome()).isEqualTo(newEntity.getNome());

        assertThat(update.getCpf()).isNotEqualTo(artico().getCpf());
        assertThat(update.getCpf()).isEqualTo(newEntity.getCpf());

        assertThat(update.getDataNascimento()).isNotEqualTo(artico().getDataNascimento());
        assertThat(update.getDataNascimento()).isEqualTo(newEntity.getDataNascimento());
    }
}
