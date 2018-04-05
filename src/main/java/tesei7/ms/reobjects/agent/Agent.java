package tesei7.ms.reobjects.agent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Agent {
    private String fullName;
    private String email;
    private String phone;
}
