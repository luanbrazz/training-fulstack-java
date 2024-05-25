package jdev.mentoria.lojavirtual.controller;

import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @ResponseBody /*Dar retorno da api*/
    @PostMapping(value = "**/salvarAcesso")
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionMentoriaJava {

        if (acesso.getId() == null) {
            List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
            if (!acessos.isEmpty()) {
                throw new ExceptionMentoriaJava("Já existe um acesso com a descrição: " + acesso.getDescricao());
            }
        }

        Acesso savedAcesso = acessoService.save(acesso);
        return new ResponseEntity<Acesso>(savedAcesso, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/deleteAcesso")
    public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso) {

        acessoRepository.deleteById(acesso.getId());
        return new ResponseEntity("Acesso removido - ID: " + acesso.getId(), HttpStatus.OK);
    }

    //    @Secured({"ROLE_GERENTE", "ROLE_ADMIN"})
    @ResponseBody /*Dar retorno da api*/
    @DeleteMapping(value = "**/deleteAcessoPorId/{id}")
    public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id) {

        acessoRepository.deleteById(id);
        return new ResponseEntity("Acesso removido - ID: " + id, HttpStatus.OK);
    }

    @GetMapping(value = "/obterAcesso/{id}")
    public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) throws ExceptionMentoriaJava {
        Optional<Acesso> acessoOptional = acessoRepository.findById(id);

        if (!acessoOptional.isPresent()) {
            throw new ExceptionMentoriaJava("Não encontrou o acesso com codigo " + id);
        }

        return ResponseEntity.ok(acessoOptional.get());
    }

    @GetMapping(value = "/buscarPorDesc/{desc}")
    public ResponseEntity<List<Acesso>> buscarPorDesc(@PathVariable("desc") String desc) {
        List<Acesso> acessos = acessoRepository.buscarAcessoDesc(desc.toUpperCase());

        if (!acessos.isEmpty()) {
            return ResponseEntity.ok(acessos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
