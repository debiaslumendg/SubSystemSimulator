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
llene el simulador realiza el reemplazo con el algoritmo de **Reloj mejorado**,
que colocará en la memoria swap la página reemplazada y asignará la que está
solicitando memoria.

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
+ **mainMemory**. Representación de los frames de la memoria principal.
+ Llama al algoritmo de reemplazo.
+ Actualiza la tabla de página y los frames de la memoria principal.


MemoryEntry:
+ **frameOwnerPID**. ID/PID del proceso/hilo asignado al frame.
+ value.

---

### **Arquitectura de la tabla de página. Clases *PageTable*, *PageTableEntry*, *SwapTable* y *SwapTableEntry***

PageTable: Tabla de página del proceso
+ **VIRTUAL_PAGES**. Cantidad de páginas virtuales.
+ **pageTableEntries**. Entradas de la tabla de página.

PageTableEntry:
+ **virtualPageID**. Número de página.
+ **frameID**. Número de frame asociado en la memoria principal.
+ **referenced**. Bit de referncia que indica si el algoritmo de reemplazo la refenció.
+ **modified**. Bit de modificada.
+ **valid**. Bit de validez. Es válida si se encuentra en el rango [VIRTUAL_PAGES - nDataPages,VIRTUAL_PAGES - 1]

SwapTable: Tabla de página en la memoria swap
+ tableEntries.

SwapTableEntry:
+ NULL_VALUE.
+ isInDisk.
+ valueInDisk.
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
+ **mmu**. Representa el MMU, el mismo proceso maneja su memoria.
+ **nTextPages**. Cantidad de páginas de texto.
+ **nDataPages**. Cantidad de páginas de datos.
+ **pid**.

---

### **Algoritmo de reemplazo. Reloj mejorado. Clase *ClockAlgorithm***

Reemplaza la página con el bit de referencia en cero y primero las no modificadas
antes que las modificadas. Para simular los page faults que puedan ocurrir con
el algoritmo se vaciará toda la memoria principal o se matará uno o más procesos
cada cierto tiempo.

Parámetros:
+ **bitRef**. Indica si la página está en memoria o no.
+ **bitModified**. Indica si la página de datos ha sido modificada o no.

---

