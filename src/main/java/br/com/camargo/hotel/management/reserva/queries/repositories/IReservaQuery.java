package br.com.camargo.hotel.management.reserva.queries.repositories;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.reserva.domain.viewobjects.ReservaVO;
import br.com.camargo.hotel.management.reserva.queries.filter.ReservaFiltros;

public interface IReservaQuery {
    Page<ReservaVO> findAll(ReservaFiltros filtros, Paginator paginator);
}
