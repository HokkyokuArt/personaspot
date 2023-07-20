package artico.personaspot.view.pessoa;


import artico.personaspot.domain.pessoa.*;
import artico.personaspot.view.common.*;
import lombok.*;

import java.time.*;

@Getter
public class PessoaListDTO extends SuperDTO {
    private final String nome;
    private final String cpf;
    private final LocalDate dataNascimento;

    public PessoaListDTO(PessoaEntity p) {
        this.uuid = p.getUuid();
        this.version = p.getVersion();
        this.nome = p.getNome();
        this.cpf = p.getCpf();
        this.dataNascimento = p.getDataNascimento();
    }
}
