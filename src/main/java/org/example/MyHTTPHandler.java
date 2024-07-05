package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * responds to application HTTP requests. HttpHandler is the interface that this class implements.
 * to handle incoming HTTP requests and produce relevant answers.
 */
public class MyHTTPHandler implements HttpHandler {
    private static final String JDBC_URL = "jdbc:sqlite:identifier.sqlite";
    private static final FoodProductDAO dao = FoodProductDAO.getInstance(JDBC_URL);
    private static Customer loggedInUser = null;

    public static boolean isValidDateFormat(String date) {
        String dateFormatPattern = "\\d{4}-\\d{2}-\\d{2}";

        Pattern pattern = Pattern.compile(dateFormatPattern);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    /**
     * Handles incoming HTTP requests.
     *
     * @param exchange, the client-server communication is represented by the HTTP exchange object.
     * @throws IOException if an input/output error happens while processing a request.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();

        switch (HTTP_Methods.getEquivalent(requestMethod)) {
            case GET:
                handleGetRequest(exchange, path);
                break;
            case POST:
                handlePostRequest(exchange, path);
                break;
            default:
                sendResponse(exchange, 405, "Method Not Allowed");
        }
    }

    /**
     * Handles GET requests based on the specified path.
     *
     * @param exchange the HTTP exchange object that serves as a client-server communication representation.
     * @param path     The requested path.
     * @throws IOException If an I/O error occurs during request processing.
     */
    private void handleGetRequest(HttpExchange exchange, String path) throws IOException {
        String selectedCategory = "all";
        String selectedExpiry = "all";
        String searchQuery = null;

        if (path.equals("/")) {
            if (loggedInUser != null) {
                Map<String, Object> model = new HashMap<>();
                model.put("user", loggedInUser);
                renderHtmlPage(exchange, "index.ftl", model);
            } else renderHtmlPage(exchange, "login.ftl", null);
        } else if (path.equals("/logout")) {
            loggedInUser = null;
            sendRedirect(exchange, "/login");
        } else if (path.equals("/login")) {
            renderHtmlPage(exchange, loggedInUser != null ? "index.ftl" : "login.ftl", null);
        } else if (path.equals("/register")) {
            renderHtmlPage(exchange, loggedInUser != null ? "index.ftl" : "register.ftl", null);
        } else if (path.equals("/cart")) {
            renderHtmlPage(exchange, "cart.ftl", null);
        } else if (path.equals("/products")) {
            if (loggedInUser == null)
                renderHtmlPage(exchange, "login.ftl", null);
            else {
                Map<String, Object> model = new HashMap<>();

                URI uri = exchange.getRequestURI();
                String query = uri.getQuery();

                if (query != null) {
                    String[] queryParams = query.split("&");
                    for (String param : queryParams) {
                        String[] keyValue = param.split("=");
                        if (keyValue.length == 2) {
                            String key = keyValue[0];
                            String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);

                            if (key.equals("category")) {
                                selectedCategory = value;
                            } else if (key.equals("search")) {
                                searchQuery = value;
                            } else if (key.equals("expiry")) {
                                selectedExpiry = value;
                            }
                        }
                    }
                }

                List<FoodProduct> products;

                if (searchQuery != null) {
                    products = dao.findProductsByDescription(searchQuery);
                } else if (!selectedCategory.equals("all")) {
                    products = dao.findProductsByCategory(selectedCategory);
                } else {
                    products = dao.findAllProducts();
                }

                if (!selectedExpiry.equals("all")) {
                    products = FoodProduct.filterExpiration(products, selectedExpiry);
                }

                List<String> uniqueCategories = dao.findUniqueCategories();

                model.put("products", products);
                model.put("uniqueCategories", uniqueCategories);
                model.put("selectedExpiry", selectedExpiry);
                model.put("selectedCategory", selectedCategory);
                model.put("searchQuery", searchQuery);

                renderHtmlPage(exchange, "products.ftl", model);
            }
        } else if (path.equals("/add-product")) {
            if (loggedInUser == null)
                renderHtmlPage(exchange, "login.ftl", null);
            else
                renderHtmlPage(exchange, "addProduct.ftl", null);
        } else if (path.startsWith("/product/") && path.endsWith("/update")) {
            if (loggedInUser == null)
                renderHtmlPage(exchange, "login.ftl", null);
            else {
                int productId = extractProductId(path);
                FoodProduct product = dao.findProduct(productId);
                if (product != null) {
                    Map<String, Object> model = new HashMap<>();
                    model.put("product", product);
                    renderHtmlPage(exchange, "updateProduct.ftl", model);
                } else {
                    sendResponse(exchange, 404, "Product not found");
                }
            }
        } else if (path.startsWith("/product/") && path.endsWith("/delete")) {
            if (loggedInUser == null)
                renderHtmlPage(exchange, "login.ftl", null);
            else {

                int productId = extractProductId(path);
                FoodProduct product = dao.findProduct(productId);
                if (product != null) {
                    Map<String, Object> model = new HashMap<>();
                    model.put("product", product);
                    renderHtmlPage(exchange, "deleteProduct.ftl", model);
                } else {
                    sendResponse(exchange, 404, "Product not found");
                }
            }
        } else {
            sendResponse(exchange, 404, "Not Found");
        }
    }

    /**
     * Handles POST requests based on the specified path.
     *
     * @param exchange The HTTP exchange object representing the client-server communication.
     * @param path     The requested path.
     * @throws IOException If an I/O error occurs during request processing.
     */
    private void handlePostRequest(HttpExchange exchange, String path) throws IOException {
        switch (path) {
            case "/login": {
                Map<String, String> formData = parseFormData(exchange);
                String username = formData.get("username");
                String password = formData.get("password");

                loggedInUser = CustomersDAO.getInstance(JDBC_URL).authenticateUser(username, password);
                if (loggedInUser != null) {
                    sendRedirect(exchange, "/");
                } else {
                    Map<String, Object> model = new HashMap<>();
                    model.put("error", "Invalid username or password");
                    renderHtmlPage(exchange, "login.ftl", model);
                }
                break;
            }
            case "/register": {
                Map<String, String> formData = parseFormData(exchange);
                String businessName = formData.get("businessName");
                String telephoneNumber = formData.get("telephoneNumber");
                String username = formData.get("username");
                String password = formData.get("password");
                String addressLine1 = formData.get("addressLine1");
                String addressLine2 = formData.get("addressLine2");
                String addressLine3 = formData.get("addressLine3");
                String country = formData.get("country");
                String postCode = formData.get("postCode");

                Customer newUser = new Customer(businessName, telephoneNumber, username, password,
                        new Address(addressLine1, addressLine2, addressLine3, country, postCode));

                if (CustomersDAO.getInstance(JDBC_URL).registerUser(newUser)) {
                    sendRedirect(exchange, "/login");
                } else {
                    Map<String, Object> model = new HashMap<>();
                    model.put("error", "Failed to register user");
                    renderHtmlPage(exchange, "register.ftl", model);
                }
                break;
            }
            case "/add-product": {
                Map<String, String> formData = parseFormData(exchange);
                String sku = formData.get("sku");
                String category = formData.get("category");
                String description = formData.get("description");
                String expiryDate = formData.get("expiry_date");
                int price = Integer.parseInt(formData.get("price"));

                if (isValidDateFormat(expiryDate)) {
                    if (dao.addProduct(new FoodProduct(sku, description, category, expiryDate, price))) {
                        sendRedirect(exchange, "/products");
                    } else {
                        sendResponse(exchange, 500, "Failed to add product");
                    }
                }
                break;
            }
            case "/update-product": {
                Map<String, String> formData = parseFormData(exchange);
                int productId = Integer.parseInt(formData.get("id"));
                String sku = formData.get("sku");
                String category = formData.get("category");
                String description = formData.get("description");
                String expiryDate = formData.get("expiry_date");
                int price = Integer.parseInt(formData.get("price"));

                if (isValidDateFormat(expiryDate)) {
                    if (dao.updateProduct(new FoodProduct(productId, sku, description, category, expiryDate, price))) {
                        sendRedirect(exchange, "/products");
                    } else {
                        sendResponse(exchange, 500, "Failed to update product");
                    }
                }
                break;
            }
            case "/delete-product": {
                Map<String, String> formData = parseFormData(exchange);
                int productId = Integer.parseInt(formData.get("id"));

                if (dao.deleteProduct(productId)) {
                    sendRedirect(exchange, "/products");
                } else {
                    sendResponse(exchange, 500, "Failed to delete product");
                }
                break;
            }
            case "/cart":
                Map<String, Object> model = new HashMap<>();
                Map<String, String> formData = parseFormData(exchange);
                String checkedItems = formData.get("checkedItems");
                List<FoodProduct> foodProducts = dao.getFoodsHavingIDs(checkedItems);
                double totalPrice = foodProducts.stream().map(FoodProduct::getPrice).collect(Collectors.toList()).stream().mapToInt(Integer::intValue).sum();
                model.put("products", foodProducts);
                model.put("totalPrice", totalPrice);
                renderHtmlPage(exchange, "cart.ftl", model);
                break;
            default:
                sendResponse(exchange, 404, "Not Found");
                break;
        }
    }

    /**
     * Extracts the product ID from the given path.
     *
     * @param path The path containing the product ID.
     * @return The extracted product ID.
     */
    public int extractProductId(String path) {
        String[] parts = path.split("/");
        return Integer.parseInt(parts[2]);
    }

    /**
     * Parses form data from the request body.
     *
     * @param exchange The HTTP exchange object representing the client-server communication.
     * @return A map containing form data key-value pairs.
     * @throws IOException If an I/O error occurs during form data parsing.
     */
    Map<String, String> parseFormData(HttpExchange exchange) throws IOException {
        Map<String, String> formData = new HashMap<>();
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        String[] pairs = requestBody.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                formData.put(key, value);
            }
        }
        return formData;
    }

    /**
     * Renders an HTML page using the specified template and model.
     *
     * @param exchange     The HTTP exchange object representing the client-server communication.
     * @param templateName The name of the template to render.
     * @param model        The model containing data to be used in the template.
     * @throws IOException If an I/O error occurs during template rendering.
     */

    private void renderHtmlPage(HttpExchange exchange, String templateName, Map<String, Object> model) throws IOException {
        String templatePath = "/templates/" + templateName;
        String html = WebController.renderTemplate(templatePath, model);
        sendResponse(exchange, 200, html);
    }

    /**
     * Sends an HTTP response with the specified status code and content.
     *
     * @param exchange   The HTTP exchange object representing the client-server communication.
     * @param statusCode The HTTP status code for the response.
     * @param response   The content of the response.
     * @throws IOException If an I/O error occurs during response sending.
     */
    void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    /**
     * Redirects the client to the specified location.
     *
     * @param exchange The HTTP exchange object representing the client-server communication.
     * @param location The location to which the client should be redirected.
     * @throws IOException If an I/O error occurs during redirection.
     */
    void sendRedirect(HttpExchange exchange, String location) throws IOException {
        exchange.getResponseHeaders().set("Location", location);
        sendResponse(exchange, 302, "Redirecting to " + location);
    }

    /**
     * Enumeration representing HTTP methods (GET and POST).
     */
    private enum HTTP_Methods {
        GET("GET"), POST("POST");
        private final String method;

        HTTP_Methods(String method) {
            this.method = method;
        }

        /**
         * Gets the equivalent HTTP method enum for the given method string.
         *
         * @param method The HTTP method as a string.
         * @return The corresponding HTTP_Methods enum.
         */
        public static HTTP_Methods getEquivalent(String method) {
            if (method.equals("GET"))
                return GET;
            else if (method.equals("POST"))
                return POST;
            return GET; //default is set to GET
        }
    }
}
