package artico.personaspot.domain.pessoa.contato;

import artico.personaspot.domain.common.*;
import artico.personaspot.domain.pessoa.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CONTATO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContatoEntity extends SuperEntity {
    private String nome;
    private String telefone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "pessoa_fk")
    @JsonBackReference
    private PessoaEntity pessoa;
}
