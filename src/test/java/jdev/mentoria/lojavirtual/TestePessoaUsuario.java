package jdev.mentoria.lojavirtual;

import jdev.mentoria.lojavirtual.controller.PessoaController;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Calendar;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class TestePessoaUsuario {

    @Autowired
    private PessoaController pessoaController;

    @Test
    public void testCadPessoa() throws ExceptionMentoriaJava {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
        pessoaJuridica.setNome("Empresa Luan");
        pessoaJuridica.setEmail("empresa@ficticia.com");
        pessoaJuridica.setTelefone("0147896555");
        pessoaJuridica.setInscEstadual((String.valueOf(Calendar.getInstance().getTimeInMillis())));
        pessoaJuridica.setInscMunicipal((String.valueOf(Calendar.getInstance().getTimeInMillis())));
        pessoaJuridica.setNomeFantasia("Fantasia Empresa Ficticia");
        pessoaJuridica.setRazaoSocial((String.valueOf(Calendar.getInstance().getTimeInMillis())));
        pessoaController.salvarPj(pessoaJuridica);

    }
}
