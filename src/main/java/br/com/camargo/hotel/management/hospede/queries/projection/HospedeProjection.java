package br.com.camargo.hotel.management.hospede.queries.projection;

import br.com.camargo.hotel.management.commons.util.CurrencyFormatUtils;
import br.com.camargo.hotel.management.hospede.domain.viewobjects.HospedeVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospedeProjection {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private BigDecimal valorTotal;
    private BigDecimal valorUltimaHospedagem;

    public HospedeVO toVO() {
        return new HospedeVO(
                this.id,
                this.nome,
                this.cpf,
                this.telefone,
                CurrencyFormatUtils.formatar(this.valorTotal),
                CurrencyFormatUtils.formatar(this.valorUltimaHospedagem)
        );
    }
}
