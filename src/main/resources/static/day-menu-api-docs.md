# Day Menu API Documentation

## Overview

The Day Menu system allows restaurants to create daily menus that are composed of multiple categories (Sopa, Principio, Proteína, Bebida) with various options in each category. Customers can order a day menu by selecting exactly one option from each available category.

## Data Structure

### Day Menu Entity
A Day Menu is a special type of product (`DaymenuEntity` extends `ProductEntity`) with the following characteristics:

- **Single Price**: The day menu has a fixed price regardless of the selections
- **Date-Specific**: Each day menu is created for a specific date
- **Categorized Options**: Contains multiple categories with product options

### Database Schema
```
Daymenu (Product)
├── DayMenuCategory (Categories within the menu)
│   └── Product (Available options for this category)
```

## API Endpoints

### Get Current Day Menu
```
GET /api/daymenu/current
```

**Description**: Retrieves the day menu for the current date with all available categories and options.

**Authentication**: ADMIN or WAITER role required

**Response**:
```json
{
  "id": 27,
  "name": "Menú del Día Tradicional",
  "description": "Menú completo: sopa + principio + proteína + bebida",
  "price": 25000.0,
  "creation": "2024-01-15",
  "subProducts": [
    {
      "category": {
        "id": 1,
        "name": "Sopas"
      },
      "products": [
        {
          "id": 1,
          "name": "Sopa de Pollo",
          "price": 12000.0
        },
        {
          "id": 2,
          "name": "Crema de Espinacas", 
          "price": 10000.0
        }
      ],
      "position": 1
    },
    {
      "category": {
        "id": 2,
        "name": "Principios"
      },
      "products": [
        {
          "id": 4,
          "name": "Arroz Blanco",
          "price": 4000.0
        },
        {
          "id": 5,
          "name": "Papa Salada",
          "price": 5000.0
        },
        {
          "id": 6, 
          "name": "Lentejas",
          "price": 8000.0
        }
      ],
      "position": 2
    }
  ]
}
```

### Create Order with Day Menu
```
POST /api/orders/create
```

**Description**: Creates a new order that can include day menu items with specific selections.

**Authentication**: USER role required

**Request Body**:
```json
{
  "table": 1,
  "clientOrders": [
    {
      "details": [
        {
          "product": 27,
          "quantity": 1,
          "observations": "Sin picante",
          "subProducts": [2, 5, 8, 13]
        },
        {
          "product": 19,
          "quantity": 1,
          "observations": "",
          "subProducts": []
        }
      ]
    }
  ]
}
```

**Day Menu Selection Rules**:
- `product`: Must be the ID of a day menu product
- `subProducts`: Array of product IDs representing the customer's selections
- Must include exactly one selection per category available in the day menu
- All selections must be valid options within their respective categories

## Validation Rules

### Day Menu Selection Validation
When ordering a day menu, the system validates:

1. **Required Categories**: All categories in the day menu must have exactly one selection
2. **Valid Options**: All selected products must be available in their respective categories
3. **No Duplicates**: Only one product per category is allowed

### Example Valid Selections
For a day menu with categories:
- Sopas (IDs: 1, 2)
- Principios (IDs: 4, 5, 6) 
- Proteínas (IDs: 8, 9, 10, 11)
- Bebidas (IDs: 13, 14, 15, 16)

**Valid**: `[2, 5, 8, 13]` (one from each category)
**Invalid**: `[2, 5]` (missing categories)
**Invalid**: `[2, 5, 8, 13, 14]` (multiple from same category)

## Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Missing selections for required categories. Required: [1, 2, 3, 4], Selected categories: [1, 2]"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-15T10:30:00Z", 
  "status": 404,
  "error": "Not Found",
  "message": "No day menu found for today"
}
```

## Example Workflow

### 1. Get Available Day Menu
```bash
curl -X GET "http://localhost:8080/api/daymenu/current" \
  -H "Authorization: Bearer <token>"
```

### 2. Create Order with Day Menu Selections
```bash
curl -X POST "http://localhost:8080/api/orders/create" \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "table": 1,
    "clientOrders": [
      {
        "details": [
          {
            "product": 27,
            "quantity": 1,
            "observations": "Sin picante",
            "subProducts": [2, 5, 8, 13]
          }
        ]
      }
    ]
  }'
```

## Data Model

### DayMenuCategoryEntity
- `id`: Unique identifier
- `daymenu`: Reference to parent day menu
- `category`: The category (Sopas, Principios, etc.)
- `products`: Available products in this category
- `position`: Display order in the menu

### DayMenuSelectionEntity (Stored when ordered)
- `id`: Unique identifier  
- `orderDetail`: Reference to the order detail
- `category`: The category selected
- `selectedProduct`: The specific product chosen

## Testing Data

The system includes test data with:
- **Day Menu Traditional** (ID: 27): $25,000 COP
  - Sopas: Sopa de Pollo (1), Crema de Espinacas (2)
  - Principios: Arroz Blanco (4), Papa Salada (5), Lentejas (6)
  - Proteínas: Pechuga (8), Carne Asada (9), Pescado (10), Cerdo (11)
  - Bebidas: Jugo Naranja (13), Limonada (14), Coca Cola (15), Agua (16)

- **Day Menu Special** (ID: 28): $35,000 COP
  - Premium options for next day