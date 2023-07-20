package artico.personaspot.domain.pessoa.contato;

import java.util.*;

import static artico.personaspot.domain.pessoa.PessoaDataProvider.*;

public class ContatoDataProvider {
    public static ContatoEntity elonMusk() {
        return new ContatoEntity(
                "Elon Musk",
                "(123) 456-7890",
                "elonmusk@spacex.com",
                artico()
        );
    }

    public static ContatoEntity getContato() {
        return new ContatoEntity(
                "Contato",
                "44987654321",
                "jeanartico13@hotmail.com",
                null);
    }
}
