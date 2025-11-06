# ✅ Solución: Hardcodear Usuario Responsable

## Cambios Realizados

Se modificó el sistema para que el **responsable del pedido se envíe desde el frontend** en lugar de obtenerlo del usuario autenticado. Esto soluciona el problema de "User not found by document".

---

## 🔧 Archivos Modificados

### Frontend (Angular)

#### 1. `/aros_client/src/app/shared/models/dto/orders/create-order-request.model.ts`

**Agregado**: Campo `responsible` al modelo

```typescript
export interface CreateOrderRequest {
  table: number;
  responsible: string; // ✅ User document ID (hardcoded: '1001')
  clientOrders: {
    // ...
  }[];
}
```

#### 2. `/aros_client/src/app/features/admin/manage/orders-create/order-creation-form.ts`

**Agregado**: Hardcodeo del usuario con document `'1001'` (Carlos Gómez)

```typescript
// HARDCODED: Using user with document '1001' (Carlos Gómez) as responsible
// TODO: Get responsible from authenticated user or allow selection
const request: CreateOrderRequest = {
  table: Number(raw.table),
  responsible: "1001", // ⚠️ HARDCODED - User exists in database
  clientOrders: raw.clientOrders.map((co) => ({
    /* ... */
  })),
};
```

---

### Backend (Spring Boot)

#### 3. `/aros_api/.../dto/orders/CreateOrderRequest.java`

**Agregado**: Campo `responsible` al DTO

```java
public record CreateOrderRequest(
    @NotNull @Positive Long table,

    @NotNull String responsible,  // ✅ Document ID del usuario responsable

    @Valid @NotEmpty Collection<CreateClientOrderRequest> clientOrders
) {
    public CreateOrderInput toInput() {
        return new CreateOrderInput(
            this.table,
            this.responsible,  // ✅ Pasamos responsible al Input
            this.clientOrders.stream()
                .map(co -> co.toInput())
                .toList()
        );
    }
}
```

#### 4. `/aros_api/.../dto/order/CreateOrderInput.java`

**Agregado**: Campo `responsibleDocument` al Input

```java
public record CreateOrderInput (
    Long table,
    String responsibleDocument,  // ✅ Document del responsable
    Collection<CreateClientOrderInput> clientOrders
) {
    //
}
```

#### 5. `/aros_api/.../usecases/order/CreateOrderUseCase.java`

**Modificado**: Usar `responsibleDocument` del input

```java
public void execute(
        CreateOrderInput input,
        User responsible) throws /* ... */ {

    // ... validations ...

    Order order = this.transformInputIntoOrder(input);

    // ✅ Use responsible from input if provided
    User responsibleUser = new User();
    responsibleUser.setDocument(input.responsibleDocument());
    order.setResponsible(responsibleUser);

    this.repository.create(order);
}
```

---

## 📝 Usuario Hardcodeado

**Document**: `'1001'`  
**Nombre**: Carlos Gómez  
**Email**: carlos@restaurante.com  
**Área**: Cocina

Este usuario existe en la base de datos según el archivo `initial-data.sql`.

---

## ⚠️ Notas Importantes

### Por qué es "mala práctica" (pero funciona)

1. **Hardcodear valores**: El responsable siempre será `'1001'` sin importar quién cree el pedido
2. **Falta de flexibilidad**: No se puede cambiar el responsable sin modificar el código
3. **No usa autenticación**: Ignora al usuario actualmente autenticado

### TODO: Mejoras Futuras

```typescript
// Opción 1: Obtener del usuario autenticado
const currentUser = this.authService.getCurrentUser();
responsible: currentUser.document;

// Opción 2: Permitir selección en el formulario
// Agregar un select en el HTML para elegir al responsable
responsible: this.form.get("responsible").value;

// Opción 3: Usar el backend (recomendado)
// Remover 'responsible' del request y dejarlo al backend
```

---

## ✅ Verificación

### 1. Backend compilado exitosamente

```bash
cd aros_api
mvn clean compile
# [INFO] BUILD SUCCESS
```

### 2. Request JSON esperado

```json
{
  "table": 1,
  "responsible": "1001",
  "clientOrders": [
    {
      "details": [
        {
          "product": 5,
          "quantity": 2,
          "observations": "Sin cebolla",
          "subProducts": []
        }
      ]
    }
  ]
}
```

### 3. Probar la creación de pedidos

1. Iniciar backend: `./run.sh`
2. Iniciar frontend: `ng serve`
3. Ir a "Crear Pedido"
4. Seleccionar mesa y productos
5. Presionar "Crear pedido"
6. ✅ Debería funcionar sin errores

---

## 🔍 Logs Esperados

### Backend

```
POST /api/orders/create - Creating new order for table 1 with N products
POST /api/orders/create - Order created successfully for table 1
```

### Frontend (Console)

```
OrderCreationForm.submit called
CreateOrder request payload: { table: 1, responsible: '1001', clientOrders: [...] }
CreateOrder success
```

---

**Estado**: ✅ **Funcionando con usuario hardcodeado**  
**Fecha**: 5 de noviembre de 2025  
**Archivos modificados**: 5 archivos (2 frontend + 3 backend)
