import json
import bcrypt

def load_json(file_path):
    with open(file_path, 'r') as file:
        return json.load(file)

def generate_insert_statements(data, table_name):
    insert_statements = []
    for item in data:
        columns = ', '.join(item.keys())
        values = ', '.join(f"'{str(value).replace('\'', '\'\'')}'" if isinstance(value, str) else str(value) for value in item.values())
        insert_statements.append(f"INSERT INTO {table_name} ({columns}) VALUES ({values});")
    return insert_statements

def generate_role_statements(roles):
    role_statements = []
    avail_perm_statements = []
    perm_statements = []

    for role in roles:
        role_stmt = {
            "name": role['name'],
            "description": role['description']
        }
        role_statements.append(role_stmt)

        for perm in role['permissions']:
            avail_perm_statements.append({
                "name": perm,
            })
            perm_stmt = {
                "role": role['name'],
                "permission": perm
            }
            perm_statements.append(perm_stmt)
    
    return (
        generate_insert_statements(role_statements, 'available_roles'),
        generate_insert_statements(avail_perm_statements, 'available_permissions'),
        generate_insert_statements(perm_statements, 'permissions_t')
    )

def generate_user_statements(users):
    user_statements = []
    role_statements = []
    
    for user in users:
        hashed_pw = bcrypt.hashpw(user['text_pw'].encode('utf-8'), bcrypt.gensalt()).decode('utf-8')
        user_stmt = {
            "email": user['email'],
            "name": user['name'],
            "hashed_pw": hashed_pw
        }
        user_statements.append(user_stmt)

        user_id = "(SELECT id FROM user_t WHERE email = " + user['email'] + ")"

        # Insert roles
        for role in user['roles']:
            role_statements.append({
                "role": role,
                "user_id": user_id
            })

    return generate_insert_statements(user_statements, 'user_t'), generate_insert_statements(role_statements, 'roles')

def generate_product_statements(products):
    product_statements = []
    image_statements = []
    property_statements = []
    category_statements = []

    for product in products:
        product_stmt = {
            "name": product['name'],
            "description": product['description'],
            "price": product['price'],
            "quantity": product['quantity'],
            "removed": False
        }
        product_statements.append(product_stmt)

        # TODO: Make sure name is unique
        product_id = "(SELECT id FROM products WHERE name = " + product['name'] + ")"

        # Insert images
        for image_url in product['images']:
            image_statements.append({
                "image_url": image_url,
                "product_id": product_id
            })

        # Insert properties
        for prop in product['properties']:
            property_statements.append({
                "key": prop['key'],
                "value": prop['value'],
                "product_id": product_id
            })

        # Insert categories
        for category in product['category']:
            category_statements.append({
                "product_id": product_id,
                "category": category
            })

    return (
        generate_insert_statements(product_statements, 'products'),
        generate_insert_statements(image_statements, 'product_images'),
        generate_insert_statements(property_statements, 'product_properties'),
        generate_insert_statements(category_statements, 'product_categories')
    )

def main():
    roles = load_json('../data/roles.json')
    products = load_json('../data/products.json')
    users = load_json('../data/users.json')

    avail_roles_inserts, avail_perms_inserts, perms_inserts = generate_role_statements(roles)
    user_inserts, role_inserts = generate_user_statements(users)
    product_inserts, image_inserts, property_inserts, category_inserts = generate_product_statements(products)

    all_statements = (
        avail_roles_inserts + avail_perms_inserts + perms_inserts +
        user_inserts + role_inserts +
        product_inserts + image_inserts + property_inserts + category_inserts
    )

    # Print all statements
    for statement in all_statements:
        print(statement)

if __name__ == "__main__":
    main()
