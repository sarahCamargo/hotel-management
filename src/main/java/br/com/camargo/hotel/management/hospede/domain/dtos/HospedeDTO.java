package br.com.camargo.hotel.management.hospede.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospedeDTO {

    @NotBlank(message = "O nome do hóspede é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O documento é obrigatório")
    private String documento;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(
            regexp = "\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}",
            message = "Telefone inválido. Formato esperado: (99) 99999-9999 ou 99999-9999"
    )
    private String telefone;
}
