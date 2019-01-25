package sr.unasat.jpa.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_fk")
    private Customer customer;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_fk")
    private Delivery deliveryMethod;

    @ManyToMany
    @JoinTable(name = "order_detail", joinColumns = @JoinColumn(name = "receipt_fk"),
    inverseJoinColumns = @JoinColumn(name = "ordered_prod_fk"))
    private Set<OrderedProduct> orderedProducts;

    public Receipt(Customer customer, double totalPrice, Date purchaseDate, Delivery deliveryMethod) {
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
        this.deliveryMethod = deliveryMethod;
    }

    public Receipt() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Delivery getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(Delivery deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "customer=" + customer +
                ", totalPrice=" + totalPrice +
                ", purchaseDate=" + purchaseDate +
                ", deliveryMethod=" + deliveryMethod +
                '}';
    }
}
