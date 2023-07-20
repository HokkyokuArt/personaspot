package artico.personaspot.domain.common;

import artico.personaspot.view.pessoa.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

import java.util.*;

import static artico.personaspot.domain.common.Utils.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class UtilsTest {
    @Test
    void testNormalizeCPF() {
        String normalized = normalizeCPF("598.015.600-37");
        assertThat(normalized).isEqualTo("59801560037");
        assertThat(normalized).doesNotContain(".", "-");
    }

    @Test
    void testNormalizeTelefone() {
        String normalized = normalizeTelefone("(123) 456-7890A");
        assertThat(normalized).isEqualTo("1234567890");
        assertThat(normalized).doesNotContain(")", "-", "A", " ");
    }

    @Test
    void testInferGenericType() {
        List<PessoaDTO> clazz = new ArrayList<>() {
        };
        Class<?> infered = inferGenericType(clazz.getClass(), 0);
        assertThat(infered).isEqualTo(PessoaDTO.class);
    }
}
