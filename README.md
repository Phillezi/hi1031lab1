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

    namespace db {
    class OrderDAO {
        +Integer id
        +Timestamp created
        +Timestamp delivered
        +String deliveryAddress
        +UserDAO customer
        +List<ProductDAO> products
        +List<StatusDAO> statuses
        +OrderDAO(Order order)
        +static List<OrderDAO> getOrders()
        +static Optional<OrderDAO> getOrderById(int id)
        +static List<OrderDAO> getOrdersByCustomer(User customer)
        +static OrderDAO createOrder(Order order) throws DAOException
        +static List<OrderDAO> getOrdersByStatus(String... statuses) throws DAOException
        +static List<OrderDAO> getOrdersByStatus(Connection conn, String... statuses) throws DAOException
    }

    class StatusDAO {
        - String status
        - Timestamp timestamp
        + StatusDAO(String status, Timestamp timestamp)
        + static StatusDAO createStatus(int orderId, Status status)
        + static StatusDAO createStatus(int orderId, Status status, Connection conn)
        + static StatusDAO toDAO(ResultSet rs)
        + static List<StatusDAO> toDAOs(ResultSet rs)
        + Status toStatus()
    }

    class CategoryDAO {
        - String name
        - String description
        + CategoryDAO(String name, String description)
        + static List<String> getAvailableCategories()
        + Category toCategory()
    }

    class ProductDAO {
        +Integer id
        +String name
        +String description
        +double price
        +int quantity
        +boolean removed
        +List<CategoryDAO> categories
        +List<String> images
        +List<PropertyDAO> properties
        +static List<ProductDAO> getAllProducts() throws DAOException
        +static Optional<ProductDAO> getProductById(int id) throws DAOException
        +static ProductDAO createProduct(Product product)
        +static boolean updateProductQuantity(Product product, int quantity)
        +static int getProductQuantity(int productId) throws DAOException
        +static List<ProductDAO> getProductsByIds(List<Integer> ids) throws DAOException
        +static void updateProduct(Product product) throws DAOException
    }

    class PropertyDAO {
        +String key
        +String value
        +Property toProperty()
    }

    class PermissionDAO {
        +String name
        +static List<String> getAvailablePermissions()
        +Permission toPermission()
    }

    class RoleDAO {
        +String name
        +List<PermissionDAO> permissions
        +Role toRole()
        +static List<String> getAvailableRoles()
        +static RoleDAO getRole(String name)
    }

    class UserDAO {
        +Integer id
        +String name
        +String email
        +String password
        +List<RoleDAO> roles
        +List<PermissionDAO> permissions
        +static List<UserDAO> getUsers()
        +static Optional<UserDAO> getUserByid(int id)
        +static Optional<UserDAO> login(User credentials)
        +static UserDAO createUser(User user)
        +static void updateUser(User user)
        +static void deleteUserById(int id)
        +User toUser()
    }

    class DBUtil {
        +cleanUp(Connection conn, PreparedStatement stmt, ResultSet rs)
    }

    class DAOException {
        +DAOException(String message)
    }

    class DBConnection {
        +DBConnection(DBConnectionManager parent)
        +boolean getAutoCommit() throws SQLException
        +void setAutoCommit(boolean autoCommit) throws SQLException
        +void commit() throws SQLException
        +void rollback() throws SQLException
        +Statement createStatement() throws SQLException
        +PreparedStatement prepareStatement(String sql) throws SQLException
        +CallableStatement prepareCall(String sql) throws SQLException
        +String nativeSQL(String sql) throws SQLException
        +boolean isClosed() throws SQLException
        +DatabaseMetaData getMetaData() throws SQLException
        +void setReadOnly(boolean readOnly) throws SQLException
        +boolean isReadOnly() throws SQLException
        +void setCatalog(String catalog) throws SQLException
        +String getCatalog() throws SQLException
        +void setTransactionIsolation(int level) throws SQLException
        +int getTransactionIsolation() throws SQLException
        +SQLWarning getWarnings() throws SQLException
        +void clearWarnings() throws SQLException
        +Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException
        +PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
        +CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
        +Map<String, Class<?>> getTypeMap() throws SQLException
        +void setTypeMap(Map<String, Class<?>> map) throws SQLException
        +void setHoldability(int holdability) throws SQLException
        +int getHoldability() throws SQLException
        +Savepoint setSavepoint() throws SQLException
        +Savepoint setSavepoint(String name) throws SQLException
        +void rollback(Savepoint savepoint) throws SQLException
        +void releaseSavepoint(Savepoint savepoint) throws SQLException
        +Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
        +PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
        +CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
        +void setClientInfo(String name, String value) throws SQLClientInfoException
        +void setClientInfo(Properties properties) throws SQLClientInfoException
        +String getClientInfo(String name) throws SQLException
        +Properties getClientInfo() throws SQLException
        +Array createArrayOf(String typeName, Object[] elements) throws SQLException
        +Blob createBlob() throws SQLException
        +Clob createClob() throws SQLException
        +NClob createNClob() throws SQLException
        +SQLXML createSQLXML() throws SQLException
        +boolean isValid(int timeout) throws SQLException
        +void setSchema(String schema) throws SQLException
        +String getSchema() throws SQLException
        +void abort(Executor executor) throws SQLException
        +void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException
        +int getNetworkTimeout() throws SQLException
        +<T> T unwrap(Class<T> iface) throws SQLException
        +boolean isWrapperFor(Class<?> iface) throws SQLException
        +PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException
        +PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException
        +PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException
        +Struct createStruct(String typeName, Object[] attributes) throws SQLException
        +void close() throws SQLException
        +void closeConnection() throws SQLException
    }

    class DBConnectionManager {
        - static instance: DBConnectionManager
        - connectionPool: List<DBConnection>
        - usedConnections: List<DBConnection>
        - MAX_POOL_SIZE: int
        + DBConnectionManager()
        + static getInstance(): DBConnectionManager
        - initializePool(): void
        + getConnection(): Connection
        + releaseConnection(connection: Connection): void
        + closePool(): void
    }

    }

    StatusDAO --|> Status : uses
    StatusDAO --> DBConnectionManager : uses
    StatusDAO --> DAOException : throws
    StatusDAO --> Connection : uses
    StatusDAO --> PreparedStatement : uses
    StatusDAO --> ResultSet : uses

    CategoryDAO --> Category : converts to
    CategoryDAO --> DBConnectionManager : uses
    CategoryDAO --> DAOException : throws
    CategoryDAO --> Connection : uses
    CategoryDAO --> PreparedStatement : uses
    CategoryDAO --> ResultSet : uses

    ProductDAO --> "0..*" CategoryDAO : contains
    ProductDAO --> "0..*" PropertyDAO : contains
    ProductDAO --> "0..*" String : contains images
    ProductDAO --> "1" Product : converts to
    Product --> "0..*" Category : has
    Product --> "0..*" Property : has

    PropertyDAO --> Property : converts to

    PermissionDAO --> Permission : converts to
    PermissionDAO --> DAOException : throws
    PermissionDAO --> DBConnectionManager : uses

    RoleDAO --> Role : converts to
    RoleDAO --> PermissionDAO : contains
    RoleDAO --> DAOException : throws
    RoleDAO --> DBConnectionManager : uses

    UserDAO --> RoleDAO : has
    UserDAO --> PermissionDAO : has
    UserDAO --> User : converts to
    RoleDAO --> User : has
    PermissionDAO --> User : grants

    DAOException --> RuntimeException : extends

    DBConnection --> DBConnectionManager : uses
    DBConnection ..> Connection : implements

    DBConnectionManager "1" -- "0..*" DBConnection : manages >

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
