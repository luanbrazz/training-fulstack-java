package jdev.mentoria.lojavirtual;

import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.repository.PessoaRepository;
import jdev.mentoria.lojavirtual.service.PessoaUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Calendar;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class TestePessoaUsuario {

    @Autowired
    private PessoaUserService pessoaUserService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    public void testCadPessoa() {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setCnpj(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        pessoaJuridica.setInscEstadual((String.valueOf(Calendar.getInstance().getTimeInMillis())));
        pessoaJuridica.setInscMunicipal((String.valueOf(Calendar.getInstance().getTimeInMillis())));
        pessoaJuridica.setRazaoSocial((String.valueOf(Calendar.getInstance().getTimeInMillis())));
        pessoaJuridica.setNome("Empresa Ficticia");
        pessoaJuridica.setNomeFantasia("Fantasia Empresa Ficticia");
        pessoaJuridica.setEmail("empresa@ficticia.com");
        pessoaJuridica.setTelefone("0147896555");
        pessoaJuridica.setEmpresa(pessoaJuridica);
        pessoaRepository.save(pessoaJuridica);

//        PessoaFisica pessoaFisica = new PessoaFisica();
//        pessoaFisica.setCpf(String.valueOf(Calendar.getInstance().getTimeInMillis()));
//        pessoaFisica.setNome("Luan Braz");
//        pessoaFisica.setEmail("luan@gmail.com");
//        pessoaFisica.setTelefone("01299877458");
//        pessoaFisica.setEmpresa(pessoaFisica);
    }
}
