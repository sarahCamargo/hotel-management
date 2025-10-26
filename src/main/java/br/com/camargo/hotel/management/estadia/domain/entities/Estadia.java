package br.com.camargo.hotel.management.estadia.domain.entities;

import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@Entity
@Table(name = "estadia")
@AllArgsConstructor
@NoArgsConstructor
public class Estadia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false, referencedColumnName = "id")
    private Reserva reserva;

    @Column(name = "data_hora_entrada", nullable = false)
    private LocalDateTime dataHoraEntrada;

    @Column(name = "data_hora_saida")
    private LocalDateTime dataHoraSaida;

    @Column(name = "valor_total_final")
    private BigDecimal valorTotalFinal;
}
