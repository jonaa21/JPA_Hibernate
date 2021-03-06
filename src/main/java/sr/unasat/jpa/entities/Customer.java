package sr.unasat.jpa.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Entity
@Table
public class Customer {

    private static List<Integer> availableCustomerNumbers = new ArrayList<>(20);

    @Id
    private int id;

    @Column(name = "customer_number")
    private int customerNumber;

    @OneToOne
    @JoinColumn(name = "person_fk")
    private Person person;

    @OneToMany(mappedBy = "customer")
    private Set<Receipt> receipt;

    public Customer(Person person) {
        this.customerNumber = getCustomerNumber();
        this.person = person;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public int getCustomerNumber() {
        if (customerNumber == 0){
            setCustomerNumber();
        }
        return customerNumber;
    }

    private void setCustomerNumber() {
        int customerNumber = new Random().nextInt(1000);
        if (!availableCustomerNumbers.contains(customerNumber)) {
            availableCustomerNumbers.add(customerNumber);
        }
        this.customerNumber = customerNumber;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        this.id = person.getId();
    }

    public void addPerson(Person person){

        if(!person.getLastname().equals(this.getPerson().getLastname()) ||
            !person.getFirstname().equals(this.getPerson().getFirstname())){
            person.setCustomer(this);
        }
    }

    public void removePerson(Person person){
        if(person.getCustomer() == this){
            person.setCustomer(null);
        }
    }

    public Set<Receipt> getReceipt() {
        return receipt;
    }

    public void setReceipt(Set<Receipt> receipt) {
        this.receipt = receipt;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                "customerNumber=" + customerNumber +
                ", person= " + getPerson().getFullName() +
                '}';
    }
}
