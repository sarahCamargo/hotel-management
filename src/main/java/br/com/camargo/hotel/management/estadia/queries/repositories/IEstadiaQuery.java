package br.com.camargo.hotel.management.estadia.queries.repositories;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.estadia.domain.viewobjects.EstadiaVO;
import br.com.camargo.hotel.management.estadia.queries.filters.EstadiaFiltros;

public interface IEstadiaQuery {
    Page<EstadiaVO> findAll(EstadiaFiltros filtros, Paginator paginator);
}
