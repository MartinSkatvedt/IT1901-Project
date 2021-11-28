# Release 3: Lage ny klient med annen teknologi
dette er dokumentasjon for tredje utgave av vårt prosjekt i IT1901.

## Maven build
Kalenderapplikasjonen vår bygges med Maven. Maven automatiserer nedlastningsprosessen og håndterer prosjektets dependencies. 


## Endringer siden sist utgave
Gruppen bestemte seg for å bygge om klientsiden med ny teknologi og beholde samme funksjonalitet som før.
Vi har bygget en ny frontend i React da vi tenkte det ble lettere å gjøre API-kall fra en javascript-aplikkasjon. Denne React-delen ble bygget med npm.

- Opprettet et **REST-API**
- Lagde **log out**-funksjon for fxui
- Laget en ny ***web-klient*** i React
- La til **Jpackage** and **Jlink** slik at brukere kan konvertere applikasjonen til en JRE
- Utvidet tester
- Mer utdypende dokumentasjon 
- La til et **Springboot-rammeverk** for rest-modulen. slik at brukere lagres i web-server.



## Kodearkitektur

The project is a multi module project to easily divide the different parts of the code. The project is divided in four main modules: Core, UI, Rest and integrationTest.

### Core
Core modulen inneholder alle kjerneklassene innkludert json-håndtering. Disse sørger for den interne logikken i applikasjonen og håndtering av brukerdata. 


### UI
UI-modulen inneholder alle metoder, brukergrensesnitt og logikk som gjør applikasjonen brukbar. Brukergrensesnittet er lagd med JavaFX og FXML.

Bruker vil først komme inn på en innlogingsside hvor man kan logge inn ved å skrive inn brukernavn. Man vil deretter bli sendt videre til en egen kalender. 

### Rest
Rest-modulen bruker Springboot til å sette opp en API. klassene i denne modulen brukes til kommunikasjon mellom web-serveren og applikasjonen. dette lagrer brukere lokalt og på web-serveren.  




# Workflow

Vi har brukt rammeverket scrum for å hjelpe oss med en god arbeidsflyt og god oversikt. 
Vi opprettet en milestone for hver sprint, samt issues for endringer, og for hvert issue
velger vi en milestone (Første, Andre eller Tredje Innlevering)


## Issues

Vi brukte issues for å hele tiden ha oversikt over hva som måtte gjøres og hva som var blitt gjort. Hvert issue fikk en label og ble tildelt enten den som opprettet issuet eller noen andre, 
så vi hele tiden så hvem som jobbet med hva. I tillegg gav vi issuene en kort
beskrivelse der vi syntes dette var nødvendig (andre ganger var navnet på issuet nok til å forstå
hva som måtte ordnes). Labelene vi i hovedsak har brukt er: Bug, Complicated og Documentation.



## Branches

For hver nye feature opprettet vi en ny branch knyttet til et issue. Slik holdt vi nye endringer
adskilte fra master-branchen i tilfelle en feil skulle dukke opp. Da kunne vi passe på at alt fungerte
før vi merget branchen med master. Siden hver branch ble knyttet til et issue fikk de samme navn som issuet samt sitt eget issue-nummer. Dette gav god oversikt. 


### Merge-requests

Før noen fikk utføre en merge-request måtte de informere de andre i gruppen samt være helt sikre
på at featuren fungerte slik som det var tenkt i issuet. Appen måtte kjøre uten problemer i branchen
før man fikk merge den med master. Etter merge-request lukket vi issuet den tilhørte (hvis ikke 
det ble lukket automatisk). 



## Kodekvalitet

Vi la til flere plugins for å forsikre oss om god kodekvalitet: 

- **Spotbugs** 
(spotbugs-maven-plugin) - sjekker koden for vanlige bugs

- **Checkstyle**
(checkstyle-maven-plugin) - sjekker at utviklerne (vi) skriver ryddig og lesbar kode

- **Jacoco**
(jacoco-maven-plugin) - samler inn og viser informasjon om kode-coveragen i koden


### Tester

Vi har tester som sjekker at logikken, features og metoder i ulike klasser fungerer slik vi har tenkt.
Snapshot-tester blir kjørt på klienten. 

### Diagrammer
Vi lagde forskjellige diagrammer for å illustrere produktets virkemåte. 
 - Pakkediagram finner du [her](../resources/Package_diagram.png)
 - Klassediagram finner du [her](../resources/Class_diagram.png)
 - Sekvensdiagram finner du [her](../resources/Sequence_diagram.png)
