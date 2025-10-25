package br.com.camargo.hotel.management.reserva.domain.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {

    @NotNull(message = "O ID do hóspede é obrigatório")
    private Long hospedeId;

    @NotNull(message = "A data de entrada prevista é obrigatória")
    @Future(message = "A data de entrada prevista deve ser futura")
    private LocalDate dataEntradaPrevista;

    @NotNull(message = "A data de saída prevista é obrigatória")
    @Future(message = "A data de saída prevista deve ser futura")
    private LocalDate dataSaidaPrevista;

    @Builder.Default
    @NotNull(message = "O campo adicionalGaragem não pode ser nulo")
    private Boolean adicionalGaragem = false;
}
