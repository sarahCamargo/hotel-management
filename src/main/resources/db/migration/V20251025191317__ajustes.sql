CREATE TABLE IF NOT EXISTS hospede (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    documento VARCHAR(20) NOT NULL,
    telefone VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS reserva (
    id BIGSERIAL PRIMARY KEY,
    hospede_id BIGINT NOT NULL,
    data_entrada_prevista DATE NOT NULL,
    data_saida_prevista DATE NOT NULL,
    valor_total_previsto NUMERIC(10,2) NOT NULL,
    adicional_garagem BOOLEAN NOT NULL DEFAULT FALSE,
    status VARCHAR(20) NOT NULL DEFAULT 'RESERVADA',

    CONSTRAINT fk_reserva_hospede
        FOREIGN KEY (hospede_id)
        REFERENCES hospede (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS estadia (
    id BIGSERIAL PRIMARY KEY,
    reserva_id BIGINT NOT NULL,
    data_hora_entrada TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_hora_saida TIMESTAMP WITHOUT TIME ZONE,
    valor_total_final NUMERIC(10, 2),

    CONSTRAINT fk_estadia_reserva
        FOREIGN KEY (reserva_id)
        REFERENCES reserva (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);
