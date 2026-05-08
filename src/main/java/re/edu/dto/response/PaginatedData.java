package re.edu.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PaginatedData<T> {

    private List<T> items;
    private Pagination pagination;

    @Getter
    @Builder
    public static class Pagination {
        private int currentPage;
        private int pageSize;
        private int totalPages;
        private long totalItems;
    }
}
