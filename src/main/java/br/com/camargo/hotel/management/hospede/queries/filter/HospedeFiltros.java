package br.com.camargo.hotel.management.hospede.queries.filter;

import lombok.*;

import java.util.Optional;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HospedeFiltros {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getNome() {
        return Optional.ofNullable(nome);
    }

    public Optional<String> getCpf() {
        return Optional.ofNullable(cpf);
    }

    public Optional<String> getTelefone() {
        return Optional.ofNullable(telefone);
    }
}
