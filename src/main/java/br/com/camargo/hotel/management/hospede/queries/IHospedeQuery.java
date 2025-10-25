package br.com.camargo.hotel.management.hospede.queries;

import br.com.camargo.hotel.management.commons.Page;
import br.com.camargo.hotel.management.commons.Paginator;
import br.com.camargo.hotel.management.hospede.structures.viewobjects.HospedeVO;

public interface IHospedeQuery {
    Page<HospedeVO> findAll(Paginator paginator);
}
