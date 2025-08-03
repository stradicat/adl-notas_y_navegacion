# App de Notas con Navegación

## Conceptos

Múltiples _Activities_, `Intent`, Navegación, persistencia básica

## Objetivo

Crear una aplicación de notas con lista principal y pantalla de detalle/edición, usando navegación
entre Activities.

## Funcionalidades Requeridas

- Lista principal de notas con título y fecha
- Botón flotante para crear nueva nota
- Activity de detalle para ver/editar nota
- Guardar cambios automáticamente
- Eliminar notas desde el detalle
- Búsqueda de notas por título

## Estructura de Archivos

```
app/src/main/java/com/tuapp/notasapp/
├── MainActivity.kt
├── DetalleNotaActivity.kt
├── adapter/
│ └── NotasAdapter.kt
├── model/
│ └── Nota.kt
└── data/
└── NotasManager.kt
app/src/main/res/layout/
├── activity_main.xml
├── activity_detalle_nota.xml
├── item_nota.xml
└── toolbar_busqueda.xml
```

## Retos Adicionales

- [ ] Implementar categorías para las notas
- [ ] Agregar recordatorios con notificaciones
- [ ] Exportar notas a archivo de texto
- [ ] Modo oscuro/claro

## Consideraciones personales

- Compatibilidad mínima: API 23 (Marshmallow, Android 6.0)
- `viewModel` para preservar los valores ante cambios de configuración de dispositivo
- Implementación de barra de búsqueda
