# 🐾 PetConnect (Proyecto EcoCity) - Diseño UI/UX Avanzado

**Módulo:** Desarrollo de Interfaces  
**Centro:** MEDAC - Instituto Oficial de Formación Profesional  
**Autores:** Francisco Javier Mora Lucena, Fernando Tejado Muñoz  
**Plataforma:** Aplicación Móvil (Android)  

> **Nota:** Este repositorio contiene la entrega final del proyecto de diseño, prototipado, evaluación y rediseño de una interfaz gráfica, aplicando principios de Material Design, Usabilidad y Accesibilidad (WCAG).

---

## 📑 Índice
1. [Descripción y Propuesta de Valor](#1-descripción-y-propuesta-de-valor)
2. [Enlaces de Entrega (Figma y Canva)](#2-enlaces-de-entrega-figma-y-canva)
3. [Estructura del Repositorio](#3-estructura-del-repositorio)
4. [Arquitectura de la Interfaz (Pantallas Obligatorias)](#4-arquitectura-de-la-interfaz-pantallas-obligatorias)
5. [Fundamentos Visuales (Material Design)](#5-fundamentos-visuales-material-design)
6. [Pruebas de Usabilidad y 6 Mejoras Clave (V1 ➔ V2)](#6-pruebas-de-usabilidad-y-6-mejoras-clave-v1--v2)
7. [Futuras Implementaciones](#7-futuras-implementaciones)

---

## 1. Descripción y Propuesta de Valor

**PetConnect** es una adaptación del concepto de gestión de incidencias urbanas (*EcoCity*), enfocada en un problema crítico: reducir el tiempo de actuación entre la pérdida de una mascota y su recuperación.

Basándonos en el **Diseño Centrado en el Usuario (DCU)**, hemos creado una interfaz que no requiere curva de aprendizaje. Entendemos que el usuario puede estar bajo estrés o en la calle (luz solar directa, usando una sola mano). Por ello, el sistema está diseñado para que el ciudadano entienda su contexto en los **primeros 7 segundos** y reciba recompensas psicológicas mediante gamificación (Ej: Rango "Rescatador" y acumulación de puntos por ayudar).

---

## 2. Enlaces de Entrega (Figma y Canva)

* 🎨 **[Prototipo Interactivo en Figma (Incluye V1 y V2)]**(https://finish-finch-59171435.figma.site)
* 📊 **[Presentación del Proyecto en Canva]**(https://www.canva.com/design/DAHA1CvyiTo/BBALAyB3VndBPmA8-uKiiA/edit?utm_content=DAHA1CvyiTo&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

---

## 3. Estructura del Repositorio

Cumpliendo estricta y rigurosamente con los requisitos de la entrega, los archivos se organizan así:

* 📁 `/figma` → Contiene información sobre los componentes, Auto-layouts y enlaces a las versiones del diseño.
* 📁 `/documentacion` → Contiene el archivo `Documentacion_GUI.pdf` con la justificación extensa, guía visual y análisis de datos.
* 📁 `/pruebas_usabilidad` → Contiene el plan de evaluación (métricas, tiempos, tasa de error) realizado con 5 usuarios reales.
* 📁 `/imagenes` → Capturas del prototipo, comparativas Antes/Después e infografías de resultados.

---

## 4. Arquitectura de la Interfaz (Pantallas Obligatorias)

El diseño cubre el flujo completo de la aplicación, destacando las 5 pantallas principales exigidas:

1. **Pantalla de Login:** Acceso rápido con validaciones visuales en tiempo real y mensajes de error humanizados (proactivos, no técnicos).
   <img width="559" height="778" alt="image" src="https://github.com/user-attachments/assets/252614b7-3acd-4864-9884-8315f00f99b3" />

2. **Pantalla Principal (Home):** Listado de incidencias mediante tarjetas (*Material Cards*). Diferencia rápidamente el estado de las mascotas (Perdidos vs Encontrados) e incluye el resumen de gamificación del usuario.
   <img width="493" height="909" alt="image" src="https://github.com/user-attachments/assets/58ffdaa8-dff9-4eff-8869-b9707058c2a7" />

3. **Pantalla de Creación de Incidencia (Reporte):** Formulario estructurado sin desplegables pesados. Usa selectores visuales rápidos (chips) y un área de captura multimedia (foto) de gran tamaño.
   <img width="512" height="904" alt="image" src="https://github.com/user-attachments/assets/4ab584e1-5a38-4292-bfca-bac7f97a72e9" />
    
5. **Pantalla de Detalle y Mapa:** Representación de ubicación exacta. Utiliza un mapa interactivo con pines semánticos muy visuales y legibles.
   <img width="363" height="802" alt="4" src="https://github.com/user-attachments/assets/8724f2f2-5d1f-4e96-9d13-483394bdc8c9" />
6. **Pantalla de Chat SOS (Soporte):** Conversación estructurada en burbujas para diferenciar claramente al usuario del operador, con botones de acceso rápido para emergencias ("Enviar ubicación").
  <img width="507" height="912" alt="image" src="https://github.com/user-attachments/assets/4bf530df-11ea-49fa-a641-502413a3f1da" />

---

## 5. Fundamentos Visuales (Material Design)

La interfaz se rige por un estricto Sistema de Diseño, sin decisiones estéticas arbitrarias:

* **Tipografía:** **Roboto** (Estándar de Android). Optimizada con escalas proporcionales (Titulares 24px, Cuerpo 14px) para accesibilidad universal.
* **Paleta Semántica:**
  * 🟠 **Naranja Principal (#FF6D00):** Para acciones principales (Call to Action). Cálido y urgente, pero sin generar pánico.
  * 🔴 **Rojo (#F44336):** Reservado estrictamente para urgencias críticas (Pines de mascotas perdidas).
  * 🟢 **Verde Bosque (#4CAF50):** Genera confianza, indica éxito y marca mascotas encontradas.
* **Componentes:** Uso de *Auto-layouts*, márgenes de 16dp, rejillas de 8dp y bordes redondeados (12dp) en las tarjetas para imitar la respuesta física del mundo real.

---

## 6. Pruebas de Usabilidad y 6 Mejoras Clave (V1 ➔ V2)

Realizamos pruebas bajo el protocolo de "pensamiento en voz alta" con **5 usuarios externos** de diferentes edades y competencias digitales (Juan, Ángel, Iván, Pablo y Agustín). 

Tras analizar métricas como el tiempo por tarea, la tasa de éxito y el nivel de satisfacción (escala SUS 4.2/5.0), **rediseñamos el prototipo generando la V2 con las siguientes 6 mejoras exigidas:**

1. 📸 **Rediseño del Botón Multimedia (Caso Juan):** Se sustituyó un icono decorativo por un botón de acción gigante con el texto claro "Añadir Foto", eliminando la confusión al crear un reporte.
2. 💬 **Humanización de Mensajes de Error (Caso Ángel):** Implementación de validaciones visuales proactivas (Ej: "Parece que te falta el símbolo @" en lugar de "Error de sintaxis").
3. 🗺️ **Optimización del Mapa (Caso Iván):** Aumento del área táctil e inclusión del botón "Centrar en mi ubicación actual", reduciendo el tiempo de reporte un 40%.
4. 🧑‍💻 **Diferenciación Visual en Chat (Caso Pablo):** Implementación del esquema de burbujas clásico de Material Design (verde para usuario, gris para soporte) y avatares.
5. 🔄 **Navegación Circular y Salida (Caso Agustín):** Inclusión de botones superiores de "Atrás" y "Finalizar" tras un reporte para evitar la sensación de "callejón sin salida".
6. ⏳ **Estados de Carga Visuales:** Integración de *Skeletons* y barras de progreso para ofrecer feedback constante sobre el estado del sistema.

---

## 7. Futuras Implementaciones

Como recomendaciones de escalabilidad técnica para futuras versiones (V3), se propone:
* **Notificaciones Push (Geofencing):** Alertas automáticas para ciudadanos que entren en el radio de pérdida de un animal.
* **Soporte Offline:** Caché local para reportar incidencias en zonas sin cobertura, sincronizándose al recuperar conexión.
* **Dark Mode (Modo Oscuro):** Mejora de accesibilidad en entornos nocturnos y ahorro de batería en pantallas OLED.

