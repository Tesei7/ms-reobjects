package tesei7.ms.reobjects.objects.base;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public abstract class ReObjectDecorator extends ReObject {
    protected final ReObject reObject;

    @Override
    public long getId() {
        return reObject.getId();
    }

    @Override
    public Address getAddress() {
        return reObject.getAddress();
    }

    @Override
    public ReObjectType getType() {
        return reObject.getType();
    }

    @Override
    public Long getArea() {
        return reObject.getArea();
    }

    @Override
    public BigDecimal getPrice() {
        return reObject.getPrice();
    }
}
