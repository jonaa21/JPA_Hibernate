package sr.unasat.jpa.entities;

import javax.persistence.*;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adres_fk")
    private Adres adres;

    @OneToOne(mappedBy = "person")
    private Customer customer;

    public Person(String firstname, String lastname, Adres adres) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.adres = adres;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getFullName() {
        return getLastname() + ", " + getFirstname();
    }

    @Override
    public String toString() {
        if (this.customer != null) {
            return "Person{" +
                    "id=" + id +
                    ", firstname='" + firstname + '\'' +
                    ", lastname='" + lastname + '\'' +
                    ", customer=" + customer.getCustomerNumber() +
                    ", adres=" + adres.getName() +
                    '}';
        }
        return "Person{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", adres=" + adres.getName() +
                '}';
    }
}
