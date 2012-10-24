package org.testinfected.petstore;

import com.pyxis.petstore.domain.product.AttachmentStorage;
import com.pyxis.petstore.domain.product.ItemInventory;
import com.pyxis.petstore.domain.product.ProductCatalog;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.testinfected.petstore.controllers.CreateProduct;
import org.testinfected.petstore.controllers.Home;
import org.testinfected.petstore.controllers.ListItems;
import org.testinfected.petstore.controllers.ListProducts;
import org.testinfected.petstore.controllers.Logout;
import org.testinfected.petstore.jdbc.ItemDatabase;
import org.testinfected.petstore.jdbc.JDBCTransactor;
import org.testinfected.petstore.jdbc.ProductsDatabase;
import org.testinfected.petstore.middlewares.ConnectionManager;
import org.testinfected.petstore.procurement.ProcurementRequestListener;
import org.testinfected.petstore.procurement.PurchasingAgent;
import org.testinfected.petstore.routing.Router;
import org.testinfected.petstore.routing.Routes;
import org.testinfected.petstore.util.FileSystemPhotoStore;

import java.nio.charset.Charset;
import java.sql.Connection;

public class Routing implements Application {

    private final RenderingEngine renderer;
    private final Charset charset;

    public Routing(final RenderingEngine renderer, final Charset charset) {
        this.renderer = renderer;
        this.charset = charset;
    }

    public void handle(final Request request, final Response response) throws Exception {
        final AttachmentStorage attachmentStorage = new FileSystemPhotoStore("/photos");

        final Connection connection = ConnectionManager.get(request);
        final Transactor transactor = new JDBCTransactor(connection);
        final ProductCatalog productCatalog = new ProductsDatabase(connection);
        final ProcurementRequestListener requestListener = new PurchasingAgent(productCatalog, transactor);
        final ItemInventory itemInventory = new ItemDatabase();

        Routes routes = Routes.draw(new Router() {{
            get("/products").to(controller(new ListProducts(productCatalog, attachmentStorage)));
            post("/products").to(controller(new CreateProduct(requestListener)));
            get("/products/:id/items").to(controller(new ListItems(itemInventory)));
            delete("/logout").to(controller(new Logout()));
            map("/").to(controller(new Home()));
        }});
        routes.handle(request, response);
    }

    private Application controller(final Controller controller) {
        return new Application() {
            public void handle(Request request, Response response) throws Exception {
                controller.process(new SimpleRequest(request), new SimpleResponse(response, renderer, charset));
            }
        };
    }
}
