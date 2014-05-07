package test.support.org.testinfected.petstore.builders;

import org.testinfected.petstore.billing.Address;

public class AddressBuilder implements Builder<Address> {

    private String firstName = "John";
    private String lastName = "Doe";
    private String emailAddress = "jdoe@gmail.com";
    private String street = "Avenue Gare";
    private String city = "Genève"; 

    public static AddressBuilder anAddress() {
        return new AddressBuilder();
    }

    public AddressBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AddressBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AddressBuilder withEmail(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public Address build() {
        return new Address(firstName, lastName, emailAddress, street, city);
    }

    public AddressBuilder withStreet(String street) {
        this.street = street;
        return this;
    }
    public AddressBuilder withCity(String city) {
        this.city = city;
        return this;
    }
}