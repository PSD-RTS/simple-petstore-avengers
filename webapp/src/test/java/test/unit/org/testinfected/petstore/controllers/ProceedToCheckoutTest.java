package test.unit.org.testinfected.petstore.controllers;

import com.vtence.molecule.Session;
import com.vtence.molecule.support.MockRequest;
import com.vtence.molecule.support.MockResponse;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.testinfected.petstore.controllers.ProceedToCheckout;
import org.testinfected.petstore.order.Cart;
import org.testinfected.petstore.views.Checkout;
import test.support.org.testinfected.petstore.builders.CartBuilder;
import test.support.org.testinfected.petstore.web.MockView;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static test.support.org.testinfected.petstore.builders.CartBuilder.aCart;
import static test.support.org.testinfected.petstore.builders.ItemBuilder.anItem;

public class ProceedToCheckoutTest {
    MockView<Checkout> view = new MockView<Checkout>();
    ProceedToCheckout checkout = new ProceedToCheckout(view);

    MockRequest request = new MockRequest();
    MockResponse response = new MockResponse();

    @Before
    public void
    createSession() {
        Session.set(request, new Session());
    }

    @SuppressWarnings("unchecked") @Test public void
    rendersBillWithAmountOfCartGrandTotal() throws Exception {
        final BigDecimal total = new BigDecimal("324.98");
        storeInSession(aCart().containing(anItem().priced(total)));

        checkout.handle(request, response);

        view.assertRenderedTo(response);
        view.assertRenderedWith(billWithTotal(total));
    }

    private Matcher<Object> billWithTotal(BigDecimal amount) {
        return hasProperty("total", equalTo(amount));
    }

    private void storeInSession(CartBuilder cart) {
        Session.get(request).put(Cart.class, cart.build());
    }
}