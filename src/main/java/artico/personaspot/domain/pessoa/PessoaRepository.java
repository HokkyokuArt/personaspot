package artico.personaspot.domain.pessoa;

import artico.personaspot.domain.common.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface PessoaRepository extends SuperRepository<PessoaEntity> {

    PessoaEntity findByCpf(String cpf);
}
