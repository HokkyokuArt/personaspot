package artico.personaspot.domain.pessoa;

import artico.personaspot.domain.common.*;

import static artico.personaspot.domain.pessoa.PessoaDataProvider.*;

public class PessoaRepositoryIT extends SuperRepositoryTest<PessoaEntity> {
    @Override
    protected PessoaEntity getEntityInstance() {
        return artico();
    }
}