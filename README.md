# hi1031lab1

## Innehållsförteckning

## Översikt

Repo för labb 1 i HI1031 med java enterprise.

```mermaid
graph
    client(client) --> tomcat(tomcat) <--> db[(db)]
    client -->|fetches static content| cdn(cdn)
    tomcat -->|uploads images| cdn
```

## UML diagram

TODO:

```mermaid
classDiagram
    class DBConnection {
        - Connection connection
        + DBConnection()
        + getConnection() Connection
        + closeConnection() void
    }

    class DBConnectionManager {
        - List~DBConnection~ connections
        + getConnection() DBConnection
        + releaseConnection(DBConnection) void
    }

    class Campaign {
        - int id
        - String name
        - String description
        + Campaign(int, String, String)
        + getId() int
        + getName() String
        + getDescription() String
    }

    class Product {
        - int id
        - String name
        - double price
        + Product(int, String, double)
        + getId() int
        + getName() String
        + getPrice() double
    }

    class ProductDAO {
        - DBConnection dbConnection
        + saveProduct(Product) boolean
        + getProductById(int) Product
    }

    class ProductUI {
        + displayProduct(Product) void
    }

    DBConnectionManager "1" *--> "0..*" DBConnection : manages
    DBConnection <-- ProductDAO : uses
    ProductDAO <-- Product : manages

```

## DB

```mermaid
erDiagram
    user_t {
        int id PK
        varchar email
        varchar name
        varchar hashed_pw
    }

    available_permissions {
        varchar name PK
        varchar description
    }

    available_roles {
        varchar name PK
        varchar description
    }

    permissions_t {
        varchar role FK, PK
        varchar permission FK, PK
    }

    roles {
        varchar role FK, PK
        int user_id FK, PK
    }

    available_categories {
        varchar name PK
        varchar description
    }

    products {
        int id PK
        varchar name
        varchar description
        decimal price
        int quantity
        boolean removed
    }

    product_images {
        varchar image_url
        int product_id FK
    }

    product_properties {
        varchar key PK
        varchar value
        int product_id FK, PK
    }

    product_categories {
        int product_id FK, PK
        varchar category FK, PK
    }

    available_status {
        varchar name PK
    }

    orders {
        int id PK
        timestamp created_at
        timestamp delivered_at
        varchar delivery_address
        int customer_id FK
    }

    order_status {
        int order_id FK, PK
        varchar status FK
        timestamp timestamp PK
    }

    ordered_products {
        int product_id FK, PK
        int order_id FK, PK
        decimal product_price
    }

    user_t ||--o{ roles : "has role"
    available_roles ||--o{ roles : "describes"
    available_roles ||--o{ permissions_t : "has permission"
    available_permissions ||--o{ permissions_t : "describes"
    products ||--o{ product_images : "has image"
    products ||--o{ product_properties : "has property"
    products ||--o{ product_categories : "belongs to category"
    available_categories ||--o{ product_categories : "describes"
    orders ||--o{ ordered_products : "contains product"
    user_t ||--o{ orders : "places order"
    products ||--o{ ordered_products : "is in order"
    orders ||--o{ order_status : "has status"
    available_status ||--o{ order_status : "describes status"

```
