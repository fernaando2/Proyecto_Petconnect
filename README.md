# 🐾 PetConnect: Innovación en el Rescate Animal

---

## 📋 Resumen del Proyecto
**PetConnect** nace como una solución tecnológica avanzada frente a la ineficiencia de los métodos tradicionales (redes sociales) para localizar mascotas perdidas. El proyecto se centra en **minimizar el tiempo de respuesta**, un factor crítico para garantizar la seguridad y la vida del animal.

**Desarrolladores:** 
* 👨‍💻 **Francisco Javier Mora Lucena**
* 👨‍💻 **Fernando Tejado Muñoz**

---

## 🛠️ Stack Tecnológico y Arquitectura
La robustez de la aplicación se basa en una arquitectura sólida y herramientas de desarrollo nativas:

* **Lenguaje:** `Java` (desarrollado en **Android Studio**).
* **Arquitectura:** Patrón **Modelo-Vista-Controlador (MVC)** para una clara separación de responsabilidades.
* **Ecosistema de Datos:**
    * `SQLite`: Base de datos local para garantizar operatividad **100% offline**.
    * `Firebase` & `Firestore`: Sincronización en tiempo real y autenticación.
    * `API REST`: Comunicación mediante conexiones HTTP nativas (`HttpURLConnection`).



---

## 🎨 Fundamentos de Diseño (Material Design)
El diseño no es solo estética, es funcionalidad. PetConnect sigue los principios de **Material Design** para asegurar una curva de aprendizaje mínima.

### 🔍 Experiencia de Usuario (UX)
* **Interfaz Intuitiva:** Navegación fluida centrada en la rapidez de reporte.
* **Accesibilidad:** Contrastes optimizados para uso en exteriores (bajo luz solar).
* **Diseño Responsivo:** Adaptación total a diversos formatos de pantalla.

### 🎨 Psicología del Color
| Color | Función | Significado |
| :--- | :--- | :--- |
| **Naranja** | Color Principal | Botones de acción y energía (Call to action). |
| **Rojo** | Alertas | Casos críticos de mascotas perdidas. |
| **Verde** | Éxito | Mascotas encontradas y usuarios activos. |
| **Gris/Blanco** | Neutros | Limpieza visual y reducción de fatiga. |

---

## ⚙️ Capacidades Técnicas y Sensores
La aplicación aprovecha al máximo el hardware del dispositivo para ofrecer una solución integral:

1.  **Geolocalización:** Integración con `Google Maps SDK` para posicionamiento exacto de incidencias.
2.  **Multimedia:** Uso de `Intents` para captura fotográfica nativa como evidencia.
3.  **Seguridad:** Gestión proactiva de **permisos dinámicos** en tiempo de ejecución.



---

## 💾 Gestión de Datos y Persistencia
PetConnect está diseñada para no fallar, incluso sin conexión a internet:

* **Sincronización Inteligente:** Volcado automático de datos locales a la nube una vez que se recupera la conexión.
* **Operaciones CRUD:** Gestión completa de incidencias (Crear, Leer, Actualizar, Borrar) con reflejo inmediato.
* **Seguridad de Sesión:** Implementación de `SignOut` seguro con destrucción de tokens y limpieza de caché.

---

## 🚀 Retos Técnicos Superados
* **Concurrencia Avanzada:** Implementación de `ExecutorService` para delegar tareas pesadas a hilos secundarios, evitando bloqueos en la interfaz de usuario (UI Thread).
* **Resiliencia de Red:** Capacidad de funcionamiento autónomo mediante persistencia en SQLite.

