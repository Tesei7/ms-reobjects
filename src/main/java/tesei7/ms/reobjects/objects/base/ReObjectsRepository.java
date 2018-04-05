package tesei7.ms.reobjects.objects.base;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReObjectsRepository extends PagingAndSortingRepository<ReObject, Long> {
}
