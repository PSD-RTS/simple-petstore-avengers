package test.unit.org.testinfected.petstore.views;


import org.junit.Before;
import org.junit.Test;
import org.testinfected.petstore.views.PlainPage;
import org.w3c.dom.Element;
import test.support.org.testinfected.petstore.web.WebRoot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testinfected.hamcrest.dom.DomMatchers.*;
import static test.support.org.testinfected.petstore.builders.Builders.build;
import static test.support.org.testinfected.petstore.builders.CartBuilder.aCart;
import static test.support.org.testinfected.petstore.builders.ItemBuilder.anItem;
import static test.support.org.testinfected.petstore.web.OfflineRenderer.render;

public class AboutPageTest {
    Element elementAbout;
    String ABOUT_TEMPLATE = "about";

    @Before
    public void
    renderAbout() {
        try {
            elementAbout = render(ABOUT_TEMPLATE).from(WebRoot.pages()).asDom();
        }catch(Error e){
            System.out.println("Erreur"+e.getMessage());
        }

    }
    @Test
    public void
    hasSelectorAboutPage() {
        assertThat("has not selector about page", elementAbout,
                hasSelector("#about-page"));
    }

    @Test public void
    hasItemElement() {
        assertThat("not items people on the page", elementAbout, hasUniqueSelector("#list-people"));

    }

    @Test public void
    hasTheRightNumberOfDeveloper() {
        assertThat("about page has not 6 developer", elementAbout, hasSelector("#list-people li", hasSize(6)));

    }

    @Test public void
    hasDeveloperImage() {
        assertThat("has image", elementAbout, hasSelector("#list-people li", hasChild(hasTag("img"))));
    }
}
