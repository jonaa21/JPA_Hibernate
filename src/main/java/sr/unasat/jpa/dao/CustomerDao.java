package sr.unasat.jpa.dao;

import sr.unasat.jpa.config.JPAConfiguration;
import sr.unasat.jpa.entities.Customer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

    private EntityManager entityManager;
    private List<Customer> customers = new ArrayList<>();

    public CustomerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Customer> selectAllCustomers() {
        entityManager.getTransaction().begin();
        String jpql = "select c from Customer c";
        TypedQuery<Customer> query = entityManager.createQuery(jpql, Customer.class);
        customers = query.getResultList();
        entityManager.getTransaction().commit();
        return customers;
    }

    public Customer selectCustomerById(int id) {
        entityManager.getTransaction().begin();
        String jpql = "select c from Customer c where c.id = :id";
        TypedQuery<Customer> query = entityManager.createQuery(jpql, Customer.class);
        query.setParameter("id", id);
        Customer customer = query.getSingleResult();
        entityManager.getTransaction().commit();
        return customer;
    }

    public void insertCustomer(Customer customer) {
        selectAllCustomers();
        for (Customer c : customers) {
            if (!customer.getPerson().getFirstname().equals(c.getPerson().getFirstname()) ||
                    !customer.getPerson().getLastname().equals(c.getPerson().getLastname())) {
                continue;
            }
            System.out.println("This customer has already been added");
            return;
        }
        entityManager.getTransaction().begin();
        entityManager.persist(customer.getPerson().getAdres());
        entityManager.persist(customer.getPerson());
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
    }
}
