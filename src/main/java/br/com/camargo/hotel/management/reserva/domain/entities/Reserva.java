package br.com.camargo.hotel.management.reserva.domain.entities;

import br.com.camargo.hotel.management.commons.exceptions.BusinessException;
import br.com.camargo.hotel.management.estadia.domain.entities.Estadia;
import br.com.camargo.hotel.management.hospede.domain.entities.Hospede;
import br.com.camargo.hotel.management.reserva.domain.enums.StatusReserva;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@Entity
@Table(name = "reserva")
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospede_id", nullable = false, referencedColumnName = "id")
    private Hospede hospede;

    @Column(name = "data_entrada_prevista", nullable = false)
    private LocalDate dataEntradaPrevista;

    @Column(name = "data_saida_prevista", nullable = false)
    private LocalDate dataSaidaPrevista;

    @Column(name = "valor_total_previsto", nullable = false)
    private BigDecimal valorTotalPrevisto;

    @Column(name = "adicional_garagem", nullable = false)
    private Boolean adicionalGaragem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusReserva status;

    @OneToOne(mappedBy = "reserva")
    private Estadia estadia;

    public void cancelar() {
        if (this.estadia != null) {
            throw new BusinessException("Não é possível cancelar uma reserva com check-in realizado.");
        }

        if (this.status == StatusReserva.CANCELADA) {
            throw new BusinessException("Reserva já está cancelada.");
        }

        if (this.status == StatusReserva.FINALIZADA) {
            throw new BusinessException("Não é possível cancelar uma reserva finalizada.");
        }

        this.status = StatusReserva.CANCELADA;
    }
}
