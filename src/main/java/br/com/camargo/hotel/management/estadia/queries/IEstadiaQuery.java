package br.com.camargo.hotel.management.estadia.queries;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.estadia.domain.viewobjects.EstadiaVO;

public interface IEstadiaQuery {
    Page<EstadiaVO> findAll(Paginator paginator);
}
