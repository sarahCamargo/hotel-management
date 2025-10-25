package br.com.camargo.hotel.management.hospede.structures.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@Table(name = "hospede")
@AllArgsConstructor
@NoArgsConstructor
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
