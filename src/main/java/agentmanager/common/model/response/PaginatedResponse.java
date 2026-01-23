package agentmanager.common.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {

	private List<T> data;
	private int currentPage;
	private int totalPages;
	private long totalItems;
	private int pageSize;
	private boolean hasNext;
	private boolean hasPrevious;

}