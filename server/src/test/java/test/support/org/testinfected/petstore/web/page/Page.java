package test.support.org.testinfected.petstore.web.page;

import com.objogate.wl.web.AsyncWebDriver;

public abstract class Page {

    protected final AsyncWebDriver browser;

    protected Page(AsyncWebDriver browser) {
        this.browser = browser;
    }
}
