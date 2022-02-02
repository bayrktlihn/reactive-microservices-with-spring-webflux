package io.bayrktlihn.orderservice.repository;

import io.bayrktlihn.orderservice.entity.PurchaseOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

  List<PurchaseOrder> findByUserId(int userId);

}
