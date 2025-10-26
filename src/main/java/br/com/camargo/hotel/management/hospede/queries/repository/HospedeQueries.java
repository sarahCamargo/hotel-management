package br.com.camargo.hotel.management.hospede.queries.repository;

public final class HospedeQueries {
    public static final String SELECT = """
            SELECT
                h.id,
                h.nome,
                h.cpf,
                h.telefone,
                COALESCE(SUM(r.valor_total_previsto),0) AS valor_total,
                COALESCE(MAX(e.valor_total_final),0) AS valor_ultima_hospedagem
            FROM
                hospede h
            """;

    public static final String JOIN = """
            LEFT JOIN reserva r ON h.id = r.hospede_id AND r.status = 'FINALIZADA'
            LEFT JOIN estadia e ON e.reserva_id = r.id AND e.data_hora_saida IS NOT NULL
            """;

    public static final String GROUP_BY = """
            GROUP BY
                h.id,
                h.nome,
                h.cpf,
                h.telefone
            """;

    public static final String COUNT = """
            SELECT count(h.id)
            FROM hospede h
            """;

    public static final String OFFSET_LIMIT = " OFFSET %d LIMIT %d";
}
