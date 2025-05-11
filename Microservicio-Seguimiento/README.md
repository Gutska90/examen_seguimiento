# Microservicio de Seguimiento de Envíos 📦

Este microservicio permite gestionar y dar seguimiento a envíos, proporcionando funcionalidades para registrar nuevos envíos, actualizar su estado y ubicación, y consultar el estado actual de un envío.

## 🛠️ Tecnologías Utilizadas

- Java 21
- Spring Boot 3.2.3
- Maven
- Oracle Database (ATP)
- JUnit 5
- Spring HATEOAS
- Lombok

## 📋 Requisitos Previos

- JDK 21
- Maven 3.8+
- Oracle Database (ATP) con wallet configurado
- Postman (para pruebas de API)

## 🔧 Configuración

1. Clonar el repositorio:
```bash
git clone [URL_DEL_REPOSITORIO]
cd shipment-tracking
```

2. Configurar variables de entorno:
```bash
# Oracle Database Configuration
export ORACLE_DB_URL="[URL_DE_CONEXION]"
export ORACLE_DB_USERNAME="[USUARIO]"
export ORACLE_DB_PASSWORD="[CONTRASEÑA]"
```

3. Compilar el proyecto:
```bash
mvn clean install
```

4. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## 📚 Documentación de la API

### Endpoints Principales

- `POST /api/shipments` - Registrar nuevo envío
- `GET /api/shipments/{trackingNumber}` - Consultar estado de envío
- `PUT /api/shipments/{trackingNumber}` - Actualizar estado y ubicación
- `GET /api/shipments` - Listar todos los envíos

### Formato de Número de Seguimiento

El número de seguimiento debe seguir el formato: `XX999999999XX`
- XX: Dos letras mayúsculas
- 999999999: Nueve dígitos
- XX: Dos letras mayúsculas

## 🧪 Pruebas

Para ejecutar las pruebas unitarias:
```bash
mvn test
```

## 📝 Notas Adicionales

- La aplicación utiliza HATEOAS para proporcionar enlaces relacionados en las respuestas
- Se implementan validaciones para todos los campos requeridos
- Se utiliza control de concurrencia mediante versionado optimista
- Los logs incluyen información detallada para debugging

## 🔐 Seguridad

- Las credenciales de la base de datos deben configurarse mediante variables de entorno
- Se recomienda utilizar HTTPS en producción
- Implementar autenticación y autorización según necesidades 