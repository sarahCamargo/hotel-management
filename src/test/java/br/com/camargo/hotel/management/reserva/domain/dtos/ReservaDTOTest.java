package br.com.camargo.hotel.management.reserva.domain.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReservaDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void reservaDTO_ValidData_ShouldPassValidation() {
        ReservaDTO dto = ReservaDTO.builder()
                .hospedeId(1L)
                .dataEntradaPrevista(LocalDate.now().plusDays(1))
                .dataSaidaPrevista(LocalDate.now().plusDays(3))
                .adicionalGaragem(true)
                .build();

        Set<ConstraintViolation<ReservaDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void reservaDTO_NullHospedeId_ShouldFailValidation() {
        ReservaDTO dto = ReservaDTO.builder()
                .hospedeId(null)
                .dataEntradaPrevista(LocalDate.now().plusDays(1))
                .dataSaidaPrevista(LocalDate.now().plusDays(3))
                .adicionalGaragem(true)
                .build();

        Set<ConstraintViolation<ReservaDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("O ID do hóspede é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void reservaDTO_PastDataEntrada_ShouldFailValidation() {
        ReservaDTO dto = ReservaDTO.builder()
                .hospedeId(1L)
                .dataEntradaPrevista(LocalDate.now().minusDays(1))
                .dataSaidaPrevista(LocalDate.now().plusDays(3))
                .adicionalGaragem(true)
                .build();

        Set<ConstraintViolation<ReservaDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("A data de entrada prevista deve ser futura", violations.iterator().next().getMessage());
    }

    @Test
    void reservaDTO_PastDataSaida_ShouldFailValidation() {
        ReservaDTO dto = ReservaDTO.builder()
                .hospedeId(1L)
                .dataEntradaPrevista(LocalDate.now().plusDays(1))
                .dataSaidaPrevista(LocalDate.now().minusDays(1))
                .adicionalGaragem(true)
                .build();

        Set<ConstraintViolation<ReservaDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("A data de saída prevista deve ser futura", violations.iterator().next().getMessage());
    }

    @Test
    void reservaDTO_NullAdicionalGaragem_ShouldFailValidation() {
        ReservaDTO dto = ReservaDTO.builder()
                .hospedeId(1L)
                .dataEntradaPrevista(LocalDate.now().plusDays(1))
                .dataSaidaPrevista(LocalDate.now().plusDays(3))
                .adicionalGaragem(null)
                .build();

        Set<ConstraintViolation<ReservaDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("O campo adicionalGaragem não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void reservaDTO_DefaultAdicionalGaragem_ShouldBeFalse() {
        ReservaDTO dto = ReservaDTO.builder()
                .hospedeId(1L)
                .dataEntradaPrevista(LocalDate.now().plusDays(1))
                .dataSaidaPrevista(LocalDate.now().plusDays(3))
                .build();

        assertFalse(dto.getAdicionalGaragem());
    }

    @Test
    void reservaDTO_BuilderAndGetters_ShouldWorkCorrectly() {
        ReservaDTO dto = ReservaDTO.builder()
                .hospedeId(1L)
                .dataEntradaPrevista(LocalDate.of(2025, 10, 26))
                .dataSaidaPrevista(LocalDate.of(2025, 10, 27))
                .adicionalGaragem(true)
                .build();

        assertEquals(1L, dto.getHospedeId());
        assertEquals(LocalDate.of(2025, 10, 26), dto.getDataEntradaPrevista());
        assertEquals(LocalDate.of(2025, 10, 27), dto.getDataSaidaPrevista());
        assertTrue(dto.getAdicionalGaragem());
    }
}
