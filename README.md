# KAI

Aplicacion Android orientada al desarrollo de habitos y crecimiento emocional mediante una mascota virtual evolutiva.

El usuario completa habitos diarios, gana experiencia y ayuda a que Kai evolucione y desarrolle diferentes atributos de personalidad.

---

# Objetivo del proyecto

KAI busca combinar:

* desarrollo de habitos
* gamificacion
* progreso personal
* evolucion emocional
* interaccion con mascota virtual

El proyecto esta desarrollado como trabajo academico para la materia de Desarrollo Movil Android utilizando arquitectura MVVM en Java.

---

# Tecnologias utilizadas

* Android Studio
* Java
* XML
* MVVM
* LiveData
* ViewBinding
* Navigation Component
* Retrofit
* API REST
* Git y GitHub

---

# Arquitectura

El proyecto utiliza arquitectura MVVM estricta.

Separacion de capas:

* Activity / Fragment -> UI
* ViewModel -> logica
* Repository -> acceso a datos
* Model -> entidades y modelos

La UI observa estados mediante LiveData.

---

# Caracteristicas principales

* Registro e inicio de sesion
* Seleccion de habitos
* Seguimiento de progreso
* Sistema de experiencia y rachas
* Evolucion de Kai
* Mensajes dinamicos de Kai
* Sistema de atributos emocionales

---

# Estructura del proyecto

```text
ui/
viewmodel/
model/
repository/
network/
utils/
```

---

# Flujo principal

1. El usuario inicia sesion
2. Selecciona habitos del catalogo
3. Completa habitos diarios
4. Gana experiencia
5. Kai evoluciona y cambia su estado
6. La app muestra mensajes y progreso

---

# Manejo de estados UI

La aplicacion utiliza estados UI separados:

* EstadoCargando
* EstadoExito
* EstadoError

Los Activities y Fragments solamente observan estados y actualizan la interfaz.

---

# Permisos Android

La aplicacion implementa permisos runtime clasicos Android.

Ejemplo:

* ACCESS_FINE_LOCATION

El Activity gestiona:

* checkSelfPermission
* requestPermissions
* shouldShowRequestPermissionRationale
* onRequestPermissionsResult

El ViewModel solamente emite eventos.

---

# Objetivo academico

El proyecto fue desarrollado siguiendo buenas practicas enseñadas en clase:

* separacion de responsabilidades
* arquitectura MVVM
* manejo de estados
* LiveData
* ViewBinding
* permisos runtime
* navegacion entre fragments

---

# Estado actual

Version MVP en desarrollo.

Funciones futuras:

* onboarding emocional
* sensores Android
* encuentros con animales
* diario emocional
* progreso fisico
* geolocalizacion
* evolucion avanzada de Kai

---

# Integrantes

* Roma Ricchiardi
* Equipo KAI
