package jdev.mentoria.lojavirtual;

import jdev.mentoria.lojavirtual.controller.AcessoController;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.service.AcessoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class LojaVirtualMentoriaApplicationTests {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private AcessoController acessoController;

    @Test
    public void testCadastraAcesso() {
        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_ALUNO");

        assertEquals(true,acesso.getId() == null);


        acesso = acessoController.salvarAcesso(acesso).getBody();

        assertTrue(acesso.getId() > 0);
        assertEquals("ROLE_ALUNO", acesso.getDescricao());

        /*Teste de carregamento*/
        Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();

        assertEquals(acesso.getId(), acesso2.getId());

        /*teste de delete*/
        acessoRepository.deleteById(acesso2.getId());
        acessoRepository.flush(); /*roda esse aql de delete no banco de dados*/

        Acesso acesso3 = acessoRepository.findById(acesso.getId()).orElse(null);
        assertNull(acesso3);

        /*teste de query*/
        acesso = new Acesso();
        acesso.setDescricao("ROLE_ALUNO");
        acesso = acessoController.salvarAcesso(acesso).getBody();

        List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());

        assertEquals(1, acessos.size());

        acessoRepository.deleteById(acesso.getId());
    }
}
