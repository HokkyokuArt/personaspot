package artico.personaspot.domain.common;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import jakarta.validation.constraints.*;

import java.lang.reflect.*;

public class Utils {
    public static String normalizeCPF(@NotNull String cpf) {
        return cpf.replaceAll("\\.", "").replaceAll("-", "");
    }

    public static String normalizeTelefone(@NotNull String telefone) {
        return telefone.replaceAll("[^0-9]", "");
    }

    public static Class<?> inferGenericType(Class<?> clazz, int index) {
        Type superClass = clazz.getGenericSuperclass();
        return (Class<?>) ((ParameterizedType) superClass).getActualTypeArguments()[index];
    }
}
