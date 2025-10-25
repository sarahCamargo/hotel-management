package br.com.camargo.hotel.management.hospede.queries;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.hospede.domain.viewobjects.HospedeVO;

public interface IHospedeQuery {
    Page<HospedeVO> findAll(HospedeFiltros filtros, Paginator paginator);
}
