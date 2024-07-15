package entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shelter")
public class Shelter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty(message = "O campo nome é obrigatório.")
    String name;
    @NotEmpty(message = "O campo endereço é obrigatório.")
    String address;
    @NotEmpty(message = "O campo responsável é obrigatório.")
    @Size(min = 4, max = 50, message = "O campo responsável deve ter entre 4 e 50 caracteres.")
    String chief;
    @NotEmpty(message = "O campo telefone é obrigatório.")
    @Size(min = 11, max = 11, message = "O campo telefone deve ter 11 caracteres. Ex: 82999999999")
    String phone;
    @NotEmpty(message = "O campo email é obrigatório.")
    @Email(message = "O campo email deve ter um formato válido. Ex: email@gmail.com")
    String email;

    @OneToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    Inventory inventory;
    @OneToMany
    List<ItemOrder> itemOrders = new ArrayList<>();

    public Shelter() {
    }

    public Shelter(String name, String address, String chief, String phone, String email) {
        this.name = name;
        this.address = address;
        this.chief = chief;
        this.phone = phone;
        this.email = email;
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

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<ItemOrder> getItemOrders() {
        return itemOrders;
    }

    public void addItemOrder(ItemOrder itemOrder) {
        itemOrders.add(itemOrder);
    }

    public void removeItemOrder(ItemOrder itemOrder) {
        itemOrders.remove(itemOrder);
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
                '}';
    }
}
