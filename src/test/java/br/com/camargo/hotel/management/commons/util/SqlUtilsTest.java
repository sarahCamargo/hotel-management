package br.com.camargo.hotel.management.commons.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqlUtilsTest {

    @Test
    void isLastPage_WhenLastPage_ShouldReturnTrue() {
        long pageNumber = 2L;
        long totalElements = 30L;
        long pageSize = 10L;

        assertTrue(SqlUtils.isLastPage(pageNumber, totalElements, pageSize));
    }

    @Test
    void isLastPage_WhenNotLastPage_ShouldReturnFalse() {
        long pageNumber = 0L;
        long totalElements = 30L;
        long pageSize = 10L;

        assertFalse(SqlUtils.isLastPage(pageNumber, totalElements, pageSize));
    }

    @Test
    void isFirstPage_WhenFirstPage_ShouldReturnTrue() {
        assertTrue(SqlUtils.isFirstPage(0L));
    }

    @Test
    void isFirstPage_WhenNotFirstPage_ShouldReturnFalse() {
        assertFalse(SqlUtils.isFirstPage(1L));
        assertFalse(SqlUtils.isFirstPage(5L));
    }

    @Test
    void totalPages_WithElements_ShouldCalculateCorrectly() {
        assertEquals(3, SqlUtils.totalPages(30L, 10L));
        assertEquals(1, SqlUtils.totalPages(5L, 10L));
        assertEquals(3, SqlUtils.totalPages(25L, 10L));
    }

    @Test
    void totalPages_ZeroElements_ShouldReturnZero() {
        assertEquals(0, SqlUtils.totalPages(0L, 10L));
    }
}
