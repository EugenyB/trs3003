package dao;

import tables.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eugeny on 16.03.2016.
 */
public class PersonDAO {
    private Connection connection;

    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    public Person create(String firstName, String lastName, int age, String adress) {
        Person person = new Person(firstName, lastName, age, adress);
        try {
            PreparedStatement query = connection.prepareStatement("INSERT INTO person (firstname, lastname, age, adress) VALUES (?,?,?,?)");
            query.setString(1,firstName);
            query.setString(2,lastName);
            query.setString(4,adress);
            query.setInt(3,age);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public List<Person> findAll() {
        try {
            Statement query = connection.createStatement();
            ResultSet resultSet = query.executeQuery("SELECT * FROM person");
            List<Person> result = new ArrayList<>();
            while (resultSet.next()) {
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String adress = resultSet.getString("adress");
                int age = resultSet.getInt("age");
                int id = resultSet.getInt("id");
                Person person = new Person(firstname,lastname,age,adress);
                person.setId(id);
                result.add(person);
            }
            resultSet.close();
            query.close();
            return result;
        } catch (SQLException e) {
            return null;
        }
    }

    public void delete(Person p) {
        try {
            PreparedStatement query = connection.prepareStatement("DELETE FROM person WHERE id = ?");
            query.setInt(1,p.getId());
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement query = connection.prepareStatement("DELETE FROM person WHERE id = ?");
            query.setInt(1,id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Person person) {
        try {
            PreparedStatement query = connection.prepareStatement("UPDATE person SET firstname=?, lastname=?, age=?, adress=? WHERE id = ?");
            query.setString(1, person.getFirstName());
            query.setString(2, person.getLastName());
            query.setInt(3, person.getAge());
            query.setString(4, person.getAdress());
            query.setInt(5, person.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Person person) {
        try {
            PreparedStatement query = connection.prepareStatement("INSERT INTO person (firstname, lastname, age, adress) VALUES (?,?,?,?)");
            query.setString(1, person.getFirstName());
            query.setString(2, person.getLastName());
            query.setInt(3, person.getAge());
            query.setString(4, person.getAdress());
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
