package tesei7.ms.reobjects.objects;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "RE_OBJECTS")
@Data
public class ReObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;
    @Enumerated(EnumType.STRING)
    private ReObjectType type;
    @Column
    private Long area;
    @Column
    private BigDecimal price;
}
