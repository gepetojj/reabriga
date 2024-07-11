package entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "shelter")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String name;
    String address;
    String chief;
    String phone;
    String email;
    Integer capacity;
    Integer occupation;

    public Shelter() {
    }

    public Shelter(String name, String address, String chief, String phone, String email, Integer capacity, Integer occupation) {
        this.name = name;
        this.address = address;
        this.chief = chief;
        this.phone = phone;
        this.email = email;
        this.capacity = capacity;
        this.occupation = occupation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChief() {
        return chief;
    }

    public void setChief(String chief) {
        this.chief = chief;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getOccupation() {
        return occupation;
    }

    public void setOccupation(Integer occupation) {
        this.occupation = occupation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(getName(), shelter.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", chief='" + chief + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", capacity=" + capacity +
                ", occupation=" + occupation +
                '}';
    }
}
