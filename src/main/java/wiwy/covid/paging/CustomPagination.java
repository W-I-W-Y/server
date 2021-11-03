package wiwy.covid.paging;

import lombok.Getter;

@Getter
public class CustomPagination {

    private long totalElements;
    private int totalPages;

    public CustomPagination() {
    }

    public CustomPagination(long totalElements, int totalPages) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
