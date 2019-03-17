---
author: Raffaele Mignone
title: Caratterizzazione della complessità di un algoritmo per il calcolo della prefix average
keywords:
  - Complessità
  - Prefix average
subject: Caratterizzazione della complessità
classoption:
papersize: a4
colorlinks: true
lang: it-IT
---

# Prefix average

## Traccia

Scrivere un algoritmo per il calcolo del *prefix average* per un array numerico e caratterizzarne la complessità.

```{#lst:prefixavg .kotlin caption="Algoritmo per il calcolo della prefix average"}
fun prefixAverage(arrayIn: IntArray): IntArray {

  val arrayOut = IntArray(arrayIn.size)
  var currentSum = 0

  for (i in 0 until arrayIn.size) {
    currentSum += arrayIn[i]
    arrayOut[i] = currentSum / (i + 1)
  }

  return arrayOut
}
```

## Caratterizzazione della complessità

La caratterizzazione della complessità dell'algoritmo è stata svolta seguendo due metodologie; il metodo sperimentale e il metodo dell'ordine di grandezza.

### Metodo sperimentale

Il metodo sperimentale consiste nell'eseguire una serie di osservazioni sperimentali e di dedurre una legge da esse.

Nel caso specifico si è misurato il tempo di esecuzione dell'[algoritmo @lst:prefixavg] avente come ingresso un array di `8000` interi casuali.
L'osservazione è stata ripetuta altre quattordici volte raddoppiando ogni volta la dimensione dell'array. 

I dati sono stati riportati in un [foglio di calcolo][spreadsheet] e sono state plottate sia la curva $T(N)$ che la curva $ln(T(N))$.
Le due curve presentano un andamento lineare, soprattutto se non si tiene conto delle prime osservazioni svolte su un numero limitato di elementi e quindi maggiormente influenzate dal calcolatore.

### Metodo dell'ordine di grandezza

Il metodo dell'ordine di grandezza consiste nell'assegnare un costo all'operazione fondamentale e vedere come quest'operazione dipende dal numero degli elementi in gioco.

Nel caso del @lst:prefixavg prendiamo come operazione di riferimento l'accesso ad un elemento dell'array.
Ad ogni iterazione viene letto un elemento dall'array d'ingresso e scritto l'elemento calcolato nell'array d'uscita.
Quindi il tempo dominante nell'esecuzione dell'algoritmo è $2cN$ dove $c$ è il tempo impiegato per accedere ad un elemento dell'array e $N$ è il numero di elementi dell'array; per cui l'ordine di grandezza dell'algoritmo è pari ad $N$, quindi lineare.

[spreadsheet]: https://docs.google.com/spreadsheets/d/1EySx1UOZ8zoDl1uqVyLpQxZRGkD0-75qb9HeyEri1d4/edit?usp=sharing 
