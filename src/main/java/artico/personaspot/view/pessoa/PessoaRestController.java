package artico.personaspot.view.pessoa;

import artico.personaspot.domain.pessoa.*;
import artico.personaspot.view.common.*;
import org.hibernate.validator.constraints.br.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoa")
public class PessoaRestController extends SuperRestController<PessoaEntity, PessoaDTO, PessoaListDTO> {

    public PessoaService service() {
        return (PessoaService) service;
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<PessoaDTO> getOneByCpf(@PathVariable @CPF String cpf) {
        return ResponseEntity.ok(converter.entityToDto(service().getOneByCpf(cpf)));
    }
}
