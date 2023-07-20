package artico.personaspot.domain.pessoa;

import artico.personaspot.domain.common.*;
import artico.personaspot.domain.pessoa.contato.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "PESSOA")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PessoaEntity extends SuperEntity {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "pessoa", orphanRemoval = true)
    @JsonManagedReference
    private List<ContatoEntity> contatos = new ArrayList<>();

}
