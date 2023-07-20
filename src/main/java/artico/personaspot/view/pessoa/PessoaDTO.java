package artico.personaspot.view.pessoa;

import artico.personaspot.view.common.*;
import artico.personaspot.view.pessoa.contato.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.*;

import java.time.*;
import java.util.*;

import static artico.personaspot.domain.common.Utils.*;

@Getter
@AllArgsConstructor
public class PessoaDTO extends SuperDTO {

    @NotBlank(message = "Nome não pode ser vazio")
    private final String nome;

    @NotNull(message = "CPF não pode ser vazio")
    @CPF(message = "CPF deve ser válido")
    private final String cpf;

    @NotNull(message = "Nascimento não pode ser vazio")
    @PastOrPresent(message = "Nascimento não pode ser uma data futura")
    private final LocalDate dataNascimento;

    @NotEmpty(message = "Contatos não pode ser vazio")
    private final List<@Valid ContatoDTO> contatos;

    public String getCpf() {
        return normalizeCPF(cpf);
    }
}
