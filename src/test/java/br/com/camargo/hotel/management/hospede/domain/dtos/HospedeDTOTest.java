package br.com.camargo.hotel.management.hospede.domain.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HospedeDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void hospedeDTO_ValidData_ShouldPassValidation() {
        HospedeDTO dto = HospedeDTO.builder()
                .nome("João Silva")
                .cpf("12345678900")
                .telefone("11999999999")
                .build();

        Set<ConstraintViolation<HospedeDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void hospedeDTO_BlankNome_ShouldFailValidation() {
        HospedeDTO dto = HospedeDTO.builder()
                .nome("")
                .cpf("12345678900")
                .telefone("11999999999")
                .build();

        Set<ConstraintViolation<HospedeDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals(3, violations.size());
    }

    @Test
    void hospedeDTO_NomeTooShort_ShouldFailValidation() {
        HospedeDTO dto = HospedeDTO.builder()
                .nome("Jo")
                .cpf("12345678900")
                .telefone("11999999999")
                .build();

        Set<ConstraintViolation<HospedeDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("O nome deve ter entre 3 e 100 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void hospedeDTO_NomeWithInvalidCharacters_ShouldFailValidation() {
        HospedeDTO dto = HospedeDTO.builder()
                .nome("João123")
                .cpf("12345678900")
                .telefone("11999999999")
                .build();

        Set<ConstraintViolation<HospedeDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("O nome deve conter apenas letras, espaços e apóstrofos", violations.iterator().next().getMessage());
    }

    @Test
    void hospedeDTO_InvalidCpfLength_ShouldFailValidation() {
        HospedeDTO dto = HospedeDTO.builder()
                .nome("João Silva")
                .cpf("1234567890")
                .telefone("11999999999")
                .build();

        Set<ConstraintViolation<HospedeDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("O CPF deve conter exatamente 11 dígitos numéricos", violations.iterator().next().getMessage());
    }

    @Test
    void hospedeDTO_CpfWithNonDigits_ShouldFailValidation() {
        HospedeDTO dto = HospedeDTO.builder()
                .nome("João Silva")
                .cpf("123.456.789-00")
                .telefone("11999999999")
                .build();

        Set<ConstraintViolation<HospedeDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void hospedeDTO_InvalidTelefoneLength_ShouldFailValidation() {
        HospedeDTO dto = HospedeDTO.builder()
                .nome("João Silva")
                .cpf("12345678900")
                .telefone("119999999")
                .build();

        Set<ConstraintViolation<HospedeDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("O telefone deve conter 10 ou 11 dígitos numéricos no formato DDD999999999.",
                violations.iterator().next().getMessage());
    }

    @Test
    void hospedeDTO_AllFieldsNull_ShouldFailValidation() {
        HospedeDTO dto = HospedeDTO.builder().build();

        Set<ConstraintViolation<HospedeDTO>> violations = validator.validate(dto);

        assertEquals(3, violations.size());
    }

    @Test
    void hospedeDTO_BuilderAndGetters_ShouldWorkCorrectly() {
        HospedeDTO dto = HospedeDTO.builder()
                .nome("Maria Santos")
                .cpf("98765432100")
                .telefone("11888888888")
                .build();

        assertEquals("Maria Santos", dto.getNome());
        assertEquals("98765432100", dto.getCpf());
        assertEquals("11888888888", dto.getTelefone());
    }
}
