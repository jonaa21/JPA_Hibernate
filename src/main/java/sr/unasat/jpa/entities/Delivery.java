package sr.unasat.jpa.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "delivery_method")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String method;

    @OneToMany(mappedBy = "deliveryMethod")
    private List<Receipt> receipt;

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

    @Override
    public String toString() {
        return "Delivery{" +
                "method='" + method + '\'' +
                '}';
    }
}
