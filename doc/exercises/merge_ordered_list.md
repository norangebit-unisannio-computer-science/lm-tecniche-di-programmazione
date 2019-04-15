---
title: Caratterizzazione della complessità di un algoritmo per la fusione di liste ordinate
author: Raffaele Mignone
subject: Fusione di liste ordinate
keywords:
  - Complessità
  - Binary Heap
  - Coda a priorità
  - Kotlin
papersize: a4
lang: it-IT
---

# Fusione di liste ordinate

## Traccia

Descrivete un algoritmo per fondere $k$ liste ordinate in un’unica lista ordinata e valutarne la complessità. 
$N_i$, con $1 \le i \le k$, denota la lunghezza della $i$-esima lista, mentre $N = \sum_i N_i$ è il numero totale di elementi di tutte le liste.

## Soluzione

Per fondere le $k$ liste ordinate possiamo usare l'algoritmo di merge visto durante l'analisi del *mergesort*, a patto di adattarlo per un merge $k$-way.
Dovendo trovare di volta in volta il minimo tra $k$ elementi non è più possibile svolgere un confronto diretto, ma si rende necessario l'utilizzo di una coda a priorità minima di dimensioni $k$.

Inoltre è stato necessario utilizzare una struttura dati d'appoggio (@lst:node) per conservare le informazioni necessarie all'aggiunta degli elementi.

```{#lst:node .kotlin caption="Struttura dati che contiene l'elemento d'interesse, la lista da cui è stato estratto e la posizione in quest'ultima."}
data class Node<T>(val value: T, val k: Int, val n: Int)
```

Nel @lst:sort viene mostrato com'è possibile risolvere il problema attraverso un binary heap[^compare-by].

[^compare-by]: Il binary heap utilizzato è in grado di contenere l'oggetto `Node`, ma il confronto per l'ordinamento viene svolto sul parametro `value`.

```{#lst:sort .kotlin caption="Algoritmo di fusione mediante binary heap"}
fun <T : Comparable<T>> sort(vararg lists: List<T>): List<T> {
  val heap = BinaryHeap
    .createMinPriorityQueue<Node<T>, T> { it.value }
  lists.forEachIndexed { k, list ->
    heap.insert(Node(list.first(), k, 0))
  }

  val outList = ArrayList<T>()

  while (!heap.isEmpty()) {
    heap.pop().map {
      outList.add(it.value)
      val k = it.k
      val n = it.n + 1

      if (lists[k].size > n)
        heap.insert(Node(lists[k][n], k, n))
    }
  }

  return outList
}
```

## Caratterizzazione della complessità

Analizzando il @lst:sort è possibile notare l'utilizzo di una struttura `while` la cui condizione di arresto è legata allo svuotamento della coda a priorità.
Visto che nella coda devono *passare* tutti gli elementi delle liste e che per ogni ciclo viene aggiunto e rimosso un elemento il `while` verrà eseguito complessivamente $N$ volte.

All'interno del `while` viene eseguita un operazione di aggiunta di un elemento alla lista (complessità costante), un operazione di estrazione di un elemento dalla coda (avendo usato un binary heap di dimensione $k$ ha complessità $logk$) e una di aggiunta anche questa con complessità pari a $logk$.

Per quanto visto possiamo caratterizzare la complessità totale dell'algoritmo pari a $Nlogk$.

## Source code

- [BinaryHeap](https://git.norangeb.it/norangebit-unisannio-computer-science/lm-tecniche-di-programmazione/src/branch/master/src/main/kotlin/it/norangeb/algorithms/datastructures/queue/priority/BinaryHeap.kt)
- [OrderedListSorter](https://git.norangeb.it/norangebit-unisannio-computer-science/lm-tecniche-di-programmazione/src/branch/master/src/main/kotlin/it/norangeb/algorithms/exercises/OrderedListSorter.kt)
