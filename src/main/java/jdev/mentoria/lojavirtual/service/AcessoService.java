package jdev.mentoria.lojavirtual.service;

import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcessoService {

    private static final Logger log = LoggerFactory.getLogger(AcessoService.class);

    @Autowired
    private AcessoRepository acessoRepository;

    public Acesso save(Acesso acesso){
        try {
            Acesso savedAcesso = acessoRepository.save(acesso);
            log.error("Acesso salvo: Descrição: {}, ID: {}", savedAcesso.getDescricao(), savedAcesso.getId());
            return savedAcesso;
        } catch (Exception e) {
            log.error("Erro ao salvar acesso: {}", e.getMessage());
            throw new RuntimeException("Erro ao salvar acesso", e);
        }
    }
}
