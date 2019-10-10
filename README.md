# **Simulador de la memoria de un sistema linux**
Sistemas Operativos: Primer Proyecto

---
## **Diseño del simulador**
---

### **Descripción del proceso de administración de Memoria**

Se crean e inician la cantidad de procesos a simular, los cuales serán representados 
como hilos, éstos serán pasados al simulador el cual los ejecutará, iniciando su tabla 
de páginas con las primeras del string de referencia, dependiendo de su cantidad de 
páginas de TEXTO y DATOS, luego el sistema de operación irá asignando frames a sus 
páginas en memoria a medida que el proceso las vaya solicitando. Cuando la memoria se 
llene el simulador realiza el reemplazo con el algoritmo de **Reloj mejorado**.

---

### **Parámetros de entrada y salida**

Para realizar distintas simulaciones se plantea que el simulador pueda manejar
distintas cantidades de procesos con diferentes cantidades de memoria.

Parámetros de entrada:
+ Tamaño de la memoria principal: Variable, suministrado por el usuario, en KB.
+ Cantidad de procesos a simular: Variable, suministrado por el usuario, en KB.
+ Tamaño de página: 4KB.

---
Diseño de la interfaz
---

### **Simulador. Clase *OperatingSystem***

Funciones:
+ Iniciar/terminar procesos.
+ Asignar memoria a los procesos.

### **Arquitectura de la memoria principal. Clases *MemoryManagerUnit* y *MemoryEntry***

Será representada como un array de enteros que contiene los números de las 
páginas de los procesos que están en ejecución.

MemoryManagerUnit: Monitor
+ **SIZE**. Tamaño de la memoria a simular en KB.
+ **PAGE_SIZE**. Tamaño de página en KB.
+ **TOTAL_FRAMES**. Cantidad total de los frames de la memoria principal. SIZE / PAGE_SIZE
+ **mainMemory**. Representación de los frames de la memoria principal.
+ Llama al algoritmo de reemplazo.
+ Actualiza la tabla de página y los frames de la memoria principal.


MemoryEntry:
+ **frameOwnerPID**. ID/PID del proceso/hilo asignado al frame.
+ **page**. Número de página asignado al frame de la memoria.

---

### **Arquitectura de la tabla de página. Clases *PageTable* y *PageTableEntry***

PageTable: Tabla de página del proceso
+ **PAGES**. Cantidad total de páginas virtuales disponibles en la tabla.
+ **pageTableEntries**. Representación de la tabla de página.

PageTableEntry:
+ **frameID**. Dirección del frame asociado en la memoria principal.
+ **referenced**. Indica si el algoritmo de reemplazo la refenció.

---

### **Arquitectura de los procesos. Clase *SymProcess***

Los procesos se crearán con cantidad arbitraria pero fija de páginas de texto y de datos,
compartirán la memoria principal por lo que surgirá un problema de exclusión
mutua al momento de realizar el reemplazo de página, por lo que se implementará
el modelo de lectores/escritores para resolverlo, usando monitores.

Parámetros:
+ **name**. ID.
+ **stringRef**. String de referencia. Se lee desde un archivo “.txt”
+ **pageTable**. Tabla de páginas.
+ **pid**. PID asignado al crearse como hilo.

---

### **Algoritmo de reemplazo. Reloj. Clase *ClockAlgorithm***

Reemplaza la página con el bit de referencia en cero y primero las no modificadas
antes que las modificadas. Para simular los page faults que puedan ocurrir con
el algoritmo se vaciará toda la memoria principal o se matará uno o más procesos
cada cierto tiempo, también ocurren si hay muchos procesos pidiendo memoria.

Parámetros:
+ **bitRef**. Indica si la página está en memoria o no.

---

