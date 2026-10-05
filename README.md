# MedPharm Express

Aplicación full-stack para la gestión de recetas médicas y despacho de farmacia.

## Estructura

- `medpharm-backend`: Spring Boot 4, Spring Security, JWT, Spring Data JPA y H2.
- `medpharm-frontend`: Angular standalone, formularios reactivos, Signals, guard e interceptor HTTP.

## Ejecución

### Backend

```powershell
cd medpharm-backend
.\mvnw.cmd spring-boot:run
```

API: `http://localhost:8080`  
Consola H2: `http://localhost:8080/h2-console`  
JDBC URL: `jdbc:h2:mem:medpharmdb`

### Frontend

```powershell
cd medpharm-frontend
npm install
npm start
```

Aplicación: `http://localhost:4200`

## Credenciales de prueba

| Usuario | Contraseña | Rol |
|---|---|---|
| `medico` | `medico123` | MEDICO |
| `farmaceutico` | `farmacia123` | FARMACEUTICO |

## Endpoints principales

- `POST /api/v1/auth/login`
- `GET /api/v1/recetas`
- `GET /api/v1/recetas?estado=PENDIENTE`
- `POST /api/v1/recetas`
- `PATCH /api/v1/recetas/{id}/estado`
- `GET /api/v1/medicamentos`

## Depuración JWT, 401 y CORS

Una petición a `GET /api/v1/recetas` sin sesión no incluye la cabecera `Authorization`. Como el endpoint está protegido por Spring Security, el servidor rechaza la petición con `401 Unauthorized` o `403 Forbidden`, según el flujo de autenticación activo. En esta reproducción el navegador registró `403`. Una petición desde otro origen también puede activar un preflight `OPTIONS`; la configuración CORS permite el origen `http://localhost:4200`, los métodos utilizados y la cabecera `Authorization`.

En la consola del navegador se observó: `Failed to load resource: the server responded with a status of 403 ()`. En Network, la solicitud corresponde a `GET http://localhost:8080/api/v1/recetas` y no contiene `Authorization`.

El `authInterceptor` se registra en `app.config.ts` mediante `provideHttpClient(withInterceptors([authInterceptor]))`. Antes de enviar cada petición, obtiene el token desde `AuthService` y clona la solicitud con:

```http
Authorization: Bearer <jwt>
```

El filtro `AuthTokenFilter` valida firma y expiración, recupera el usuario y establece el contexto de seguridad. Para reproducir el error solicitado, se puede retirar temporalmente el interceptor, iniciar sesión, visitar el tablero y observar el `401` en Network; luego se restaura el registro del interceptor. La captura visual debe guardarse como `docs/error_jwt_401.png` después de ejecutar esa reproducción en el navegador.

## Validación ejecutada

- `medpharm-backend`: `./mvnw.cmd test -q`
- `medpharm-frontend`: `npm run build`
