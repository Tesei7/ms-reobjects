package tesei7.ms.reobjects.objects.base;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "zip_code")
    private String zipCode;
    @Column
    private String city;
    @Column
    private String address;
    @OneToOne(mappedBy="address")
    private ReObject reObject;
}
