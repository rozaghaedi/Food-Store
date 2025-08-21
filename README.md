# 🍕 Food Store Management System

**A Java-based web application for managing food products with dual console and web interfaces**

A comprehensive food inventory management system built with Java, featuring user authentication, product CRUD operations, shopping cart functionality, and expiry date tracking. The application provides both a command-line interface for administrative tasks and a modern web interface for end users.

## 🌟 Features

### 🔐 User Management
- **User Registration** - Create new customer accounts with business details
- **User Authentication** - Secure login system with SHA-256 password hashing
- **Session Management** - Maintain user sessions across web requests

### 📦 Product Management
- **CRUD Operations** - Create, Read, Update, and Delete food products
- **Category Filtering** - Browse products by categories
- **Search Functionality** - Find products by description
- **Expiry Date Tracking** - Filter products by expiration status (expired/non-expired)
- **SKU Management** - Track products with stock keeping units

### 🛒 Shopping Experience
- **Shopping Cart** - Add multiple products to cart
- **Price Calculation** - Automatic total price computation
- **Product Browsing** - Intuitive web interface for product discovery

### 💻 Dual Interface
- **Web Interface** - Modern HTML-based UI with FreeMarker templates
- **Console Interface** - Command-line interface for administrative tasks

## 🛠️ Built With

- **Backend:**
  - Java (JDK 8+)
  - SQLite Database
  - Java HTTP Server (com.sun.net.httpserver)
  - JDBC for database connectivity

- **Frontend:**
  - HTML5 & CSS3
  - FreeMarker Template Engine
  - Responsive Web Design

- **Security:**
  - SHA-256 Password Hashing
  - Session-based Authentication

## 🏗️ Architecture

```
src/main/java/org/example/
├── Main.java               Application entry point
├── MyHTTPHandler.java      HTTP request handler
├── WebController.java      Template rendering controller
├── FoodProduct.java        Product entity model
├── Customer.java           Customer entity model
├── Address.java            Address entity model
├── FoodProductDAO.java     Product data access layer
└── CustomersDAO.java       Customer data access layer
```

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK) 8 or higher**
- **SQLite** (included with Java)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/rozaghaedi/Food-Store.git
   cd Food-Store
   ```

2. **Set up the database**
   The application uses SQLite with the database file `identifier.sqlite`. The application will create the necessary tables automatically on first run.

3. **Compile the Java application**
   ```bash
   javac -cp . src/main/java/org/example/*.java
   ```

4. **Run the application**
   ```bash
   java -cp . org.example.Main
   ```

5. **Access the application**
   - **Web Interface:** Open your browser and navigate to `http://localhost:4567`
   - **Console Interface:** Use the command-line menu that appears in your terminal

## 📚 Usage

### Web Interface

1. **Register a new account** at `/register` with your business details
2. **Login** to access the product management system
3. **Browse products** at `/products` with filtering and search options
4. **Add products** using the "Add Product" feature (requires login)
5. **Update/Delete products** through the product management interface
6. **Use the shopping cart** to select and calculate total prices

### Console Interface

The console provides a menu-driven interface for:
- `[1]` List all products
- `[2]` Search for product by ID
- `[3]` Add a new product
- `[4]` Update a product by ID
- `[5]` Delete a product by ID
- `[6]` Exit application

### Database Schema

**Products Table (`food_products`):**
- `id` - Primary key
- `SKU` - Stock keeping unit
- `description` - Product description
- `category` - Product category
- `expiry_date` - Expiration date (YYYY-MM-DD)
- `price` - Product price

**Customers Table (`customers`):**
- `id` - Primary key
- `businessName` - Customer business name
- `telephoneNumber` - Contact number
- `username` - Login username
- `password` - Hashed password
- `address` - Foreign key to address table

## 🔧 Configuration

- **Server Port:** 4567 (configurable in `Main.java`)
- **Database:** `identifier.sqlite` (configurable in handler classes)
- **Templates:** Located in `/templates/` directory

## 🎯 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Home page (redirects to login if not authenticated) |
| GET | `/login` | Login page |
| GET | `/register` | Registration page |
| GET | `/products` | Product listing with filters |
| GET | `/add-product` | Add product form |
| GET | `/product/{id}/update` | Update product form |
| GET | `/product/{id}/delete` | Delete product confirmation |
| GET | `/cart` | Shopping cart page |
| GET | `/logout` | Logout and redirect |
| POST | `/login` | Process login |
| POST | `/register` | Process registration |
| POST | `/add-product` | Add new product |
| POST | `/update-product` | Update existing product |
| POST | `/delete-product` | Delete product |
| POST | `/cart` | Process cart items |

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines:
- Follow Java naming conventions
- Add JavaDoc comments for public methods
- Ensure proper exception handling
- Test both console and web interfaces


## 🚀 Future Enhancements

- Payment gateway integration
- Email notifications for expired products
- Advanced reporting and analytics
- REST API endpoints
- Mobile application support
- Inventory level tracking
- Multi-user role management



