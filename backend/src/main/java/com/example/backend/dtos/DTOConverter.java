package com.example.backend.dtos;

import com.example.backend.entities.*;

import java.util.List;
import java.util.stream.Collectors;

public class DTOConverter {
    //#region Users
    // Maker
    public MakerDTO makerToDTO(Maker maker) {
        return new MakerDTO(
                maker.getUsername(),
                maker.getName(),
                maker.getEmail()
        );
    }
    public List<MakerDTO> makerToDTOList(List<Maker> makers) {
        return makers.stream().map(this::makerToDTO).collect(Collectors.toList());
    }

    // Client
    public ClientDTO clientToDTO(Client client) {
        return new ClientDTO(
                client.getUsername(),
                client.getPassword(),
                client.getName(),
                client.getEmail()
        );
    }
    public List<ClientDTO> clientToDTOList(List<Client> clients) {
        return clients.stream().map(this::clientToDTO).collect(Collectors.toList());
    }

    // Line Operator
    public LineOperatorDTO lineOperatorToDTO(LineOperator lineOperator) {
        return new LineOperatorDTO(
                lineOperator.getUsername(),
                lineOperator.getPassword(),
                lineOperator.getName(),
                lineOperator.getEmail()
        );
    }
    public List<LineOperatorDTO> lineOperatorToDTOList(List<LineOperator> lineOperators) {
        return lineOperators.stream().map(this::lineOperatorToDTO).collect(Collectors.toList());
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

    // PhysicalProduct
    public PhysicalProductDTO physicalProductToDTO(PhysicalProduct physicalProduct) {
        return new PhysicalProductDTO(
                physicalProduct.getId(),
                physicalProduct.getProduct().getId(),
                physicalProduct.getProduct().getName(),
                physicalProduct.getProduct().getMaker().getUsername(),
                physicalProduct.getStockTimestamp()
        );
    }
    public List<PhysicalProductDTO> physicalProductToDTOList(List<PhysicalProduct> physicalProducts) {
        return physicalProducts.stream().map(this::physicalProductToDTO).collect(Collectors.toList());
    }
    //#endregion

    //#region Sensors
    // Sensor
    public SensorDTO sensorToDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getName(),
                sensor.getType()
        );
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
                0,
                observation.getOrder().getId()
        );
    }
    public List<ObservationDTO> observationToDTOList(List<Observation> observations) {
        return observations.stream().map(this::observationToDTO).collect(Collectors.toList());
    }
    //#endregion

    //#region Packages
    // Transportation Package
    public TransportationPackageDTO transportationPackageToDTO(TransportationPackage transportationPackage) {
        return new TransportationPackageDTO(
                transportationPackage.getPackageType(),
                transportationPackage.getMaterial(),
                transportationPackage.getCurrentOrder().getId()
        );
    }
    public List<TransportationPackageDTO> transportationPackageToDTOList(List<TransportationPackage> transportationPackages) {
        return transportationPackages.stream().map(this::transportationPackageToDTO).collect(Collectors.toList());
    }

    // Product Package
    public ProductPackageDTO productPackageToDTO(ProductPackage productPackage) {
        return new ProductPackageDTO(
                productPackage.getPackageType(),
                productPackage.getMaterial(),
                productPackage.getCurrentPhysicalProduct().getId()
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
                physicalProductToDTOList(order.getPhysicalProducts())
        );
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
