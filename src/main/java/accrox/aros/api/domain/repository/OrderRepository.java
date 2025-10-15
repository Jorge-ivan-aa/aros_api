package accrox.aros.api.domain.repository;

public interface OrderRepository {
    public void MarkOrderAsCompleted(Long orderId);
}
