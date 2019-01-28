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

    public Receipt(Customer customer, double totalPrice, Delivery deliveryMethod) {
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.purchaseDate = new Date();
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

    public Set<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(Set<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public void addCustomer(Customer customer) {
        if (!customer.getReceipt().contains(this)) {
            customer.getReceipt().add(this);
        }
    }

    public void removeCustomer(Customer customer) {
        if (customer.getReceipt().contains(this)) {
            customer.getReceipt().remove(this);
        }
    }

    public void addOrder(OrderedProduct orderedProduct) {
        if (!orderedProduct.getReceipt().contains(this)) {
            orderedProduct.getReceipt().add(this);
        }
    }

    public void removeOrders(OrderedProduct orderedProduct) {
        if (orderedProduct.getReceipt().contains(this)) {
            orderedProduct.getReceipt().remove(this);
        }
    }

    public void addDeliveryMethod(Delivery delivery) {
        if (!delivery.getReceipt().contains(this)) {
            delivery.getReceipt().add(this);
        }
    }

    public void removeDelivery(Delivery delivery) {
        if (delivery.getReceipt().contains(this)) {
            delivery.getReceipt().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", customer=" + customer +
                ", purchaseDate=" + purchaseDate +
                ", deliveryMethod=" + deliveryMethod +
                ", orderedProducts=" + orderedProducts +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
