package jdev.mentoria.lojavirtual;

import jdev.mentoria.lojavirtual.controller.AcessoController;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.service.AcessoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
//@SpringBootTest(classes = LojaVirtualMentoriaApplicationTests.class)
class LojaVirtualMentoriaApplicationTests {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoController acessoController;

//    @Autowired
//    private AcessoRepository acessoRepository;

    @Test
    void cadastraAcesso() {
        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_ADMIN");
//        acessoService.save(acesso);
        acessoController.salvarAcesso(acesso);
    }
}
