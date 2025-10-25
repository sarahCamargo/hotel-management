package br.com.camargo.hotel.management.hospede.structures.viewobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospedeVO {
    private Long id;
    private String nome;
    private String documento;
    private String telefone;
}
