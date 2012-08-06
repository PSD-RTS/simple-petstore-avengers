package test.unit.org.testinfected.petstore.templates;

import com.github.mustachejava.TemplateFunction;
import com.pyxis.petstore.domain.product.Product;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.petstore.util.ContextBuilder;
import org.w3c.dom.Element;
import test.support.com.pyxis.petstore.builders.Builder;
import test.support.org.testinfected.petstore.web.OfflineRenderer;
import test.support.org.testinfected.petstore.web.Paths;
import test.support.org.testinfected.petstore.web.WebRoot;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.testinfected.hamcrest.dom.DomMatchers.anElement;
import static org.testinfected.hamcrest.dom.DomMatchers.hasAttribute;
import static org.testinfected.hamcrest.dom.DomMatchers.hasChild;
import static org.testinfected.hamcrest.dom.DomMatchers.hasSelector;
import static org.testinfected.hamcrest.dom.DomMatchers.hasSize;
import static org.testinfected.hamcrest.dom.DomMatchers.hasText;
import static org.testinfected.hamcrest.dom.DomMatchers.hasUniqueSelector;
import static org.testinfected.petstore.util.ContextBuilder.context;
import static test.support.com.pyxis.petstore.builders.Builders.build;
import static test.support.com.pyxis.petstore.builders.ProductBuilder.aProduct;

public class ProductsPageTest {
    String PRODUCTS_TEMPLATE = "products";

    Element productsPage;
    Paths paths = Paths.root();
    List<Product> productList = new ArrayList<Product>();
    ContextBuilder context = context().with("products", productList).with("keyword", "dog");

    @Test public void
    displaysAllProductsFound() {
        addToProducts(aProduct(), aProduct());

        productsPage = renderProductsPage().using(context.with("matchCount", 2)).asDom();

        assertThat("products page", productsPage, hasUniqueSelector("#match-count", hasText("2")));
        assertThat("products page", productsPage, hasSelector("#catalog li[id^='product']", hasSize(2)));
    }

    @SuppressWarnings("unchecked") @Test public void
    displaysProductDetails() throws Exception {
        addToProducts(aProduct().withNumber("LAB-1234").named("Labrador").describedAs("Friendly").withPhoto("labrador.png"));

        productsPage = renderProductsPage().using(context.with("photo", new TemplateFunction() {
            public String apply(@Nullable String fileName) {
                return "/photos/" + fileName;
            }
        })).asDom();

        assertThat("products page", productsPage, hasUniqueSelector("li[id='product-LAB-1234']", anElement(
                hasUniqueSelector(".product-image", hasImage(paths.pathFor("/photos/labrador.png"))),
                hasUniqueSelector(".product-name", hasText("Labrador")),
                hasUniqueSelector(".product-description", hasText("Friendly")))));
    }

    @Test public void
    productNameAndPhotoLinkToProductInventory() {
        addToProducts(aProduct().named("Labrador").withNumber("LAB-1234"));

        productsPage = renderProductsPage().asDom();
        assertThat("products page", productsPage, hasSelector("li a", hasSize(2)));
        assertThat("products page", productsPage, hasSelector("li a", everyItem(hasAttribute("href", equalTo(paths.itemsPath("LAB-1234"))))));
    }

    private void addToProducts(Builder<Product>... products) {
        productList.addAll(build(products));
    }

    private Matcher<Element> hasImage(String imageUrl) {
        return hasChild(hasAttribute("src", equalTo(imageUrl)));
    }

    private OfflineRenderer renderProductsPage() {
        return OfflineRenderer.render(PRODUCTS_TEMPLATE).using(context).from(WebRoot.locate().pages);
    }
}
