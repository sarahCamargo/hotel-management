package br.com.camargo.hotel.management.hospede.domain.viewobjects;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospedeVO {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String valorTotal;
    private String valorUltimaHospedagem;
}
