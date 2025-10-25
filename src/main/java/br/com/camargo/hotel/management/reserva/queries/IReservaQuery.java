package br.com.camargo.hotel.management.reserva.queries;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.reserva.domain.viewobjects.ReservaVO;

public interface IReservaQuery {
    Page<ReservaVO> findAll(Paginator paginator);
}
