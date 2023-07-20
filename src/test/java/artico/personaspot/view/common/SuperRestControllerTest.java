package artico.personaspot.view.common;

import artico.personaspot.domain.common.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.*;
import lombok.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;
import org.springframework.transaction.annotation.*;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public abstract class SuperRestControllerTest<T extends SuperEntity, DTO extends SuperDTO> {
    @Autowired
    public MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    @Autowired
    public Converter<T, DTO> converter;
    @Autowired
    public SuperRepository<T> repository;
    private Class<?> classType;
    private Class<?> dtoType;

    public SuperRestControllerTest() {
        classType = Utils.inferGenericType(this.getClass(), 0);
        dtoType = Utils.inferGenericType(this.getClass(), 1);
    }

    protected abstract String getBasePath();

    protected abstract String getUuid();

    protected abstract T getEntity();

    @SneakyThrows
    @Test
    void testGetOne() {
        mvc.perform(MockMvcRequestBuilders.get(getBasePath() + "/" + getUuid()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void testGet_InvalidUuid() {
        mvc.perform(MockMvcRequestBuilders.get(getBasePath() + "/123123").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void testListAll() {
        mvc.perform(MockMvcRequestBuilders.get(getBasePath())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].uuid").isNotEmpty());
    }

    @SneakyThrows
    @Test
    void testListAll_page2() {
        mvc.perform(MockMvcRequestBuilders.get(getBasePath())
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page", "2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty());
    }

    @SneakyThrows
//    @Test
    void testListAll_Sort_ID() {
        // TODO: 20/07/2023 validate sort
        mvc.perform(MockMvcRequestBuilders.get(getBasePath())
                        .accept(MediaType.APPLICATION_JSON)
                        .param("field", "id"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].uuid").isNotEmpty());
    }


    @SneakyThrows
    @Test
    void testSave() {
        long before = repository.count();
        String response = mvc.perform(MockMvcRequestBuilders.post(getBasePath())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonContent()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        T saved = objecOf(response);
        long after = repository.count();
        assertThat(repository.findByUuid(saved.getUuid()).orElse(null)).isNotNull();
        assertThat(after).isEqualTo(before + 1);
    }

    @SneakyThrows
    @Test
    void testUpdate() {
        String response = mvc.perform(MockMvcRequestBuilders.post(getBasePath())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonContent()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        T saved = objecOf(response);
        assertThat(repository.findByUuid(saved.getUuid()).orElse(null)).isNotNull();

        long before = repository.count();

        String response2 = mvc.perform(MockMvcRequestBuilders
                        .put(getBasePath() + "/" + saved.getUuid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOf(converter.entityToDto(saved)))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        T updated = objecOf(response2);
        long after = repository.count();
        assertThat(updated.getUuid()).isEqualTo(updated.getUuid());
        assertThat(after).isEqualTo(before);
    }

    @SneakyThrows
    @Test
    void testDelete() {
        String response = mvc.perform(MockMvcRequestBuilders.post(getBasePath())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonContent()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        T saved = objecOf(response);
        assertThat(repository.findByUuid(saved.getUuid()).orElse(null)).isNotNull();

        long before = repository.count();
        mvc.perform(MockMvcRequestBuilders
                        .delete(getBasePath() + "/" + saved.getUuid())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(repository.findByUuid(saved.getUuid()).orElse(null)).isNull();
        long after = repository.count();
        assertThat(after).isEqualTo(before - 1);
    }

    private String getJsonContent() throws JsonProcessingException {
        return jsonOf(converter.entityToDto(getEntity()));
    }

    private String jsonOf(Object obj) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    private T objecOf(String json) throws JsonProcessingException {
        return (T) mapper.readValue(json, classType);
    }
}