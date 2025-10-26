package br.com.camargo.hotel.management.hospede.domain.entities;

import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "telefone")
    private String telefone;

    @OneToMany(mappedBy = "hospede", fetch = FetchType.LAZY)
    private List<Reserva> reservas;
}
