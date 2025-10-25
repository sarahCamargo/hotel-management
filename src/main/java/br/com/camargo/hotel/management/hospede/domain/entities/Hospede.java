package br.com.camargo.hotel.management.hospede.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@Table(name = "hospede")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Hospede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "documento", nullable = false)
    private String documento;

    @Column(name = "telefone")
    private String telefone;
}
