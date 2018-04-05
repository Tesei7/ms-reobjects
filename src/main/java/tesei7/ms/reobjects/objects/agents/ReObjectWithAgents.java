package tesei7.ms.reobjects.objects.agents;

import javafx.print.Collation;
import lombok.Data;
import lombok.ToString;
import tesei7.ms.reobjects.agent.Agent;
import tesei7.ms.reobjects.objects.base.ReObject;
import tesei7.ms.reobjects.objects.base.ReObjectDecorator;

import java.util.Collection;
import java.util.List;

@Data
@ToString(of = {"reObject", "agents"})
public class ReObjectWithAgents extends ReObjectDecorator {
    private Collection<Agent> agents;

    public ReObjectWithAgents(ReObject reObject, Collection<Agent> agents) {
        super(reObject);
        this.agents = agents;
    }
}
