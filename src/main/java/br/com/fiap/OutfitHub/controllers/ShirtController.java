package br.com.fiap.OutfitHub.controllers;

import br.com.fiap.OutfitHub.models.Shirt;
import br.com.fiap.OutfitHub.services.ShirtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("shirts")
public class ShirtController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ShirtService service;

    public ShirtController(ShirtService service) {
        this.service = service;
    }

    @GetMapping
    public List<Shirt> listAll() {
        log.info("Listando todas as camisas");
        return service.listShirts();
    }

}
