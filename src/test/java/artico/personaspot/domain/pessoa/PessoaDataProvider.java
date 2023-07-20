package artico.personaspot.domain.pessoa;

import artico.personaspot.domain.pessoa.contato.*;

import java.time.*;
import java.util.*;

public class PessoaDataProvider {

    public static PessoaEntity artico() {
        PessoaEntity toReturn = new PessoaEntity(
                "Artico",
                "49127951006",
                LocalDate.now(),
                new ArrayList<>());
        List<ContatoEntity> contatos = new ArrayList<>();
        contatos.add(new ContatoEntity(
                "Contato",
                "44987654321",
                "jeanartico13@hotmail.com",
                toReturn));

        List<ContatoEntity> newContatosList = new ArrayList<>(contatos);
        toReturn.setContatos(newContatosList);
        return toReturn;
    }
}
