**Duca Andrei-Rares**  
**321CA**

# Tema 1 PA

## Problema 1: Alimentare Servere

### Functia main()

    Se citesc informatiile despre cele N servere, reprezentate prin puterea de
calcul si limitele de alimentare. Apoi se apeleaza findMaxPower, care ne va
returna rezultatul.

### Functia findMaxPower()

    Pentru a cauta puterea de calcul maxima a sistemului, am ales sa folosesc un
algoritm de cautare binara, cu marginile in valorile minime si maxime ale
vectorului de limite de alimentare, spre a nu face calcule inutile. Algoritmul
calculeaza mijlocul intervalului, iar pentru a scapa de cazurile eronate, am
gandit ca, in cazul in care mijlocul nu se termina in ".0" sau in ".5", sa fie
rotunjit inspre intregul de mai aproape.

### Complexitatea algoritmului
* spatiala: O(N)
* temporala: O(N * log(N)), din cautarea binara si calcularea puterilor


## Problema 2: Colorare

### Functia main() + findDistinct()

    Se citesc pe rand perechile, iar noi tinem minte de fiecare data ce tip de
zona am avut la perechea precedenta. Apoi updatam rezultatul pentru fiecare
pereche, utilizand logica de calcul.

### Functia calculateLogic()

    prezinta logica de calcul in felul urmator:

* daca prima zona din tablou, este verticala, putem colora dreptunghiul in
3 moduri, deoarece nu avem nimic langa, care sa ne incurce;

* daca prima zona din tablou este orizontala, inseamna ca putem colora prin 6
moduri cele 2 dreptunghiuri suprapuse;

* daca zona precedenta a fost verticala, si urmeaza alte zone tot verticale,
numarul de moduri va fi inmultit cu 2 la puterea numarului nou de zone, deoarece
avand un singur dreptunghi, el ocupa o culoare, astfel cand punem un alt
dreptunghi langa el, are 2 culori libere pentru el;

* daca zona precedenta a fost orizontala, si urmeaza alte zone tot orizontale,
numarul de moduri va fi inmultit cu 3 la puterea numarului nou de zone, deoarece
dreptunghiurile suprapuse din stanga ocupa fiecare o culoare, astfel noile
dreptunghiuri suprapuse vor avea libere fiecare cate 2 culori, dar cum nu avem
voie ca dreptunghiuri adiacente sa aibe aceeasi culoare, vor fi doar 3 variante
de asezare;

* daca zona precedenta a fost verticala, si urmeaza o zona orizontala, se
inmulteste cu 2, deoarece dreptunghiul din stanga ocupa o culoare, ramanand doar
2 culori disponibile pentru noile dreptunghiuri suprapuse, astfel incat sunt
doar 2 variante;

* daca zona precedenta a fost orizontala, si urmeaza o zona verticala, ramane o
singura culoare disponibila pentru acel dreptunghi, deci numarul de variante
ramane acelasi.

### Functia calculatePower()

    Calculeaza efectiv ridicarea la putere, urmand logica de mai sus. Fiind
vorba de valori foarte mari, am gandit un algoritm eficient care merge doar pana
la radicalul puterii, iar apoi reface numarul dorit.

### Complexitatea algoritmului
* spatiala: O(1)
* temporala: O(K * sqrt(maxPower)), unde maxPower este cel mai mare numar X
din cadrul perechilor


## Problema 3: Compresie

### Functia main()

    Se citesc cele 2 siruri si se apeleaza functia de cautare a lungimii maxime
a noului sir.

### Functia findLength()

    Se cauta cel mai lung sir posibil, creat din operatiile de concatenare
aplicate asupra celor 2 siruri, pentru a ajunge egale. Astfel m-am gandit ca,
daca se cauta cea mai mare lungime, inseamna ca putem parcurge sirurile de la
primul element incolo, incercand sa formam elemente egale. Am considerat ca este
o operatie costisitoare sa eliminam efectiv elementele din siruri, indata ce le
alipim, astfel, consideram doar suma lor, dar parcurgerea merge inainte, motiv
pentru care folosim 2 indici, unul pentru fiecare sir.
    Logica din spatele cautarii sta in felul urmator:

* daca elementul curent este egal, mergem mai departe in ambele siruri
* altfel, aflam care este emenetul mai mic dintre cele 2, si constant incercam
sa il alipim cu urmatoru din acel sir, pana va fi mai mare sau egal cu elementul
din celalalt sir. Daca este egal, mergem mai departe in siruri. Daca este mai
mare, ne portam pe celalalt sir si facem acelasi algoritm.

    Astfel se va ajunge la cea mai mare lungime a sirului, in cazulin care se
evita exceptiile: unul din siruri s-a terminat, dar celalalt mai are elemente/
nu s-a putut forma un element comun.

### Complexitatea algoritmului
* spatiala: O(n + m)
* temporala: O(n + m)


## Problema 4: Criptat

### Functia main()

    Pentru logica la aceasta problema, m-am gandit sa lucrez eficient, astfel:
pentru fiecare cuvant am nevoie de un hashtable de litere si numarul aparitiilor
, iar global am nevoie de o lista ce stockeaza doar literele dominante din
cuvinte, spre a sorta doar dupa ele, si nu dupa orice litera prezenta, fapt
ineficient in timp.

    Astfel citim cuvantul si il atribuim clasei interne Word, de unde se vor
salva pentru fiecare cuvant urmatoarele informatii: lungimea sa, hashtablelul
mentionat mai sus si litera care apare de cele mai multe ori. Tot in acea clasa
mai avem metodele:
* getLetterApps, ce intoarce numarul de aparitii a unei litere in cuvant, sau 0,
in caz contrar
* getLetterFrequency, ce intoarce frecventa literei, adica numarul de aparitii,
impartit la lungimea cuvantului
* Mentionez ca functia getCharFrequencyMap() a fost realizata cu ajorul chatgpt

    Pentru fiecare cuvant aflam litera cu cele mai multe aparitii, iar daca
frecventa sa este mai mare de jumatate, o salvam in lista de litere dominante.

### Functia getMaxLengthPassword()

    Pentru fiecare din literele dominante, sorteaza cuvintele dupa frecventa
literei, descrescator, iar apoi calculeaza lungimea curenta pentru acea litera,
lungimea maxima fiind aflata dupa ce am sortat dupa toate literele dominante.

### Functia de sortare sortWords()

* Mentionez ca am facut-o utilizand chatgpt. Am gandit logica din spate si i-am
dat urmatoarea cerinta: 

Pentru o anumita litera data, sub forma String letter, vreau sa fac o sortare a
cuvintelor in primul rand dupa frecventa maxima a literei respective in acel
cuvant (frecventa se calculeaza prin impartirea numarului de aparitii ale
literii, impartit la lungimea cuvantului), iar daca sunt 2 astfel de cuvinte cu
o frecventa egala a literei, se sorteaza descrescator dupa lungimea cuvantului.
Iar apoi, pentru acele cuvinte ce nu contin litera, se sorteaza descrescator in
functie de lungimea cuvintelor.

* Astfel mi-a fost generata sortarea.

### Functia calculateCurrentLength()

    Parcurge cuvintele, iar pentru fiecare calculeaza noua lungime si noul numar
de aparitii al literei curente, iar daca se respecta logica ca frecventa sa fie
mai mare de jumatate, adaugam cuvantul in parola.

### Complexitatea algoritmului
* spatiala: O(N)
* temporala: O(N^2 * log(N))


## Problema 5: Oferta

### Functia main()

    Se citeste numarul de produse si pretul fiecaruia, apoi apelam functia de
calcul al pretului minim.

### Functia findLowestPrice()

    Intrucat putem grupa doar produsele adiacente, pentru eficientizare am ales
sa lucrez cu doar 3 valori stockate, fiind ultimele 3 sume minime, si o valoare
calculata pe moment. Astfel se urmeaza logica:

* minimul cu un singur produs este el insusi
* minimul cu doar 2 produse, este oferta de 2 aplicata lor
* minimul cu doar 3 produse este stabilit din 3 valori distincte si anume vrem
sa calculam minimul dintre(oferta pentru cele 3 produse/ suma dintre el sioferta
dintre primele 2/ suma dintre primul si oferta intre el si al 2lea)
* astfel pretul minim calculat incluzand oricare din urmatoarele produse, de la
un numar > 3 incolo, se calculeaza ca minimul dintre 3 valori. Suntem la
produsul i din lista, pretul minim curent, incluzand si produsul i, este minimul
dintre(suma dintre produsul i si minimul3/ suma dintre minimul2 si oferta de 2
dintre produsul i si produsul i-1/ suma dintre minimul1 si oferta de 3 dintre
produsele de pe pozitiile i, i-1 si i-2)
* se updateaza cele 3 minimuri

    Pe tot parcursul executiei:
* min3 = suma minima incluznd toate produsele, fara cel curent
* min2 = suma minima dintre toate produsele, mai putin ultimul, fara cel curent
* min1 = suma minima dintre toate produsele, mai putin ultimele 2, fara cel
curent
    ceea ce ne ajuta in a calcula ofertele si a stabili suma minima.

### Functiile calculateSumOf2() si calculateSumOf3()

    Calculeaza ofertele dintre 2, respectiv 3 produse, ca in cerinta.

### Complexitatea algoritmului
* spatiala: O(N)
* temporala: O(N)
