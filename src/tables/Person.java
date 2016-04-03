package tables;

/**
 * Created by eugeny on 16.03.2016.
 */
public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String adress;

    public Person(String firstName, String lastName, int age, String adress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.adress = adress;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", adress='" + adress + '\'' +
                '}';
    }
}
