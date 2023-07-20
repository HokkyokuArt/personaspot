package artico.personaspot.view.pessoa;

import artico.personaspot.domain.pessoa.*;
import artico.personaspot.view.common.*;
import lombok.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.request.*;

import static artico.personaspot.domain.pessoa.PessoaDataProvider.artico;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PessoaRestControllerIT extends SuperRestControllerTest<PessoaEntity, PessoaDTO> {
    @Autowired
    PessoaRestController pessoaRestController;

    @Test
    void testService() {
        assertThat(pessoaRestController.service()).isEqualTo(pessoaRestController.service);
    }

    @SneakyThrows
    @Test
    void getOneByCpf() {
        var artico = artico();
        repository.save(artico);
        mvc.perform(MockMvcRequestBuilders.get(getBasePath() + "/cpf/41490944036").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Override
    protected String getBasePath() {
        return "/pessoa";
    }

    @Override
    protected String getUuid() {
        return "12a319db-aec9-438f-bb3d-60f102827729";
    }

    @Override
    protected PessoaEntity getEntity() {
        return artico();
    }
}