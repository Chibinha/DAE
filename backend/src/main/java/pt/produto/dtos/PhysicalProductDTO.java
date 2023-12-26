package pt.produto.dtos;

public class PhysicalProductDTO {
    private Long id;
    private ProductDTO product;
    private String serialNumber; // Unique serial number for each item

    public PhysicalProductDTO() {
    }

    public PhysicalProductDTO(Long id, ProductDTO product, String serialNumber) {
        this.id = id;
        this.product = product;
        this.serialNumber = serialNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
