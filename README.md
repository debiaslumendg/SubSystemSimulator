# **Simulador de la memoria de un sistema linux**
Sistemas Operativos: Primer Proyecto

**Autores:**
 *  Natascha Gamboa     12-11250
 * 	Manuel  Gonzalez    11-10390
 * 	Pedro   Perez       10-10574

---
## **Diseño del simulador**
---

### **Descripción del proceso de administración de Memoria**

Se crean e inician los procesos a simular, los datos de inicio se le piden al usuario. 

* Tamaño de la memoria, en frames.
* Cantidad de procesos a ejecutar.
* Tamaño de la memoria virtual para cada proceso, el número de páginas virtuales.

Cada **proceso** es representado como un **hilo**, para que los accesos a la memoria sean controlados por un **monitor**, el cual permita un solo proceso con acceso a la **memoria**.

Las tablas con las traducciones **página** **->** **frame** (Las *Page Tables*) son guardadas en la clase del sistema operativo, para cada proceso referenciados por el **PID** del **proceso**. 

Los procesos cuando se crean generan aleatoriamente una cadena de referencia hacia sus páginas del tamaño de su page table.

En la ejecución del proceso se recorre su string de referencia, cada acceso a memoria será a través del sistema operativo, el cual realizará la referencia si existe el frame asociado con la página, la asignación en caso que no exista y haya espacio o el reemplazo utilizando el algoritmo de **Reloj mejorado** si no hay frames disponibles.

Al terminar todos los procesos, el sistema muestra una tabla con las estadisticas de su ejecución.

```> PID|Accesos a memoria| Page Faults|Reemplazos provocados|Frames reemplazados en memoria|Frames escritos a disco```
    
```PID :``` Identificador del proceso/Hilo
```Accesos a memoria:``` Número de veces que el proceso hizo un acceso a la memoria
```Page Faults:``` Cantidad de accesos a páginas virtuales que realizó el proceso, los cuales no tenian un frame asociado.
```Reemplazos provocados:``` Cantidad de page faults del proceso que resultaron en una ejecución del algoritmo de reemplazo.
```Frames reemplazados en memoria:``` Cantidad de frames del proceso que fueron escogidos como target por el algoritmo de reemplazo.
```Frames escritos a disco:``` Cantidad de frames del proceso que fueron escritos a disco.

---
Diseño de la interfaz
---

### **Simulador. Clase *OperatingSystem***

El sistema operativo se implementa como la clase principal que se encarga de la mayoria de las acciones del simulador.

Implementa un método sincronizado para compartir el acceso a memoria.

OperatingSystem: Monitor
1) Crear/Iniciar/terminar procesos.
2) Manejar referencias a memoria de los procesos. (**Método sincronizado**)
<br/>
2.1) Convertir refencia número de página a número de frame accesado.
<br/>
2.2) Referenciar a los frames.
<br/>
2.3) Asignar frames libres a los procesos.
<br/>
2.4) Ejecutar el algoritmo de reemplazo.
<br/>
2.5) Actualiza la tabla de página de los procesos.
<br/>
3) Generar Logs en consola en su ejecución.
4) Guardar estadisticas en las referencias a memoria.

### Arquitectura de la memoria principal. Clases *MemoryManagerUnit* y *PageFrame*


La memoria principal será representada como una lista de frames `List<PageFrame>` administrada por una clase  `MemoryManagerUnit`.

MemoryManagerUnit: Administrador simple de memoria
+ **mainMemory**. Lista de PageFrame
+ **lastFreeFrame**. Ultimo frame en **mainMemory** que está libre, disponible para asignaciones.
+ Asigna o libera un frame.
+ Indica si hay frames libres.


PageFrame: Representa un frame de memoria.
+ **frameOwnerPID**. PID del proceso/hilo que tiene asignado el frame.
+ **page**. Número de página asignado al frame.
+ **rBit**. Booleano que indica si este frame ha sido referenciado.
+ **mBit**. Booleano que indica si este frame ha sido modificado.
+ **lastReferencedOn**. Dice la última vez que este frame ha sido referenciado.
+ Asigna, libera o referencia al frame.
+ Cuando se hace una referencia, hay una probabilidad de que se establezca el frame como modificado.


---

### Estructura de la tabla de página. 

La Tabla se guarda para cada proceso como una variable `HashMap` del sistema operativo.

`Map<Long, List<Integer>>:` Tabla de página para los procesos
+ **Long**. PID del proceso.
+ **List<Integer>**. Lista de números de frames, para cada página virtual de un proceso.

Ejemplo:

Map:
`[(2,[-1,1,3,-1])] :` El PID es `2` , y su Page Table es `[-1,1,4,-1]`. El proceso tiene **4** páginas virtuales, la segunda tiene asignado el `frame 1`, la tercera tiene asignado el `frame 4` y el resto no tiene frames asignados (`valores -1`).

---

### Arquitectura de los procesos. Clase *SymProcess*

Los procesos se implementan como hilos para compartir el acceso a memoria utilizando monitores, hay un solo método sincronizado para realizar todo lo relacionado con el acceso a memoria.

El método `run()` del proceso, implementa un `bucle` que recorre su `lista de referencia`, en donde en cada `ciclo` hace una `referencia` a una `página virtual` llamando a un método del `sistema operativo`.

Parámetros:
+ **references**. Lista de referencias, generada aleatoriamente del tamaño de su tabla de páginas, contiene solo referencias válidas [0 , número de páginas).
+ **pid**. PID asignado al crearse como hilo.
+ Realiza referencias a las páginas virtuales en su lista de referencias.

---

### Algoritmo de reemplazo. Reloj. Clases *PageReplacementAlgorithm* , *WSClock* 

Se encarga de obtener el frame a reemplazar cuando no hay un frame disponible.

**PageReplacementAlgorithm:** Interfaz abstracta a implementar por los algoritmos de reemplazo.
+ **getReplacementPageFrame**. Método abstracto que regresa el frame a reemplazar.


**WSClock:** Implementación del algoritmo de reloj mejorado

El algoritmo itera la lista de frames en memoria, cuando llega al último completa un ciclo.

En el transcurso del ciclo: 
* Si encuentra un frame muy viejo lo escoge para reemplazar y sale del algoritmo.
* Establece un frame que esté referenciado como no referenciado.
* Guarda una referencia al primer frame que no esté modificado y lo regresa al completar el ciclo actual.
* Si el frame está modificado y no referenciado lo coloca en la cola para guardar en disco  al finalizar el ciclo.

Al completar un ciclo:
* Se guarda en disco los frames en la cola y se ponen como no modificados.
* Si se tiene guardada una referencia a un frame limpio (sin referenciar y sin modificar) se regresa y se sale del algoritmo.

**Campos:**
**diskQueue**. Cola con páginas que se escribirán al disco cuando se complete el ciclo de reloj.
**currentPageFrame**. Indica el frame actual, en el recorrido del reloj.
**ageThreshold**. Tiempo máximo que puede estar una frame ocupado.
**Metodos:**
Obtiene el frame a reemplazar.
Escribe al disco un frame.
