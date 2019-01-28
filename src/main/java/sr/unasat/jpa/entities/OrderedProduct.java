package sr.unasat.jpa.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ordered_product")
public class OrderedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_fk")
    private AvailableProduct availableProduct;

    @Column
    private int quantity;

    @ManyToMany(mappedBy = "orderedProducts")
    private Set<Receipt> receipt;

    public OrderedProduct(AvailableProduct availableProduct, int quantity) {
        this.availableProduct = availableProduct;
        this.quantity = quantity;
    }

    public OrderedProduct() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AvailableProduct getAvailableProduct() {
        return availableProduct;
    }

    public void setAvailableProduct(AvailableProduct availableProduct) {
        this.availableProduct = availableProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<Receipt> getReceipt() {
        return receipt;
    }

    public void setReceipt(Set<Receipt> receipt) {
        this.receipt = receipt;
    }

    @Override
    public String toString() {
        return "OrderedProduct{" +
                "id=" + id +
                ", availableProduct=" + availableProduct.getName() +
                ", quantity=" + quantity +
                '}';
    }
}
