---
title: Caratterizzazione della complessità di un algoritmo per l'ordinamento da file
author: Raffaele Mignone
subject: Ordinamento da file
keywords:
  - Complessità
  - Ordinamento da file
  - Kotlin
papersize: a4
lang: it-IT
---

# Ordinamento da file

## Tracia

Scrivere un programma per l’ordinamento di un file di grandi dimensioni, senza caricarlo in memoria, mediante distribuzioni e fusioni successive. Nel passo di distribuzione i valori vengono distribuiti in due file, passando dall’uno l’altro ogni volta che si incontro un valore minore dell’ultimo trattato; i due file vengono poi fusi prendendo ogni volta il valore minore fra i due non ancora trattati.

## Soluzione

L'algoritmo si compone di due parti principali: la parte di distribuzione (@lst:branch) e la parte fusione (@lst:merge) dei valori.
I due algoritmi vengono infine combinati nello snippet -@lst:run.


```{#lst:branch .kotlin caption="Algoritmo per la distribuzione dei valori"}
private fun branch(
  source: Scanner,
  destination1: PrintStream,
  destination2: PrintStream
) {
  var destination = destination1
  var current: T
  var last: T? = null

  while (source.hasNextLine()) {
    current = fromJson(source.nextLine())
    
      if (isLess(current, last))
        destination = switchDestination(
          destination, destination1, destination2
        )
        
    destination.println(toJson(current))
    last = current
  }
}
```

```{#lst:merge .kotlin caption="Algoritmo per la fusione dei valori"}
private fun merge(
  source1: Scanner,
  source2: Scanner,
  destination: PrintStream
) {
  var source = source1
  var current: T
  var cache: T? = null

  while (source1.hasNextLine() && source2.hasNextLine()) {
    if (cache == null)
      cache = fromJson(
        switchSource(source, source1, source2).nextLine()
      )

    current = fromJson(source.nextLine())

    if (!isLess(current, cache)) {
      source = switchSource(source, source1, source2)
      current = cache!!.also { cache = current }
    }

    destination.println(toJson(current))
  }
  
  destination.println(toJson(cache!!))

  mergeTail(source1, source2, destination)
}
```

```{#lst:run .kotlin caption="Algoritmo di sorting"}
fun run (source: File) {
  tryBranch(source)
  
  while (!isSorted()) {
    tryMerge(source)
    tryBranch(source)
  }
}
```

## Caratterizzazione della complessità

Per la la caratterizzazione della complessità si è scelto di usare il metodo dell'ordine di grandezza.
Per poter applicare questo metodo la prima operazione da compiere è l'individuazione dell'operazione caratteristica.
Trattandosi di un algoritmo che ordina valori letti da file come operazioni caratteristica sono state scelte le operazioni di lettura e scrittura.
Nel caso della funzione -@lst:branch sono presenti un'operazione di scrittura e una di lettura all'interno del ciclo `while`; quest'ultimo viene eseguito finché sono presenti linee nel file quindi $n$ volte.

Con un ragionamento analogo si può stimare pari ad $n$ anche la complessità della funzione -@lst:merge.

Passando all'analisi della funzione -@lst:run notiamo immediatamente un ulteriore ciclo `while` che ha come condizione d'arresto il file ordinato.
Da ciò possiamo evincere come l'algoritmo sia sensibile all'input e che quindi abbia una complessità che varia in base ad esso.
Inoltre notiamo anche che la funzione `tryBranch`[^nota] viene eseguita almeno una volta anche se i dati contenuti nel file già sono ordinati.
Avendo caratterizzato la complessità della funzione -@lst:branch pari a $n$ possiamo asserire che l'algoritmo di distribuzione e fusione ha una complessità di *best case* lineare.

[^nota]: Le funzioni `tryBranch` e `tryMerge` fanno da wrapper alle funzioni `branch` e `merge` ne gestiscono le eccezioni.

Per la complessità di *worst case* e media dobbiamo introdurre il concetto di numero di inversioni ovvero il numero di coppie di valori non ordinati.
Nel caso peggiore (valori ordinati in ordine inverso) si ha un numero di inversioni $i = \frac{n(n-1)}{2} \approx \frac{n^2}{2}$ dove $n$ indica la dimensione del problema.
Ipotizzando di poter rimuovere ad ogni *giro di while* un numero di inversioni proporzionale ad $n$ il ciclo `while` verrà eseguito circa $n$ volte.
Avendo un ciclo `while` eseguito $n$ volte che richiama delle funzioni con all'interno un altro ciclo `while` che viene eseguito $n$ volte, possiamo stimare la complessità *worst case* come quadratica.

Nei casi intermedi ci aspettiamo un numero di inversioni compreso tra $1$ e $\frac{n^2}{2}$ quindi sempre di ordine $n^2$.
Ripetendo gli stessi ragionamenti fatti per il *worst case* otteniamo un complessità media pari a $n^2$.

|             | *best case* | *average* | *worst case* |
| :-:         | :-:         | :-:       | :-:          |
| Complessità | $n$         | $n^2$     | $n^2$        |
