package br.com.camargo.hotel.management.estadia.domain.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstadiaDTO {

    @Valid
    @NotNull(message = "Reserva não pode ser nula")
    private Long reservaId;
}
