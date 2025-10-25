package br.com.camargo.hotel.management.hospede.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HospedeFiltros {
    private String nome;
    private String documento;
    private String telefone;

    public Optional<String> getNome() {
        return Optional.ofNullable(nome);
    }

    public Optional<String> getDocumento() {
        return Optional.ofNullable(documento);
    }

    public Optional<String> getTelefone() {
        return Optional.ofNullable(telefone);
    }
}
