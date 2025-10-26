package br.com.camargo.hotel.management.estadia.domain.dtos;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstadiaDTO {

    private Long reservaId;
    private String nome;
    private String cpf;
    private String telefone;
}
