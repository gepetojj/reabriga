package entities;

import java.util.Objects;

public class DistributionCenter {
    String name;
    String address;
    String postalCode;

    public DistributionCenter() {
    }

    public DistributionCenter(String name, String address, String postalCode) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistributionCenter that = (DistributionCenter) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "DistributionCenter{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
