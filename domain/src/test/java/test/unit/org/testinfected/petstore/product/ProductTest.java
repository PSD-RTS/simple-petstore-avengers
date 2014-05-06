package test.unit.org.testinfected.petstore.product;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.petstore.product.Product;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static test.support.org.testinfected.petstore.builders.CreditCardBuilder.aVisa;
import static test.support.org.testinfected.petstore.builders.ProductBuilder.aProduct;
import static test.support.org.testinfected.petstore.matchers.ValidationMatchers.on;
import static test.support.org.testinfected.petstore.matchers.ValidationMatchers.validationOf;
import static test.support.org.testinfected.petstore.matchers.ValidationMatchers.violates;
import static test.support.org.testinfected.petstore.matchers.ValidationMatchers.withMessage;

public class ProductTest {

    String MISSING = null;
    String EMPTY = "";

    
    
    
    @Test public void
    hasADefaultPhoto() {
        assertThat("default photo", aProductWithoutAPhoto(), productWithPhoto("missing.png"));
    }

    @Test public void
    productIsUniquelyIdentifiedByItsNumber() {
        Product product = aProduct().withNumber("AAA-123").build();
        Product shouldMatch = aProduct().withNumber("AAA-123").build();
        Product shouldNotMatch = aProduct().withNumber("BBB-456").build();
        assertThat("product", product, equalTo(shouldMatch));
        assertThat("hash code", product.hashCode(), equalTo(shouldMatch.hashCode()));
        assertThat("product", product, not(equalTo(shouldNotMatch)));
    }
    
    @Test public void
    areInvalidWithAnEmptyOrIncorrectProductNumber() {
        assertThat("empty number", validationOf(aProduct().withNumber(EMPTY)), violates(on("number"), withMessage("empty")));
        assertThat("incorrect number with only numbers", validationOf(aProduct().withNumber("1111111")), violates(on("number"), withMessage("incorrect")));
        assertThat("incorrect number with only letters", validationOf(aProduct().withNumber("AAAAAAA")), violates(on("number"), withMessage("incorrect")));
        assertThat("incorrect number with 4 letters and 4 numbers", validationOf(aProduct().withNumber("AAAA-1111")), violates(on("number"), withMessage("incorrect")));
        assertThat("incorrect number with 3 letters and 5 numbers", validationOf(aProduct().withNumber("AAA-11111")), violates(on("number"), withMessage("incorrect")));
        assertThat("incorrect number with lowercase letters", validationOf(aProduct().withNumber("aaa-1111")), violates(on("number"), withMessage("incorrect")));
           
    }

    private Product aProductWithoutAPhoto() {
        return aProduct().withoutAPhoto().build();
    }

    private Matcher<? super Product> productWithPhoto(String fileName) {
        return new FeatureMatcher<Product, String>(equalTo(fileName), "a product with photo", "photo") {
            protected String featureValueOf(Product actual) {
                return actual.getPhotoFileName();
            }
        };
    }
}
