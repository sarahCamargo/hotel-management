package br.com.camargo.hotel.management.estadia.domain.entities;

import br.com.camargo.hotel.management.commons.exceptions.BusinessException;
import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import br.com.camargo.hotel.management.reserva.domain.enums.StatusReserva;
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

    @OneToOne
    @JoinColumn(name = "reserva_id", nullable = false, referencedColumnName = "id")
    private Reserva reserva;

    @Column(name = "data_hora_entrada", nullable = false)
    private LocalDateTime dataHoraEntrada;

    @Column(name = "data_hora_saida")
    private LocalDateTime dataHoraSaida;

    @Column(name = "valor_total_final")
    private BigDecimal valorTotalFinal;

    public void checkout() {
        if (this.getDataHoraSaida() != null) {
            throw new BusinessException("Check-out já realizado.");
        }

        this.setDataHoraSaida(LocalDateTime.now());
        this.getReserva().setStatus(StatusReserva.FINALIZADA);
    }
}
