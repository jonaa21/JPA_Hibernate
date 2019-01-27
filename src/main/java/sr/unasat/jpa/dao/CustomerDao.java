package sr.unasat.jpa.dao;

import sr.unasat.jpa.entities.Customer;

import javax.persistence.EntityExistsException;
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

    public Customer insertCustomer(Customer customer) {
        selectAllCustomers();
        for (Customer cust : customers) {
            if (!customer.getPerson().getFirstname().equals(cust.getPerson().getFirstname()) ||
                    !customer.getPerson().getLastname().equals(cust.getPerson().getLastname())) {
                continue;
            } else {
                throw new EntityExistsException();
            }
        }
        entityManager.getTransaction().begin();
        entityManager.persist(customer.getPerson().getAdres());
        entityManager.persist(customer.getPerson());
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        return customer;
    }

    public Customer deleteCustomer(int personId) {
        Customer customer = selectCustomerById(personId);
        entityManager.getTransaction().begin();
        entityManager.remove(customer);
        entityManager.getTransaction().commit();
        return customer;
    }
}
