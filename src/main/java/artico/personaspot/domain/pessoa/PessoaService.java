package artico.personaspot.domain.pessoa;

import artico.personaspot.domain.common.*;
import artico.personaspot.domain.pessoa.contato.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
@Transactional
public class PessoaService extends SuperService<PessoaEntity> {

    @Autowired
    private ContatoRepository contatoRepository;

    public PessoaEntity getOneByCpf(String cpf) {
        return ((PessoaRepository) repository).findByCpf(cpf);
    }

    @Override
    public PessoaEntity update(PessoaEntity oldEntity, PessoaEntity newEntity) {
        oldEntity.setVersion(newEntity.getVersion());
        oldEntity.setNome(newEntity.getNome());
        oldEntity.setCpf(newEntity.getCpf());
        oldEntity.setDataNascimento(newEntity.getDataNascimento());

        List<ContatoEntity> contatos = new ArrayList<>();
        for (ContatoEntity c : newEntity.getContatos()) {
            ContatoEntity contato = contatoRepository.findByUuid(c.getUuid()).orElse(new ContatoEntity());
            contato.setVersion(c.getVersion());
            contato.setNome(c.getNome());
            contato.setTelefone(c.getTelefone());
            contato.setEmail(c.getEmail());
            contato.setPessoa(oldEntity);
            contatos.add(contato);
        }
        oldEntity.getContatos().clear();
        oldEntity.getContatos().addAll(contatos);
        return oldEntity;
    }
}
