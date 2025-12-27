# API Test Examples for User CRUD Operations

## Base URL
http://localhost:8080/api/users

---

## 1. CREATE - Create a new user
**Request:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "phone": "13800138000",
    "age": 25
  }'
```

---

## 2. READ - Get all users
**Request:**
```bash
curl -X GET http://localhost:8080/api/users
```

---

## 3. READ - Get user by ID
**Request:**
```bash
curl -X GET http://localhost:8080/api/users/1
```

---

## 4. READ - Get user by username
**Request:**
```bash
curl -X GET http://localhost:8080/api/users/username/john_doe
```

---

## 5. READ - Get users by page
**Request:**
```bash
curl -X GET "http://localhost:8080/api/users/page?pageNum=1&pageSize=10"
```

---

## 6. READ - Search users by condition
**Request:**
```bash
curl -X GET "http://localhost:8080/api/users/search?username=john&status=1"
```

---

## 7. UPDATE - Update user
**Request:**
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john.doe@example.com",
    "phone": "13900139000",
    "age": 26
  }'
```

---

## 8. DELETE - Delete user by ID
**Request:**
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

---

## Test Workflow Example

### Step 1: Create users
```bash
# Create user 1
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username": "alice", "email": "alice@example.com", "age": 28}'

# Create user 2
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username": "bob", "email": "bob@example.com", "age": 30}'
```

### Step 2: Query all users
```bash
curl -X GET http://localhost:8080/api/users
```

### Step 3: Update a user
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"username": "alice", "email": "alice.updated@example.com", "age": 29}'
```

### Step 4: Search users
```bash
curl -X GET "http://localhost:8080/api/users/search?username=alice"
```

### Step 5: Delete a user
```bash
curl -X DELETE http://localhost:8080/api/users/2
```
