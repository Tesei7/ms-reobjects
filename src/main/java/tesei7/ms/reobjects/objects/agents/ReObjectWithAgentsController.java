package tesei7.ms.reobjects.objects.agents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tesei7.ms.reobjects.agent.AgentsClient;
import tesei7.ms.reobjects.objects.base.ReObject;
import tesei7.ms.reobjects.objects.base.ReObjectsAssembler;
import tesei7.ms.reobjects.objects.base.ReObjectsRepository;

import java.util.Collections;
import java.util.List;

@BasePathAwareController
@RequestMapping("reobjets/search")
public class ReObjectWithAgentsController implements ResourceProcessor<RepositorySearchesResource> {
    @Autowired
    private ReObjectsRepository reObjectsRepository;
    @Autowired
    private ReObjectsAssembler reObjectsAssembler;
    @Autowired
    private PagedResourcesAssembler<ReObject> pagedResourcesAssembler;
    @Autowired
    private AgentsClient agentsClient;

    @RequestMapping(value = "withagents", method = RequestMethod.GET)
    public ResponseEntity<PagedResources> customFind(@PageableDefault Pageable pageable) {
        Page<ReObject> reObjects = reObjectsRepository.findAll(pageable);
        Page<ReObject> reObjectsWithAgents = reObjects
                .map(reObject -> new ReObjectWithAgents(reObject,
                        agentsClient.findAgentsByZipCode(reObject.getAddress().getZipCode()).getContent()));
        PagedResources reObjectResources = pagedResourcesAssembler.toResource(reObjectsWithAgents, reObjectsAssembler);

        if (reObjectsWithAgents.getContent() == null || reObjectsWithAgents.getContent().isEmpty()) {
            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(ReObject.class);
            List<EmbeddedWrapper> embedded = Collections.singletonList(wrapper);
            reObjectResources = new PagedResources<>(embedded, reObjectResources.getMetadata(), reObjectResources.getLinks());
        }
        return new ResponseEntity<>(reObjectResources, HttpStatus.OK);
    }

    @Override
    public RepositorySearchesResource process(RepositorySearchesResource repositorySearchesResource) {
        final String search = repositorySearchesResource.getId().getHref();
        final Link customLink = new Link(search + "/withagents{?page,size,sort}").withRel("withagents");
        repositorySearchesResource.add(customLink);
        return repositorySearchesResource;
    }
}
