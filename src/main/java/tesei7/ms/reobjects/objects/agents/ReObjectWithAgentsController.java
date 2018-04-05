package tesei7.ms.reobjects.objects.agents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import tesei7.ms.reobjects.agent.Agent;
import tesei7.ms.reobjects.agent.Agents;
import tesei7.ms.reobjects.agent.AgentsClient;
import tesei7.ms.reobjects.objects.base.ReObject;
import tesei7.ms.reobjects.objects.base.ReObjectsRepository;

import java.sql.Array;
import java.util.Arrays;
import java.util.Collection;
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
    @Autowired
    private RestTemplate restTemplate;

    private Collection<Agent> getAgents(String zipCode) {
        ResponseEntity<PagedResources<Agent>> restExchange = restTemplate.exchange(
                "http://agents/api/v1/agents/search/findByZipCode?zipCode={zipCode}", HttpMethod.GET,
                null, new ParameterizedTypeReference<PagedResources<Agent>>() {}, zipCode);
        return restExchange.getBody().getContent();
    }

    @RequestMapping(value = "withagents", method = RequestMethod.GET)
    public ResponseEntity<PagedResources> customFind(@PageableDefault Pageable pageable) {
        Page<ReObject> reObjects = reObjectsRepository.findAll(pageable);
//        Page<ReObject> reObjectsWithAgents = reObjects.map(reObject -> new ReObjectWithAgents(reObject, agentsClient.findAgentsByZipcode(reObject.getAddress().getZipCode())));
        Page<ReObject> reObjectsWithAgents = reObjects
                .map(reObject -> new ReObjectWithAgents(reObject, getAgents(reObject.getAddress().getZipCode())));
        PagedResources adminPagedResources = pagedResourcesAssembler.toResource(reObjectsWithAgents, reObjectsAssembler);

        if (reObjectsWithAgents.getContent() == null || reObjectsWithAgents.getContent().isEmpty()) {
            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(ReObject.class);
            List<EmbeddedWrapper> embedded = Collections.singletonList(wrapper);
            adminPagedResources = new PagedResources(embedded, adminPagedResources.getMetadata(), adminPagedResources.getLinks());
        }
        return new ResponseEntity<>(adminPagedResources, HttpStatus.OK);
    }

    @Override
    public RepositorySearchesResource process(RepositorySearchesResource repositorySearchesResource) {
        final String search = repositorySearchesResource.getId().getHref();
        final Link customLink = new Link(search + "/withagents{?page,size,sort}").withRel("customFind");
        repositorySearchesResource.add(customLink);
        return repositorySearchesResource;
    }
}
