package tesei7.ms.reobjects.objects.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import tesei7.ms.reobjects.objects.agents.ReObjectWithAgentsController;

import java.util.Arrays;

@Component
public class ReObjectsAssembler extends ResourceAssemblerSupport<ReObject, ReObjectResource> {
    @Autowired
    private RepositoryEntityLinks repositoryEntityLinks;

    public ReObjectsAssembler() {
        super(ReObjectWithAgentsController.class, ReObjectResource.class);
    }

    @Override
    public ReObjectResource toResource(ReObject reObject) {
        Link reObjects = repositoryEntityLinks.linkToSingleResource(ReObject.class, reObject.getId());
        Link selfLink = new Link(reObjects.getHref(), Link.REL_SELF);
        return new ReObjectResource(reObject, Arrays.asList(selfLink, reObjects));
    }
}
