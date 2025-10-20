package accrox.aros.api.infrastructure.spring.jpa.entity.converters;

import accrox.aros.api.domain.model.enums.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {
    
    public String convertToNAMEColumn(OrderStatus attribute) {
        return attribute.name().toLowerCase();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String dbData) {
        return OrderStatus.valueOf(dbData.toUpperCase());
    }

    @Override
    public String convertToDatabaseColumn(OrderStatus attribute) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToDatabaseColumn'");
    }
}
