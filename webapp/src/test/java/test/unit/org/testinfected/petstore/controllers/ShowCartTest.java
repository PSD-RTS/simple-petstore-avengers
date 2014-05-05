package test.unit.org.testinfected.petstore.controllers;

import com.vtence.molecule.Session;
import com.vtence.molecule.support.MockRequest;
import com.vtence.molecule.support.MockResponse;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.testinfected.petstore.controllers.ShowCart;
import org.testinfected.petstore.controllers.StaticView;
import org.testinfected.petstore.order.Cart;

import test.support.org.testinfected.petstore.web.MockView;
import static test.support.org.testinfected.petstore.builders.CartBuilder.aCart;
import static test.support.org.testinfected.petstore.builders.ItemBuilder.anItem;

public class ShowCartTest {

    MockView<Void>  page        = new MockView<Void>();
    StaticView      home        = new StaticView( page );
    
    MockView<Cart>  view        = new MockView<Cart>();
    ShowCart        showCart    = new ShowCart(view);

    MockRequest request = new MockRequest();
    MockResponse response = new MockResponse();

    @Before
    public void
    createSession() {
        Session.set(request, new Session());
    }

    @Test public void
    rendersCartContent() throws Exception {
        final Cart cart = aCart().containing(anItem()).build();
        storeInSession(cart);

        showCart.handle(request, response);
        view.assertRenderedTo(response);
        view.assertRenderedWith(sameCartAs(cart));
    }

    @Test public void 
    rendersEmptyCart () throws Exception { 
        Session.set(request, new Session()); 
        final Cart cart = new Cart(); 
        storeInSession(cart); 
        showCart.handle(request, response); 
        response.assertRedirectedTo("/");
        
    }
    
    
    private Matcher<Object> sameCartAs(Cart cart) {
        return Matchers.<Object>sameInstance(cart);
    }

    private void storeInSession(Cart cart) {
        Session.get(request).put(Cart.class, cart);
    }
}