package tesei7.ms.reobjects.objects.base;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import tesei7.ms.reobjects.objects.base.ReObject;

public class ReObjectResource extends Resource<ReObject> {
    public ReObjectResource(ReObject content, Iterable<Link> links) {
        super(content, links);
    }
}
