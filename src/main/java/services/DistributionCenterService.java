package services;

import entities.DistributionCenter;
import entities.Item;
import entities.ItemOrder;
import entities.enums.OrderStatus;
import repositories.DistributionCenterRepository;

import java.util.Optional;
import java.util.List;

public class DistributionCenterService {
    DistributionCenterRepository distributionCenterRepository;
    ItemOrderService itemOrderService;

    public DistributionCenterService() {
        this.distributionCenterRepository = new DistributionCenterRepository();
        this.itemOrderService = new ItemOrderService();
    }

    public Optional<DistributionCenter> getDistributionCenter(Long distributionCenterId) {
        return distributionCenterRepository.findById(distributionCenterId);
    }

    public List<DistributionCenter> getAllDistributionCenters() {
        return distributionCenterRepository.findAll();
    }

    public void updateItemOrderStatus(ItemOrder order, OrderStatus newStatus, String refusedMotive) {
        order.setStatus(newStatus);
        order.setRefusedMotive(refusedMotive);
        itemOrderService.update(order);
    }

    public void addItemOrder(DistributionCenter center, ItemOrder order) {
        center.addItemOrder(order);
        distributionCenterRepository.update(center);
    }

    public void transferItem(DistributionCenter from, DistributionCenter to, Item item) {
        from.getInventory().removeItem(item);
        distributionCenterRepository.update(from);
        to.getInventory().addItem(item);
        distributionCenterRepository.update(to);
    }
}
