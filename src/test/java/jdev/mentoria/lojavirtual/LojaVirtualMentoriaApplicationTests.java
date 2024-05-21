package jdev.mentoria.lojavirtual;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdev.mentoria.lojavirtual.controller.AcessoController;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class LojaVirtualMentoriaApplicationTests {

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private AcessoController acessoController;

    @Autowired
    private WebApplicationContext wac;

    /*Teste do end point salva acesso*/
    @Test
    public void testRestApiCadastroAcesso() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_ADMIN");

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.post("/salvarAcesso")
                .content(objectMapper.writeValueAsString(acesso)).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        /*Converter o retorno da API para um objeto de acesso*/
        String retornoDaApi = retornoApi.andReturn().getResponse().getContentAsString();
        System.out.println("Retorno da API: " + retornoDaApi);

        Acesso objetoRetorno = objectMapper.readValue(retornoDaApi, Acesso.class);

        assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());

//        acessoRepository.deleteById(objetoRetorno.getId());

    }

    /*Teste do end point delete acesso*/
    @Test
    public void testRestApiDeleteAcesso() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_DELETE_ACESSO");

        acesso = acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.post("/deleteAcesso")
                .content(objectMapper.writeValueAsString(acesso)).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        String retornoDaApi = retornoApi.andReturn().getResponse().getContentAsString();
        String statusDoRetorno = String.valueOf(retornoApi.andReturn().getResponse().getStatus());

        System.out.println("Retorno da API: " + retornoDaApi);
        System.out.println("Status do retorno: " + statusDoRetorno);

        assertEquals("Acesso removido - ID: " + acesso.getId(), retornoDaApi);
        assertEquals("200", statusDoRetorno);

    }

    @Test
    public void testRestApiDeleteAcessoPorId() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_DELETE_ACESSO_ID");

        acesso = acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/" + acesso.getId())
                .content(objectMapper.writeValueAsString(acesso)).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        String retornoDaApi = retornoApi.andReturn().getResponse().getContentAsString();
        String statusDoRetorno = String.valueOf(retornoApi.andReturn().getResponse().getStatus());

        System.out.println("Retorno da API: " + retornoDaApi);
        System.out.println("Status do retorno: " + statusDoRetorno);

        assertEquals("Acesso removido - ID: " + acesso.getId(), retornoDaApi);
        assertEquals("200", statusDoRetorno);
    }

    @Test
    public void testRestApiObterAcesso() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_OBTER_ACESSO_ID");

        acesso = acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.get("/obterAcesso/" + acesso.getId())
                .content(objectMapper.writeValueAsString(acesso)).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        String statusDoRetorno = String.valueOf(retornoApi.andReturn().getResponse().getStatus());
        String retornoDaApi = retornoApi.andReturn().getResponse().getContentAsString();

        assertEquals("200", statusDoRetorno);

        Acesso acessoRetorno = objectMapper.readValue(retornoDaApi, Acesso.class);

        assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());

        acessoRepository.deleteById(acesso.getId());
    }

    @Test
    public void testRestApiBuscarPorDesc() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_TESTE_OBTER_LIST_POR_DESCRICAO");

        acesso = acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.get("/buscarPorDesc/" + acesso.getDescricao())
                .content(objectMapper.writeValueAsString(acesso)).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        String statusDoRetorno = String.valueOf(retornoApi.andReturn().getResponse().getStatus());
        String retornoDaApi = retornoApi.andReturn().getResponse().getContentAsString();

        assertEquals("200", statusDoRetorno);
        List<Acesso> retornoApiList = objectMapper.readValue(retornoDaApi, new TypeReference<List<Acesso>>() {
        });

        assertEquals(1, retornoApiList.size());


        acessoRepository.deleteById(acesso.getId());
    }

    @Test
    public void testCadastraAcesso() {
        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_ALUNO");

        assertNull(acesso.getId());

        acesso = acessoController.salvarAcesso(acesso).getBody();

        assertTrue(acesso.getId() > 0);
        assertEquals("ROLE_ALUNO", acesso.getDescricao());

        // Teste de carregamento
        Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();

        assertEquals(acesso.getId(), acesso2.getId());

        // Teste de delete
        acessoRepository.deleteById(acesso2.getId());
        acessoRepository.flush(); /*roda esse aql de delete no banco de dados*/

        Acesso acesso3 = acessoRepository.findById(acesso.getId()).orElse(null);
        assertNull(acesso3);

        // Teste de query
        acesso = new Acesso();
        acesso.setDescricao("ROLE_ALUNO");
        acesso = acessoController.salvarAcesso(acesso).getBody();

        List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());

        assertEquals(1, acessos.size());

        acessoRepository.deleteById(acesso.getId());
    }
}
