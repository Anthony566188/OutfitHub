package br.com.fiap.OutfitHub.models;

import br.com.fiap.OutfitHub.models.enums.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DB_OUTFITHUB_SHIRTS")
public class Shirt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(nullable = false, unique = true)
    private String imagePath;
    @Column(name = "SHIRT_NAME", nullable = false, unique = true)
    private String name;
    @Column(name = "SHIRT_SIZE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Size size;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private BigDecimal price;

}
