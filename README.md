# hi1031lab1

Det här repot är skapat för labb 1 i kursen HI1031 och är byggt med hjälp av Java Enterprise (Jakarta) och trelagersarkitektur med MVC. Applikationen hanterar produkter, användare (med olika roller) och beställningar genom en tydligt separerad arkitektur där logik, dataåtkomst och användargränsnitt hålls åtskilda.

## Köra applikationen

Om du har [`docker`](https://docker.com/) installerat kan du köra applikationen genom att klona repot och sedan använda docker compose för att köra igång tomcatservern och dbn.

```bash
git clone https://github.com/Phillezi/hi1031lab1.git && \
cd hi1031lab1 && \
docker compose up
```

Webbshoppen nås då på [`localhost:8080`](http://localhost:8080).

## UML diagram

TODO:

```mermaid
classDiagram
namespace bo {
    class AuthMiddleware {
        +static boolean userHasOneOf(UserDTO user, PermissionDTO... permissions)
        +static boolean userHasOneOf(UserDTO user, RoleDTO... roles)
        +static boolean userHasOneOf(UserDTO user, Permission... permissions)
        +static boolean userHasOneOf(UserDTO user, Role... roles)
        +static boolean userHasOneOf(User user, Permission... permissions)
        +static boolean userHasOneOf(User user, Role... roles)
    }

    class Order {
        +Integer id
        +Timestamp created
        +Timestamp delivered
        +String deliveryAddress
        +User customer
        +List~Product~ products
        +List~Status~ statuses
        +Order(OrderDTO order)
        +OrderDTO toDTO()
        +OrderDAO toDAO()
    }

    class Status {
        +String status
        +Timestamp timestamp
        +Status(StatusDTO status)
        +StatusDTO toDTO()
        +StatusDAO toDAO()
    }

    class Category {
        +String name
        +String description
        +Category(CategoryDTO category)
        +CategoryDTO toDTO()
        +CategoryDAO toDAO()
    }

    class Product {
        +Integer id
        +String name
        +String description
        +double price
        +int quantity
        +boolean removed
        +List~Category~ categories
        +List~String~ images
        +List~Property~ properties
        +Product(ProductDTO product)
        +ProductDTO toDTO()
        +ProductDAO toDAO()
    }

    class Property {
        +String key
        +String value
        +Property(PropertyDTO property)
        +PropertyDTO toDTO()
        +PropertyDAO toDAO()
    }

    class Permission {
        +String name
        +Permission(PermissionDTO permission)
        +PermissionDTO toDTO()
        +PermissionDAO toDAO()
        +int compareTo(Permission p)
        +boolean equals(Object o)
    }

    class Role {
        +String name
        +List<Permission> permissions
        +Role(String name)
        +Role(RoleDTO role)
        +RoleDTO toDTO()
        +RoleDAO toDAO()
        +int compareTo(Role r)
        +boolean equals(Object o)
    }

    class User {
        +Integer id
        +String name
        +String email
        +String password
        +List<Role> roles
        +List<Permission> permissions
        +User(UserDTO user)
        +UserDTO toDTO()
        +UserDAO toDAO()
    }

    class OrderService {
        +OrderDTO createOrder(UserDTO user, UserDTO customer, String deliveryAddress, List<ProductDTO> products)
        +List<OrderDTO> getAllOrders(UserDTO user)
        +Optional<OrderDTO> getOrderById(UserDTO user, int id)
        +List<OrderDTO> getOrdersWithStatus(UserDTO user, String... statuses)
    }

    class StatusService {
        +void setOrderStatus(int orderId, String status, UserDTO user)
    }

    class CategoryService {
        +List<String> getAvailableCategories()
    }

    class ProductService {
        +List<ProductDTO> getProducts()
        +List<ProductDTO> getProducts(List<Integer> ids)
        +ProductDTO getProductById(int id)
        +void updateProduct(UserDTO user, ProductDTO productToUpdate)
        +ProductDTO createProduct(UserDTO user, ProductDTO product)
    }

    class PermissionService {
        +List<String> getAvailablePermissions()
    }

    class RoleService {
        +List<String> getAvailableRoles()
        +RoleDTO getRole(String name)
    }

    class UserService {
        +UserDTO createUser(UserDTO user)
        +List<UserDTO> getUsers(UserDTO user)
        +UserDTO login(UserDTO user)
        +UserDTO getUserById(UserDTO user, int userId)
        +void updateUser(UserDTO user, UserDTO userToUpdate)
        +void deleteUserById(UserDTO user, int id)
    }

    class PermissionException {
        +PermissionException(String message)
    }

    class ServiceException {
        +ServiceException(String message)
    }

    }

    AuthMiddleware --> UserDTO
    AuthMiddleware --> PermissionDTO
    AuthMiddleware --> RoleDTO
    AuthMiddleware --> User
    AuthMiddleware --> Permission
    AuthMiddleware --> Role

    Order --> User
    Order --> Product
    Order --> Status
    Order --> OrderDTO
    Order --> OrderDAO
    Order --> Timestamp

    Status --> StatusDTO
    Status --> StatusDAO
    Status --> Timestamp

    Category --> CategoryDTO
    Category --> CategoryDAO

    Product --> ProductDTO
    Product --> ProductDAO
    Product --> Category
    Product --> Property
    Product --> String

    Property --> PropertyDTO
    Property --> PropertyDAO

    Permission --> PermissionDTO
    Permission --> PermissionDAO

    Role --> RoleDTO
    Role --> RoleDAO
    Role --> Permission

    User --> UserDTO
    User --> UserDAO
    User --> Role
    User --> Permission

    OrderService --> UserDTO
    OrderService --> ProductDTO
    OrderService --> OrderDTO
    OrderService --> OrderDAO
    OrderService --> PermissionDTO
    OrderService --> Status
    OrderService --> ServiceException
    OrderService --> PermissionException

    StatusService --> UserDTO
    StatusService --> Permission
    StatusService --> PermissionException
    StatusService --> OrderDAO
    StatusService --> StatusDAO

    CategoryService --> DAOException
    CategoryService --> ServiceException
    CategoryService --> CategoryDAO

    ProductService --> ProductDAO
    ProductService --> Product
    ProductService --> ProductDTO
    ProductService --> UserDTO
    ProductService --> DAOException
    ProductService --> PermissionException
    ProductService --> ServiceException
    ProductService --> AuthMiddleware
    ProductService --> Role

    PermissionService --> PermissionDAO
    PermissionService --> DAOException
    PermissionService --> ServiceException

    RoleService --> RoleDAO
    RoleService --> DAOException
    RoleService --> ServiceException
    RoleService --> RoleDTO

    UserService --> UserDAO
    UserService --> DAOException
    UserService --> ServiceException
    UserService --> PermissionException
    UserService --> Role
    UserService --> UserDTO
    UserService --> User

    PermissionException --> RuntimeException

    ServiceException --> RuntimeException

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
