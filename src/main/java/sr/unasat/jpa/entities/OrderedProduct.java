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
    private Product product;

    @Column
    private int quantity;

    @ManyToMany
    @JoinTable(name = "order_detail", joinColumns = @JoinColumn(name = "ordered_prod_fk"),
            inverseJoinColumns = @JoinColumn(name = "receipt_fk"))
    private Set<Receipt> receipt;

    public OrderedProduct(Product product, int quantity) {
        this.product = product;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderedProduct{" +
                "id=" + id +
                ", product=" + product.getName() +
                ", quantity=" + quantity +
                '}';
    }
}
