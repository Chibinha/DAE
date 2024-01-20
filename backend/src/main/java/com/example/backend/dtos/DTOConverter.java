package com.example.backend.dtos;

import com.example.backend.entities.*;
import com.example.backend.entities.ProductPackage;
import com.example.backend.entities.TransportPackage;
import com.example.backend.entities.InventoryItem;
import com.example.backend.entities.Product;
import com.example.backend.entities.Observation;
import com.example.backend.entities.Sensor;
import com.example.backend.entities.Customer;
import com.example.backend.entities.Manufacturer;
import com.example.backend.entities.WarehouseOperator;

import java.util.List;
import java.util.stream.Collectors;

public class DTOConverter {
    //#region Users
    // Manufacturer
    public ManufacturerDTO makerToDTO(Manufacturer manufacturer) {
        return new ManufacturerDTO(
            manufacturer.getUsername(),
            manufacturer.getName(),
            manufacturer.getEmail()
        );
    }
    public List<ManufacturerDTO> makerToDTOList(List<Manufacturer> manufacturers) {
        return manufacturers.stream().map(this::makerToDTO).collect(Collectors.toList());
    }

    // Customer
    public CustomerDTO clientToDTO(Customer customer) {
        return new CustomerDTO(
            customer.getUsername(),
            customer.getPassword(),
            customer.getName(),
            customer.getEmail()
        );
    }
    public List<CustomerDTO> clientToDTOList(List<Customer> customers) {
        return customers.stream().map(this::clientToDTO).collect(Collectors.toList());
    }

    // Line Operator
    public WarehouseOperatorDTO lineOperatorToDTO(WarehouseOperator warehouseOperator) {
        return new WarehouseOperatorDTO(
            warehouseOperator.getUsername(),
            warehouseOperator.getPassword(),
            warehouseOperator.getName(),
            warehouseOperator.getEmail()
        );
    }
    public List<WarehouseOperatorDTO> lineOperatorToDTOList(List<WarehouseOperator> warehouseOperators) {
        return warehouseOperators.stream().map(this::lineOperatorToDTO).collect(Collectors.toList());
    }
    //#endregion

    //#region Products/PhysicalProducts
    // Product
    public ProductDTO productToDTO(Product product) {
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getDescription(),
            product.getWeight(),
            product.getIngredients(),
            product.getInStock(),
            product.getMaker().getUsername()
        );
    }
    public List<ProductDTO> productToDTOList(List<Product> products) {
        return products.stream().map(this::productToDTO).collect(Collectors.toList());
    }

    // InventoryItem
    public InventoryItemDTO inventoryItemToDTO(InventoryItem inventoryItem) {
        return new InventoryItemDTO(
            inventoryItem.getId(),
            inventoryItem.getProduct().getId(),
            inventoryItem.getProduct().getName(),
            inventoryItem.getProduct().getMaker().getUsername(),
            inventoryItem.getStockTimestamp(),
            inventoryItem.getProductPackages().stream().map(this::productPackageToDTO).collect(Collectors.toList())
        );
    }
    public List<InventoryItemDTO> inventoryItemToDTOList(List<InventoryItem> inventoryItems) {
        return inventoryItems.stream().map(this::inventoryItemToDTO).collect(Collectors.toList());
    }
    //#endregion

    //#region Sensors
    // Sensor
    public SensorDTO sensorToDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getName(),
                sensor.getType(),
                sensor.getUnit(),
                sensor.getCurrentPackage());
    }
    public List<SensorDTO> sensorToDTOList(List<Sensor> sensors) {
        return sensors.stream().map(this::sensorToDTO).collect(Collectors.toList());
    }

    // Observation
    public ObservationDTO observationToDTO(Observation observation) {
        return new ObservationDTO(
            observation.getId(),
            observation.getSensor().getType(),
            observation.getValue(),
            observation.getSensor().getUnit(),
            observation.getTimestamp(),
            observation.getSensor().getName(),
            observation.getSensor().getCurrentPackage());
    }
    public List<ObservationDTO> observationToDTOList(List<Observation> observations) {
        return observations.stream().map(this::observationToDTO).collect(Collectors.toList());
    }
    //#endregion

    //#region Packages
    // Transportation Package
    public TransportPackageDTO transportationPackageToDTO(TransportPackage transportPackage) {
        return new TransportPackageDTO(
                transportPackage.getId(),
                transportPackage.getType(),
                transportPackage.getMaterial(),
                transportPackage.getCurrentOrder()
        );
    }
    public List<TransportPackageDTO> transportationPackageToDTOList(List<TransportPackage> transportPackages) {
        return transportPackages.stream().map(this::transportationPackageToDTO).collect(Collectors.toList());
    }

    // Product Package
    public ProductPackageDTO productPackageToDTO(ProductPackage productPackage) {
        return new ProductPackageDTO(
            productPackage.getId(),
            productPackage.getType(),
            productPackage.getMaterial()
        );
    }
    public List<ProductPackageDTO> productPackageToDTOList(List<ProductPackage> productPackages) {
        return productPackages.stream().map(this::productPackageToDTO).collect(Collectors.toList());
    }
    //#endregion

    //#region Orders
    // Order
    public OrderDTO orderToDTO(Order order) {
        return new OrderDTO(
            order.getId(),
            order.getStatus(),
            order.getTotalPrice(),
            order.getLineOperator().getUsername(),
            order.getClient().getUsername(),
            order.getOrderTimestamp(),
            inventoryItemToDTOList(order.getInventoryItems()),
            order.getCurrentPackage(),
            order.getCurrentPackage());
    }
    public List<OrderDTO> orderToDTOList(List<Order> orders) {
        return orders.stream().map(this::orderToDTO).collect(Collectors.toList());
    }
    //#endregion

    //#region Alerts
    // Alert
    public AlertDTO alertToDTO(Alert alert) {
        return new AlertDTO(
            alert.getUser().getUsername(),
            alert.getDescription(),
            alert.getTimestamp()
        );
    }
    public List<AlertDTO> alertToDTOList(List<Alert> alerts) {
        return alerts.stream().map(this::alertToDTO).collect(Collectors.toList());
    }
    //#endregion

}
