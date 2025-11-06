# 🔧 Fix: Error al crear pedidos (Frontend-Backend)

## Problema Identificado

Al presionar el botón de "Crear pedido" en el frontend, se recibía un **Internal Server Error 500** desde el backend.

## Causa Raíz

El modelo de datos del **frontend NO coincidía con el DTO del backend**:

### ❌ Modelo Anterior (Incorrecto)

```typescript
// Frontend enviaba:
{
  table: 1,
  responsible: "1001",  // ❌ Campo NO existe en el backend
  clientOrders: [...]
}
```

### ✅ Modelo Esperado por el Backend

```java
// Backend espera:
{
  "table": 1,
  "clientOrders": [...]
}
// El "responsible" se obtiene del usuario autenticado, NO del request
```

## Archivos Modificados

### 1. `/aros_client/src/app/shared/models/dto/orders/create-order-request.model.ts`

**Cambio**: Eliminado el campo `responsible` del modelo

```typescript
export interface CreateOrderRequest {
  table: number;
  // responsible?: string | null;  ❌ ELIMINADO
  clientOrders: {
    details: {
      product: number;
      quantity: number;
      observations: string;
      subProducts: number[];
    }[];
  }[];
}
```

### 2. `/aros_client/src/app/features/admin/manage/orders-create/order-creation-form.ts`

**Cambio**: Removida la línea que asignaba `responsible: "1001"`

```typescript
const request: CreateOrderRequest = {
  table: Number(raw.table),
  // responsible: "1001",  ❌ ELIMINADO
  clientOrders: raw.clientOrders.map((co) => ({
    // ...
  })),
};
```

## Explicación Técnica

### Flujo en el Backend

```java
// Controller: OrderController.java
@PostMapping(path = "/create")
public ResponseEntity<?> create(
    @Valid @RequestBody CreateOrderRequest request,
    @AuthenticationPrincipal UserDetailsAdapter authentication) {

    // El 'responsible' se obtiene del usuario autenticado:
    this.createOrderUseCase.execute(
        request.toInput(),
        authentication.getUser()  // ← Aquí se obtiene el responsable
    );
}
```

El backend **automáticamente** obtiene el usuario responsable desde el token de autenticación JWT, por lo que NO es necesario (ni permitido) enviarlo en el body del request.

## Estructura Correcta del Request

### Request JSON que envía el frontend:

```json
{
  "table": 1,
  "clientOrders": [
    {
      "details": [
        {
          "product": 5,
          "quantity": 2,
          "observations": "Sin cebolla",
          "subProducts": [101, 102]
        }
      ]
    }
  ]
}
```

### Validaciones del Backend:

- `table`: **Obligatorio**, debe ser un número positivo (`@NotNull @Positive Long`)
- `clientOrders`: **Obligatorio**, debe tener al menos un elemento (`@NotEmpty`)
- `details`: Cada clientOrder debe tener al menos un detail
- `product`: **Obligatorio**, debe ser un número positivo
- `quantity`: **Obligatorio**, debe ser un número positivo
- `observations`: **Opcional**, puede ser null o string vacío
- `subProducts`: **Opcional**, puede ser array vacío `[]`

## Verificación

### ✅ Para verificar que funciona:

1. **Iniciar el backend**:

   ```bash
   cd aros_api
   ./run.sh
   ```

2. **Iniciar el frontend**:

   ```bash
   cd aros_client
   ng serve
   ```

3. **Probar crear un pedido**:
   - Ir a la página de creación de pedidos
   - Seleccionar una mesa
   - Agregar al menos un producto
   - Presionar "Crear pedido"
   - Debería mostrar mensaje de éxito ✅

### 🔍 Logs para debugging:

En la consola del navegador deberías ver:

```
OrderCreationForm.submit called
Form status: VALID
CreateOrder request payload: { table: 1, clientOrders: [...] }
CreateOrder success
```

En los logs del backend deberías ver:

```
POST /api/orders/create - Creating new order for table 1 with N products
POST /api/orders/create - Order created successfully for table 1
```

## Notas Adicionales

- El campo `responsible` se asigna automáticamente desde el token JWT del usuario autenticado
- No es necesario modificar nada en el backend para este fix
- El frontend ahora envía exactamente el formato que espera el backend

---

**Estado**: ✅ **Problema Resuelto**  
**Fecha**: 5 de noviembre de 2025  
**Archivos modificados**: 2 archivos en el frontend
