package artico.personaspot.view.pessoa.contato;

import artico.personaspot.view.common.*;
import jakarta.validation.constraints.*;
import lombok.*;

import static artico.personaspot.domain.common.Utils.*;

@Getter
@AllArgsConstructor
public class ContatoDTO extends SuperDTO {

    @NotBlank(message = "Nome não pode ser vazio")
    private final String nome;

    @NotBlank(message = "Telefone não pode ser vazio")
    private final String telefone;

    @NotNull(message = "Email não pode ser vazio")
    @Email(regexp = ".+[@].+[\\.].+", message = "Email deve ser válido")
    private final String email;

    public String getTelefone() {
        return normalizeTelefone(telefone);
    }

}
