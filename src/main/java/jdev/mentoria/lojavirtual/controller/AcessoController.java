package jdev.mentoria.lojavirtual.controller;

import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RestController
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @ResponseBody /*Dar retorno da api*/
    @PostMapping( value = "**/salvarAcesso")
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso){

        Acesso savedAcesso = acessoService.save(acesso);
        return new ResponseEntity<Acesso>(savedAcesso, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping( value = "**/deleteAcesso")
    public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso){

        acessoRepository.deleteById(acesso.getId());
        return new ResponseEntity("Acesso removido - ID: " + acesso.getId(), HttpStatus.OK);
    }

    @ResponseBody /*Dar retorno da api*/
    @DeleteMapping(value = "**/deleteAcessoPorId/{id}")
    public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id) {

        acessoRepository.deleteById(id);
        return new ResponseEntity("Acesso removido - ID: " + id, HttpStatus.OK);
    }

    @GetMapping(value = "/obterAcesso/{id}")
    public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) {
        Optional<Acesso> acessoOptional = acessoRepository.findById(id);

        if (acessoOptional.isPresent()) {
            return ResponseEntity.ok(acessoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
