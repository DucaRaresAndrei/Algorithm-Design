**Duca Andrei-Rares**  
**321CA**

# Tema 2 PA

## Problema 1: Numarare

### Functia main()

Se citesc informatiile despre cele 2 grafuri, iar apoi se apeleaza functia findElementaryChains(), care intoarce rezultatul.

### Functia findElementaryChains()

Numara caile comune de la nodul 1 la nodul N in cele doua grafuri.

Logica din spate:
* Initializeaza vectorul dp pentru a numara caile
* Combina grafurile pentru a facilita sortarea topologica
* Sorteaza nodurile topologic
* Parcurge nodurile sortate si actualizeaza numarul de cai pentru nodurile copil comune in ambele grafuri

### Functia topologicalSort()

Realizeaza sortarea topologica a grafurilor. Calculeaza gradele de intrare ale nodurilor, initializeaza coada cu nodurile de start, apoi proceseaza nodurile in ordine topologica.

### Complexitatea algoritmului
* spatiala: O(N + M)
* temporala: O(N + M)

## Problema 2: Trenuri

### Functia main()

Se citesc orasele din care pleaca si in care ajunge, numarul de orase si conexiunile dintre acestea. Se efectueaza sortarea topologica a nodurilor si se apeleaza functia findDistinctCities, care intoarce rezultatul dorit.

### Functia topologicalSort()

Efectueaza sortarea topologica, folosind algoritmul lui Kahn. Identifica nodurile cu grad intern 0 si le proceseaza, scazand gradul intern al vecinilor lor. Procesul continua pana cand toate nodurile sunt incluse in ordinea topologica.

### Functia findDistinctCities()

Calculeaza numarul maxim de orase distincte care pot fi vizitate. Itereaza prin graful ordonat topologic, actualizand numarul maxim pentru fiecare nod, in functie de predecesorii sai.

### Complexitatea algoritmului
* spatiala: O(N + M), unde N este nr de noduri si M este nr de muchii
* temporala: O(N + M), unde N este nr de noduri si M este nr de muchii

### Problema 3: Drumuri

### Functia main()

Se citesc muchiile grafului si se adauga in listele de adiacenta corespunzatoare. Apoi se apeleaza functia findMinSum(), care intoarce rezultatul cerut.

### Functia findMinSum()

Calculeaza suma minima a unei submultimi de muchii care respecta cerintele problemei.

Logica din spate:
* Calculam distantele minime de la x si y catre z, folosind algoritmul dijkstra
* Iteram prin toate nodurile: pentru fiecare nod calculam suma distantelor de la x la nodul curent, de la y la nodul curent si de la nodul curent la z
* Daca aceste distante sunt valide, actualizam minCost

### Functia dijkstra()

Implementeaza algoritmul Dijkstra pentru a calcula distantele minime de la un nod sursa la toate celelalte noduri din graf.

### Complexitatea algoritmului
* spatiala: O(N + M)
* temporala: O((N + M) * log(N))
