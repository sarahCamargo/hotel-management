package br.com.camargo.hotel.management.commons.util;

public final class SqlUtils {
    public static boolean isLastPage(long pageNumber, long totalNumberOfElements, long pageSize) {
        int totalPageNumber = totalPages(totalNumberOfElements, pageSize);
        return pageNumber + 1L == (long) totalPageNumber;
    }

    public static int totalPages(long totalNumberOfElements, long pageSize) {
        return totalNumberOfElements == 0L ? 0 : (int) Math.ceil((double) totalNumberOfElements / (double) pageSize);
    }

    public static boolean isFirstPage(long pageNumber) {
        return pageNumber == 0L;
    }
}
