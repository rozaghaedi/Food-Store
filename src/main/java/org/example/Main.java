package org.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Scanner;


/**
 * The main class for the Food Store application.
 */
public class Main {

    /**
     * Scanner object for handling console input.
     */
    private static final Scanner in = new Scanner(System.in);

    /**
     * JDBC URL to establish a SQLite database connection.
     */
    private static final String JDBC_URL = "jdbc:sqlite:identifier.sqlite";

    /**
     * The web server port number.
     */
    private static final int PORT = 4567;

    /**
     * For connecting with food product data in the database, use the Data Access Object.
     */
    private static final FoodProductDAO dao = FoodProductDAO.getInstance(JDBC_URL);

    /**
     * The main entry point for the Food Store application.
     * two distinct threads for managing console and web interactions are initialized and started.
     */
    public static void main(String[] args) {
        Thread consoleThread = new Thread(Main::handleConsole);
        Thread webThread = new Thread(Main::handleWeb);
        consoleThread.start();
        webThread.start();
    }

    /**
     * Method to handle the functionality of the web server.
     */
    private static void handleWeb() {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", new MyHTTPHandler());
            server.setExecutor(null);
            server.start();

            System.out.printf("Web server started on port %s%n", PORT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     Method to handle console exchanges for the Food Store application.
     */
    private static void handleConsole() {
        try {
            Class.forName("org.sqlite.JDBC");
            String selection;
            do {
                System.out.println("--------------------");
                System.out.println("The Food Store");
                System.out.println("Choose from these options");
                System.out.println("--------------------");
                System.out.println("[1] List all products");
                System.out.println("[2] Search for product by ID");
                System.out.println("[3] Add a new product");
                System.out.println("[4] Update a product by ID");
                System.out.println("[5] Delete a product by ID");
                System.out.println("[6] Exit");
                System.out.println("===> Enter your option: ");
                selection = in.nextLine();
                System.out.println();

                switch (selection) {
                    case "1":
                        System.out.println("Retrieving all products ...");
                        List<FoodProduct> items = dao.findAllProducts();
                        for (FoodProduct item : items) {
                            System.out.println(item);
                        }
                        System.out.println();
                        break;
                    case "2":
                        System.out.println("\nSearch for a product by ID");
                        int id = Integer.parseInt(in.nextLine());
                        FoodProduct foodProduct = dao.findProduct(id);
                        if (foodProduct != null)
                            System.out.println(foodProduct);
                        else
                            System.err.println("No such product exists!");
                        break;
                    case "3":
                        System.out.println("Enter the product SKU: ");
                        String enteredSKU = in.nextLine();
                        System.out.println("Enter the product category: ");
                        String enteredCategory = in.nextLine();
                        System.out.println("Enter the product description: ");
                        String enteredDescription = in.nextLine();
                        System.out.println("Enter the product price: ");
                        int enteredPrice = Integer.parseInt(in.nextLine());
                        String expiryDate;
                        do {
                            System.out.println("Enter the product expiry Date (yyyy-MM-dd): ");
                            expiryDate = in.nextLine();
                        } while (!MyHTTPHandler.isValidDateFormat(expiryDate));
                        if (dao.addProduct(new FoodProduct(enteredSKU, enteredDescription, enteredCategory, expiryDate, enteredPrice)))
                            System.out.println("Food product added successfully!");
                        else
                            System.err.println("Failed to add product!");
                        break;
                    case "4":
                        System.out.println("Update a product");
                        System.out.println("\nSearch for a product by ID");
                        int toUpdateID = Integer.parseInt(in.nextLine());
                        FoodProduct toUpdateFP = dao.findProduct(toUpdateID);
                        if (toUpdateFP != null) {
                            System.out.println(toUpdateFP);
                            System.out.println("Enter product new SKU:");
                            String enteredUpdatedSKU = in.nextLine();
                            System.out.println("Enter product new category:");
                            String enteredUpdatedCategory = in.nextLine();
                            System.out.println("Enter product new description:");
                            String enteredUpdatedDescription = in.nextLine();
                            String enteredUpdatedExpiryDate;
                            do {
                                System.out.println("Enter product new expiry Date (yyyy-MM-dd): ");
                                enteredUpdatedExpiryDate = in.nextLine();
                            } while (!MyHTTPHandler.isValidDateFormat(enteredUpdatedExpiryDate));

                            System.out.println("Enter product new price:");
                            int enteredUpdatedPrice = Integer.parseInt(in.nextLine());
                            if (dao.updateProduct(new FoodProduct(toUpdateID, enteredUpdatedSKU,
                                    enteredUpdatedDescription, enteredUpdatedCategory, enteredUpdatedExpiryDate, enteredUpdatedPrice)))
                                System.out.println("Food product updated successfully!");
                            else
                                System.err.println("Failed to update product!");
                        } else
                            System.err.println("No such product exists!");

                        break;
                    case "5":
                        System.out.println("\nEnter product ID to delete: ");
                        int toDeleteID = Integer.parseInt(in.nextLine());
                        FoodProduct toDeleteFP = dao.findProduct(toDeleteID);
                        if (toDeleteFP != null) {
                            if (dao.deleteProduct(toDeleteID))
                                System.out.println("Food deleted successfully!");
                            else
                                System.out.println("Failed to delete product!");
                        } else
                            System.err.println("No such product exists!");
                        break;
                }
            } while (!selection.equals("6"));
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}