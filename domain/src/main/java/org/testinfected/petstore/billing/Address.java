package org.testinfected.petstore.billing;

import org.testinfected.petstore.validation.Validates;
import org.testinfected.petstore.validation.NotNull;

import java.io.Serializable;

public class Address implements Serializable {
    private final NotNull<String> firstName;
    private final NotNull<String> lastName;
    private final String emailAddress;
    private final String street;
    private final String city;
    private final String zipcode;
    private final String state;
    private final String country;


    public Address(String firstName, String lastName, String emailAddress, String street, String city, String zipcode, String state, String country) {
        this.firstName = Validates.notNull(firstName);
        this.lastName = Validates.notNull(lastName);
        this.emailAddress = emailAddress;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.state = state;
        this.country = country;
    }

    public String getZipcode() {
		return zipcode;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!firstName.equals(address.firstName)) return false;
        if (!lastName.equals(address.lastName)) return false;
        if (emailAddress != null ? !emailAddress.equals(address.emailAddress) : address.emailAddress != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        return result;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

}
