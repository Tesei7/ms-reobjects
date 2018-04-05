package tesei7.ms.reobjects.objects.agents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import tesei7.ms.reobjects.objects.base.ReObject;

import java.util.Arrays;

@Component
public class ReObjectsAssembler extends ResourceAssemblerSupport<ReObject, ReObjectResource> {
    @Autowired
    private RepositoryEntityLinks repositoryEntityLinks;

    public ReObjectsAssembler() {
        super(ReObjectWithAgentsController.class, ReObjectResource.class);
    }

    @Override
    public ReObjectResource toResource(ReObject reObjectWithAgents) {
        Link reObjectWithAgentsLink = repositoryEntityLinks.linkToSingleResource(ReObject.class, reObjectWithAgents.getId());
        Link selfLink = new Link(reObjectWithAgentsLink.getHref(), Link.REL_SELF);
        return new ReObjectResource(reObjectWithAgents, Arrays.asList(selfLink, reObjectWithAgentsLink));
    }
}
