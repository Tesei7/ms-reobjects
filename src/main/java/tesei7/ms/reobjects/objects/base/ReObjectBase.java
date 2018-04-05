package tesei7.ms.reobjects.objects.base;

import java.math.BigDecimal;

public interface ReObjectBase {
    long getId();
    Address getAddress();
    ReObjectType getType();
    Long getArea();
    BigDecimal getPrice();
}
