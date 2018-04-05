package tesei7.ms.reobjects.agent;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("agents")
public interface AgentsClient {
    @RequestMapping(method = RequestMethod.GET,
            value = "/api/v1/agents/search/findByZipCode?zipCode={zipCode}", consumes = "application/json")
    Resources<Agent> findAgentsByZipcode(@PathVariable("zipCode") String zipCode);
}
