---
author: Raffaele Mignone
title: Caratterizzazione della complessità di un algoritmo per la ricerca del k minimo in uno stream
keywords:
  - Complessità
  - Coda a priorità
  - k minimo
subject: Caratterizzazione della complessità
papersize: a4
lang: it-IT
---

# Ricerca del k minimo

## Traccia

Scrivere un programma che, dato uno stream di interi in ingresso, restituisce in ogni momento il k-esimo elemento più piccolo.

## Soluzione

La risoluzione del problema è avvenuta attraverso un cambiamento di prospettiva.
Infatti la ricerca del $k$-esimo elemento più piccolo è equivalente alla ricerca dell'elemento più grande all'interno di una collezione di $k$ elementi.
Una volta strutturato il problema come ricerca del massimo può essere velocemente risolto attraverso una coda a priorità massima come mostrato nel @lst:ignoreInput.

```{#lst:ignoreInput .kotlin caption="Algoritmo di risoluzione non sensibile l'input"}
fun insert(elem: T): Option<T> {
  heap.insert(elem)
  
  if (heap.size() > k)
    heap.pop()

  if (heap.size() < k)
    return None

  return heap.peek()
}
```

## Caratterizzazione della complessità

La complessità della funzione `insert` del @lst:ignoreInput  è legata alla complessità delle funzioni della coda.
Sia la funzione per inserire un elemento nel binary heap che quella per rimuoverlo hanno una complessità logaritmica legata al numero di elementi nella coda.
Dato che una delle condizioni fondamenta per il corretto funzionamento dell'algoritmo è l'avere una coda sempre lunga $k$ possiamo affermare che la complessità della funzione `insert` è pari a $logk$.
Visto che la funzione `insert` sarà invocata per ogni elemento dello stream si ha una complessità totale di $nlogk$.

La soluzione mostrata nel @lst:ignoreInput non è sensibile all'input del problema quindi rappresenta un upper bound che può essere migliorato come mostrato nel @lst:smart.

```{#lst:smart .kotlin caption="Algoritmo di risoluzione sensibile all'input"}
fun insert(elem: T): Option<T> {
  heap.peek().fold(
    { heap.insert(elem) },
    {
      if (elem < it || heap.size() < k)
        heap.insert(elem)
    })

  if (heap.size() > k)
    heap.pop()

  if (heap.size() < k)
    return None

  return heap.peek()
}
```

In questa nuova versione, attraverso la funzione `fold`[^fold], si evita di aggiungere elementi più grandi dell'attuale massimo, che quindi verrano eliminati immediatamente dalla lista per portarla di nuovo ad una lunghezza $k$.
In questo modo l'algoritmo diventa sensibile all'input e nel caso ideale (il più grande elemento tra i primi $k$ elementi dello stream è più piccolo di tutti gli elementi che vengono dopo $k$) si attiene una complessità di $klogk$ e quindi una soluzione che è costante rispetto alla dimensione del problema.

[^fold]: La funzione `fold` ha come parametri due lambda expression, la prima viene eseguita quando l'oggetto è vuoto, mentre la seconda quando l'oggetto esiste.

| best case | average | worst case |
| :-:       | :-:     | :-:        |
| $klogk$   | $nlogk$ | $nlogk$    |

## Source code

- [BinaryHeap](https://git.norangeb.it/norangebit-unisannio-computer-science/lm-tecniche-di-programmazione/src/branch/master/src/main/kotlin/it/norangeb/algorithms/datastructures/queue/priority/BinaryHeap.kt)
- [KMin](https://git.norangeb.it/norangebit-unisannio-computer-science/lm-tecniche-di-programmazione/src/branch/master/src/main/kotlin/it/norangeb/algorithms/exercises/KMin.kt)
