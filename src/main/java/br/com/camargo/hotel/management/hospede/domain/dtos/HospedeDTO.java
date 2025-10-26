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
    @Pattern(
            regexp = "^[a-zA-ZÀ-ÿ\\s']+$",
            message = "O nome deve conter apenas letras, espaços e apóstrofos"
    )
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(
            regexp = "^\\d{11}$",
            message = "O CPF deve conter exatamente 11 dígitos numéricos"
    )
    private String cpf;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(
            regexp = "^\\d{10,11}$",
            message = "O telefone deve conter 10 ou 11 dígitos numéricos no formato DDD999999999."
    )
    private String telefone;
}
