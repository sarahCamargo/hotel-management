package br.com.camargo.hotel.management.estadia.domain.viewobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstadiaVO {
    private Long id;
    private Long reservaId;
    private String dataEntrada;
    private String dataSaida;
    private String valorTotalFinal;
}
