package org.testinfected.petstore.product;

import org.testinfected.petstore.validation.Constraint;
import org.testinfected.petstore.validation.Path;
import org.testinfected.petstore.validation.Report;

import java.io.Serializable;
import java.util.regex.Pattern;

public class CorrectProductNumber implements Constraint<String>, Serializable {

    private static final String INCORRECT = "incorrect";

    private static final Pattern NUMBER = Pattern.compile("^[A-Z]{3}-[0-9]{4}$", Pattern.DOTALL);
    
    private final String productNumber;

    public CorrectProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String get() {
        return productNumber;
    }

    public void check(Path path, Report report) {
        if (!satisfied()) report.violation(path, INCORRECT, productNumber);
    }

    private boolean satisfied() {
        if (productNumber == null) return false;
        return NUMBER.matcher(productNumber).matches();
    }
}
