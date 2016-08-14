# Viikkoraportti 3

_Aikaa käytetty tällä viikolla: 13h_

- Ti 9.8. 1,5h Korjauksia MinHeapiin ja sen testeihin.
- Ke 10.8. 2h Lyhimmän polun tulosten esittäminen, korjauksia 
Dijkstra-algoritmiin ja MinHeapiin.
- To 11.8. 1h PathStackin testejä.
- Pe 12.8. 2h AStar-algoritmin toteutus.
- La 13.8. 2,5h AStar- ja Dijkstra-algoritmien toteutusten yhdistäminen yhteen
luokkaan, muutama testi lisää ja pientä viilailua.
- Su 14.8. 4h Refaktorointia, paikkatietoaineiston kaivelua ja
muokkausta.

Ohjelman toteutus etenee hyvin aikataulussa. Viikon alussa korjasin ensin
minimikeossa sekä Dijkstra-algortimissa olleet virheet. Samassa 
yhteydessä kirjoitin näille lisää testejä (sen lisäksi että korjasin
myös testeissä olleita virheitä). A*-algoritmin toteutin ensin omana
luokkanaan; koska se on suurelta osin identtinen Dijkstra-algoritmin
kanssa, yhdistin A*- ja Dijkstra-luokat yhdeksi PathAlgorithm-luokaksi.
Refaktoroin hieman koodia muutoinkin pahimmiksi katsomistani paikoista.

Kun ajoin molempia algoritmeja data-kansiossa olevalla testgraph.data-
tiedostolla, ne antoivat eri tuloksia - ko. tiedostossa on kuitenkin
täysin hihasta vedettyjä koordinaatteja ja etäisyyksiä naapureihin, mikä
näyttää hämäävän AStaria. Aitoa dataa sisältävällä metropolitan.data -tiedostolla
molemmat algoritmit tuottavat samat polut.

Seuraavalle viikolle luvassa lisää testien kirjoittamista, algoritmien
ajon kellotus ja käytetyn ajan lisääminen tuloksiin, ajonaikaisen tuloshistorian
tallentaminen ja esittäminen sekä edellisen sisäänluetun datatiedoston
mukaisen verkon käyttö uudelleen polun etsinnässä. Minimikeko- ja pino-
rakenteet onkin jo toteutettu mutta verkko tallennetaan edelleen ArrayList-
rakenteeseen; tämä pitää korvata itse toteutetulla rakenteella.
