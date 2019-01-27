package sr.unasat.jpa.entities;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Adres {

    private static int count = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    @OneToMany(mappedBy = "adres", fetch = FetchType.EAGER)
    private Set<Person> personList;

    public Adres(String name) {
        this.name = name;
    }

    public Adres() {
    }

    public int getId() {
        if(id == 0){
            id = count;
        }
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(Set<Person> personList) {
        this.personList = personList;
    }

    @Override
    public String toString() {
        return "Adres{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
