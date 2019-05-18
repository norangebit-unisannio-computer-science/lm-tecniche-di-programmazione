---
author: Raffaele Mignone
title: Caratterizzazione della complessità di un algoritmo per la fusione di due alberi binari ordinati tra loro
keywords:
  - Complessità
  - Alberi binari
  - fusione
subject: Caratterizzazione della complessità
papersize: a4
lang: it-IT
---

# Merge di due alberi binari

## Traccia 

Siano $T_1$ e $T_2$ due alberi binari di ricerca tali che tutte le chiavi in $T_1$ sono minori delle chiavi in $T_2$.
Scrivere un programma che crea un albero binario di ricerca contenente tutte le chiavi di $T_1$ e $T_2$ e calcolarne la complessità computazionale.

## Soluzione

L'esercizio è stato risolto usando uno pseudo *decorator* che va a wrappare i due alberi come mostrato in @fig:classDiagram.

```{#fig:classDiagram .plantuml caption="Class Diagram"}
interface Tree<K: Comparable<K>, V> {
  + get(key: K): Option<V>
  + set(key: K, value: V)
}

class MergeTree<K: Comparable<K>, V> {
  - leftTree: Tree<K, V>
  - rightTree: Tree<K, V>
  - medianKey: K
}

Tree <|-- MergeTree

note top of MergeTree: fun get(key: K): Option<V> {\n\treturn if( key <= medianKey ) leftTree.get(key)\n\telse rightTree.get(key)\n}
```

Sulla classe `MergeTree` sono consentite tutte l'operazione operazioni che è possibile fare un un albero standard in quanto implementa l'Interfaccia `Tree`.
Il ruolo di `MergeTree` è fare da dispatcher e passare le chiamate ai due sotto alberi.
La scelta dell'albero da chiamare avviene mediante il valore mediano[^size] che viene calcolato all'atto della creazione andando a recuperare la chiave più grande dell'albero di sinistra[^delete].

[^size]: Alcune operazioni, come `select` e `rank` vengono smistate tramite la size dei due alberi.

[^delete]: In caso di un'operazione di `delete` sulla chiave presa come valore mediano, oltre a passare la chiamata di `delete` bisogna avere anche l'accortezza di aggiornare il valore mediano.

## Caratterizzazione della complessità

Per creare il nuovo albero è necessario recuperare il valore più grande dell'albero di  sinistra, per fare ciò si usa la funzione `max` che ha una complessità $log(l)$ dove $l$ sono gli elementi presente nell'albero di sinistra.
Se indichiamo con $n$ la somma del numero di chiavi presenti nell'albero $T_1$ e $T_2$ possiamo affermare che nel caso migliore (albero di sinistra vuoto) la creazione ha complessità $1$, nel caso peggiore (albero di destra vuoto) una complessità $logn$;
quindi mediamente ci aspettiamo una complessità pari a $log\frac{n}{2}$ e quindi $logn$.

| Best case | Average | Worst case |
| :-:       | :-:     | :-:        |
| $1$       | $logn$  | $logn$     |
