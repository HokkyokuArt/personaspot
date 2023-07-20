package artico.personaspot.domain.pessoa;

import artico.personaspot.domain.common.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import static artico.personaspot.domain.pessoa.PessoaDataProvider.*;

public class PessoaServiceIT extends SuperServiceTest<PessoaEntity> {

    PessoaService service() {
        return (PessoaService) service;
    }

    @Test
    void getOneByCpf() {
        PessoaEntity save = repository.save(artico());
        PessoaEntity oneByCpf = service().getOneByCpf(save.getCpf());
        Assertions.assertThat(oneByCpf).isNotNull();
    }

    @Override
    protected PessoaEntity getEntityInstance() {
        return artico();
    }
}