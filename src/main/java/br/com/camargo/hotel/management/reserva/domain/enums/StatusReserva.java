package br.com.camargo.hotel.management.reserva.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusReserva {
    RESERVADA("Reservada"),
    CANCELADA("Cancelada"),
    FINALIZADA("Finalizada");

    private final String descricao;
}
