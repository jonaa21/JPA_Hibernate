package sr.unasat.jpa.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "delivery_method")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String method;

    @OneToMany(mappedBy = "deliveryMethod")
    private Set<Receipt> receipt;

    public Delivery(String method) {
        this.method = method;
    }

    public Delivery() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Set<Receipt> getReceipt() {
        return receipt;
    }

    public void setReceipt(Set<Receipt> receipt) {
        this.receipt = receipt;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "method='" + method + '\'' +
                '}';
    }
}
