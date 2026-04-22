package br.com.fiap.OutfitHub.controllers;

import br.com.fiap.OutfitHub.dto.ShirtRequest;
import br.com.fiap.OutfitHub.models.Shirt;
import br.com.fiap.OutfitHub.services.ShirtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("shirts")
public class ShirtController {

    private final ShirtService service;

    public ShirtController(ShirtService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Shirt>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Shirt create(@RequestPart("shirt") @Valid ShirtRequest shirtRequest,
                        @RequestPart("image") MultipartFile image) throws IOException {
        return service.saveShirt(shirtRequest.toEntity(), image);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteShirt(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public Shirt update(@RequestPart("shirt") Shirt shirt,
                        @PathVariable Long id,
                        @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return service.updateShirt(shirt, id, image);
    }
}
