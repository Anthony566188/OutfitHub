package br.com.fiap.OutfitHub.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DB_SHIRTS")
public class Shirt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    private String imagePath;
    private String shirtName;
    private String shirtSize;
    private String status;
    private BigDecimal price;

}
