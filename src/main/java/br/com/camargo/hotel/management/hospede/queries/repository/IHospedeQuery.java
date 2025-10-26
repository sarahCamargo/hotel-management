package br.com.camargo.hotel.management.hospede.queries.repository;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.hospede.domain.viewobjects.HospedeVO;
import br.com.camargo.hotel.management.hospede.queries.filter.HospedeFiltros;

public interface IHospedeQuery {
    Page<HospedeVO> findAll(HospedeFiltros filtros, Paginator paginator);

    HospedeVO findById(Long id);
}
