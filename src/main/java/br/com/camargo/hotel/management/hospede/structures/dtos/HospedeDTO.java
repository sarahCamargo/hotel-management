package br.com.camargo.hotel.management.hospede.structures.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospedeDTO {
    private String nome;
    private String documento;
    private String telefone;
}
