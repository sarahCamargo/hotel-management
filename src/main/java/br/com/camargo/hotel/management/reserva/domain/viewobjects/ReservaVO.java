package br.com.camargo.hotel.management.reserva.domain.viewobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaVO {
    private Long id;
    private Long hospedeId;
    private String dataEntradaPrevista;
    private String dataSaidaPrevista;
    private String valorTotalPrevisto;
    private Boolean adicionalGaragem;
    private String status;
}
