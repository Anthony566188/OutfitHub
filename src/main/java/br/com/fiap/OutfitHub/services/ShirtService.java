package br.com.fiap.OutfitHub.services;

import br.com.fiap.OutfitHub.models.Shirt;
import br.com.fiap.OutfitHub.models.enums.Status;
import br.com.fiap.OutfitHub.repositories.ShirtRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ShirtService {

    private final ShirtRepository repository;
    private final CloudinaryService cloudinaryService;

    public ShirtService(ShirtRepository repository, CloudinaryService cloudinaryService) {
        this.repository = repository;
        this.cloudinaryService = cloudinaryService;
    }

    public List<Shirt> listAll() {
        return repository.findAll();
    }

    public Shirt saveShirt(Shirt shirt, MultipartFile image) throws IOException {
        // Faz o upload da imagem e recebe a URL do Cloudinary
        String imageUrl = cloudinaryService.upload(image);

        shirt.setImagePath(imageUrl);
        shirt.setStatus(Status.ESTOQUE);
        return repository.save(shirt);
    }

    public void deleteShirt(Long id) {

        var existing = findShirtById(id);

        // Tenta apagar a imagem do Cloudinary primeiro
        try {
            String publicId = extractPublicId(existing.getImagePath());
            if (publicId != null) {
                Map result = cloudinaryService.delete(publicId);
                System.out.println("Resposta do Cloudinary: " + result);
            }
        } catch (IOException e) {
            // Em caso de erro de rede, lança uma exceção para o Spring tratar
            throw new RuntimeException("Erro ao tentar apagar a imagem do Cloudinary", e);
        }

        repository.deleteById(id);
    }

    public Shirt updateShirt(Shirt shirt, Long id, MultipartFile image) throws IOException {

        // Busca a camisa existente no banco
        Shirt existingShirt = findShirtById(id);

        // Verifica se uma nova imagem foi enviada na requisição
        if (image != null && !image.isEmpty()) {

            // Apaga a imagem antiga da nuvem antes de colocar a nova
            try {
                String oldPublicId = extractPublicId(existingShirt.getImagePath());
                if (oldPublicId != null) {
                    cloudinaryService.delete(oldPublicId);
                }
            } catch (IOException e) {
                System.err.println("Aviso: Falha ao apagar a imagem antiga do Cloudinary.");
            }

            // Faz o upload da nova imagem para o Cloudinary
            String newImageUrl = cloudinaryService.upload(image);
            // Atualiza o caminho com a nova URL gerada
            existingShirt.setImagePath(newImageUrl);

        }

        existingShirt.setName(shirt.getName());
        existingShirt.setSize(shirt.getSize());
        existingShirt.setStatus(shirt.getStatus());
        existingShirt.setPrice(shirt.getPrice());

        // Salva a alteração
        return repository.save(existingShirt);

    }

    private Shirt findShirtById(Long id){
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Camisa de id " + id + " não encontrada"));
    }

    private String extractPublicId(String imageUrl) {
        if (imageUrl == null || !imageUrl.contains("/")) {
            return null;
        }

        // Divide a URL pelas barras ("/") e pega o último elemento
        String[] parts = imageUrl.split("/");
        String lastPart = parts[parts.length - 1]; // "imagem.png"

        // Remove a extensão (o ponto e o que vem a seguir)
        int dotIndex = lastPart.lastIndexOf('.');
        return dotIndex != -1 ? lastPart.substring(0, dotIndex) : lastPart;
    }

}
