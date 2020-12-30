package ru.javalab.hateoas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.javalab.hateoas.models.Shoper;
import ru.javalab.hateoas.services.ShopersService;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ShoperTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopersService shopersService;

    @BeforeEach
    public void setUp() {
        when(shopersService.ban(1L)).thenReturn(getShoper());
    }

    @Test
    public void shoperPublishTest() throws Exception {
        mockMvc.perform(put("/shopers/1/ban")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(getShoper().getName()))
                .andExpect(jsonPath("$.surname").value(getShoper().getSurname()))
                .andExpect(jsonPath("$.status").value(getShoper().getStatus()))
                .andDo(document("publish_course", responseFields(
                        fieldWithPath("name").description("Имя"),
                        fieldWithPath("surname").description("Фамилия"),
                        fieldWithPath("status").description("Статус профиля")
                )));
    }

    private Shoper getShoper() {
        return Shoper.builder()
                .id(1L)
                .name("Mikhail")
                .surname("Trubov")
                .status("Active")
                .build();
    }
}
