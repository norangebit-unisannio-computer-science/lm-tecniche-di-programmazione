---
title: Ordinamento di punti cartesiani in base al loro angolo polare
author: Raffaele Mignone
lang: it-IT
subject: Ordinamento
keywords:
  - Higher Order Functions
  - Kotlin
  - Sorting
papersize: a4
---

# Ordinamento di punti

## Tracia

Scrivere un programma per ordinare un insieme di punti rispetto all’angolo polare che formano con un punto dato P.
Valutare la complessità.

![Esempio di ordinamento](figures/pointSorter.pdf)

## Soluzione

I punti cartesiani non presentano alcun ordinamento naturale, quindi implementare di la classe `Point` usando l'interfaccia `Comparable` renderebbe la nostra astrazione non coincidente con la realtà e la soluzione specifica per questo problema e non riutilizzabile.

La libreria standard di Java ci consente di eseguire ordinamenti su oggetti che non implementano l'interfaccia `Comparable`, o in base a criteri non specificati in essa, attraverso la classe `Comparator`.
Analizzando la struttura interna della classe `Comparator` si nota immediatamente la sua natura di wrapper rispetto alla strategia di confronto racchiusa nel metodo `compare`.
Questa soluzione si rendeva necessaria in linguaggi strettamente OO, ma ciò non è più necessario nei linguaggi che supportano le *Higher Order Functions* come kotlin.
Per questo motivo all'interfaccia `Sorter` (@lst:sorter) è stata aggiunta la funzione `compareWith` che oltre ad accettare un array di oggetti (non è necessario che siano di tipo `Comparable`) accetta anche una funzione `compare` che ricevuti due oggetti in input restituisce un intero.

```{#lst:sorter .kotlin caption="Interfaccia utilizzata dagli algoritmi di ordinamento"}
interface Sorter {
  fun <T : Comparable<T>> sort(array: Array<T>)
  fun <T> sortWith(array: Array<T>, compare: (T, T) -> Int)
}
```

Nella classe `Point` (@lst:point) è stata implementata la funzione `theta` che tramite l'ausilio della funzione di libreria `Math.atan2`, restituisce l'angolo[^angolo] formato con l'origine degli assi cartesiani.
Tramite un companion object[^companion-object] è stata implementata la funzione `thetaComparator` che accetta come parametri tre punti e esegue il confronto tra gli angoli formati dai primi due punti rispetto all'origine indicata dal terzo punto.

[^angolo]: L'angolo viene restituito nel range $[-\pi, \pi]$, ma per poter eseguire un confronto dovremo portarlo nell'intervallo $[0, 2\pi[$.

[^companion-object]: Le funzioni dichiarate all'interno di un companion object possono essere equiparate ai metodi statici di Java.

```{#lst:point .kotlin caption="Classe Point"}
data class Point(val x: Double, val y: Double) {

  fun theta(): Double = Math.atan2(y, x)

  companion object {
    fun thetaComparator(
      p1: Point,
      p2: Point,
      origin: Point = Point(0, 0)
    ): Int {
      var theta1 = (p1 - origin).theta()
      var theta2 = (p2 - origin).theta()

      if (theta1 < 0) theta1 += 2 * Math.PI
      if (theta2 < 0) theta2 += 2 * Math.PI

      return theta1.compareTo(theta2)
    }
  }
}
```

A questo punto è possibile eseguire il sort di un array di punti attraverso un sorter come mostrato nel @lst:main [^eccezioni]

[^eccezioni]: La funzione `main` non gestisce le eccezioni.

```{#lst:main .kotlin caption="Ordinamento e stampa di punti letti da file"}
fun main(args: Array<String>) {
  if (args.size < 4 || args.size % 2 != 0) return

  val origin = Point(args[0].toDouble(), args[1].toDouble())

  val list = ArrayList<Point>()

  for (i in 2 until args.size step 2)
    list.add(Point(args[i].toDouble(), args[i + 1].toDouble()))

  val points = list.toTypedArray()

  val compare = { p1: Point, p2: Point ->
    Point.thetaComparator(p1, p2, origin)
  }

  Mergesort.sortWith(points, compare)

  println("origin in $origin")
  points.forEach {
    println(it)
  }
}
```

## Caratterizzazione della complessità

A differenza della funzione `sort` applicata ad un array di `Comparable` l'implementazione attuale differisce solo nel modo in cui viene eseguito il confronto tra gli oggetti, per cui la complessità complessiva dell'algoritmo coincide con quella dell'algoritmo di sorting utilizzato.
Nel caso del @lst:main si è usato il `Mergesort` per cui si ha una complessità di best, average e worst case pari a $n\log n$.

## Sort by

Nel caso di oggetti privi di un ordinamento naturale è possibile percorrere una via alternativa all'utilizzo di un `Comparator`.
Invece di passare la funzione che esegue la comparazione possiamo passare una funzione che si occupa di *estrarre* dal nostro oggetto un oggetto di tipo `Comparable` e usare quest'ultimo come riferimento durante le operazioni di confronto.

Per fare ciò è stata aggiunta la funzione `sortBy` all'interfaccia `Sorter`.

```kotlin
fun <T, C : Comparable<C>> sortBy(
  array: Array<T>,
  compareBy: (T) -> C
)
```

Nel @lst:main2 viene mostrato l'utilizzo della funzione `sortBy` per ordinare i punti in base al raggio.
Inoltre essendo l'algoritmo di mergesort stabile avremo un ordinamento in base all'angolo *theta* e a parità di angolo in base al raggio.

Per gli stessi ragionamenti di cui sopra la complessità totale dell'algoritmo non cambia.

```{#lst:main2 .kotlin caption="Ordinamento in base all'angolo e a parità di angolo in base al raggio"}
fun main(args: Array<String>) {
  if (args.size < 4 || args.size % 2 != 0) return

  val origin = P(args[0].toDouble(), args[1].toDouble())

  val list = ArrayList<Point>()

  for (i in 2 until args.size step 2)
    list.add(P(args[i].toDouble(), args[i + 1].toDouble()))

  val points = list.toTypedArray()

  Mergesort.sortBy(points) {
    (it - origin).r()
  }

  val compare = { p1: Point, p2: Point ->
    Point.thetaComparator(p1, p2, origin)
  }

  Mergesort.sortWith(points, compare)

  println("origin in $origin")
  points.forEach {
    println(it)
  }
}
```

## Source code

- Interfaccia [Sorter](https://github.com/norangebit-unisannio-computer-science/lm-tecniche-di-programmazione/blob/master/src/main/kotlin/it/norangeb/algorithms/sorting/Sorter.kt)
- Oggetto [Mergesort](https://github.com/norangebit-unisannio-computer-science/lm-tecniche-di-programmazione/blob/master/src/main/kotlin/it/norangeb/algorithms/sorting/Mergesort.kt)
- Classe [Point e funzione main](https://github.com/norangebit-unisannio-computer-science/lm-tecniche-di-programmazione/blob/master/src/main/kotlin/it/norangeb/algorithms/exercises/PointSorter.kt)
