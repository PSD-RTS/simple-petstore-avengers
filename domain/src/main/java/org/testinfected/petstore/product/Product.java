package org.testinfected.petstore.product;

import java.io.Serializable;

import org.testinfected.petstore.validation.Constraint;
import org.testinfected.petstore.validation.Validates;

public class Product implements Serializable {

    public static final String MISSING_PHOTO = "missing.png";

    @SuppressWarnings("unused")
	private long id;

    private final Constraint<String> number;
    private final String name;

    private String description;
    private Attachment photo;

    public Product(String number, String name) {
        this.number = Validates.both( Validates.notEmpty(number), Validates.correctnessOf(number) );
        this.name = name;
	}

    public String getNumber() {
		return number.get();
	}

	public String getName() {
		return name;
	}

    public String getDescription() {
		return description;
	}

    public void setDescription(String description) {
		this.description = description;
	}

    public String getPhotoFileName() {
        return hasPhoto() ? photo.getFileName() : MISSING_PHOTO;
    }

    public void attachPhoto(Attachment photo) {
        this.photo = photo;
	}

    public boolean hasPhoto() {
        return photo != null && photo.getFileName() != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (getNumber() != null ? !getNumber().equals(product.getNumber()) : product.getNumber() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getNumber() != null ? getNumber().hashCode() : 0;
    }

    @Override
    public String toString() {
        return getNumber() + " (" + name + ")"; 
	}
}
