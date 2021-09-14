[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/todo-list) 

# todo-list-prosjektet

Dette prosjektet er et utviklingsprosjekt tilsvarende det en skal gjennom i IT1901. Det er ment å være til et nyttig eksempel på flere måter:
- Det viser hvordan prosjektet kan deles opp i mange fokuserte og håndterbare trinn
- Prosjektoppsett og kodingsteknikker
- Det er utgangspunktet for en videoserie som også viser hvordan det jobbes praktisk med utviklingsverktøy

Ikke alt vi gjør blir tatt opp på video, noe arbeid vil bli gjort som forarbeid eller mellom episodene, videoene skal gi en oversikt over alt arbeidet. 

## Bygging og kjøring av prosjektet

Prosjektet bruker maven til bygging og kjøring.

For å bygge, kjør `mvn install` fra rot-prosjektet (**todolist**-mappa). Dette vil kjøre alle tester og kvalitetssjekker.

Prosjektet må kjøres fra **fxui**-modulen, enten med `mvn javafx:run -f fxui/pom.xml` eller ved å først kjøre `cd fxui` og så `mvn javafx:run`.
Merk at man må først ha kjørt `mvn install` på modulene som **fxui**-modulen er avhengig av (pr. nå **core** og **fxutil**), for at det skal gå.

## Plan over arbeidet/episodene

Her følger oversikt over arbeidet/episodene. Først er dette en plan, men etterhvert som arbeidet blir utført så blir det en oversikt over faktisk gjennomført arbeid.

### Gitpodifisering av repoet og oppsett av første trinn av utviklingsprosjektet

Planen er å primært bruke gitpod til utvikling (selv om det kanskje ville vært mest praktisk for meg å bare bruke Eclipse).
Først må vi "gitpodifisere" repoet, dvs. gjøre at gitpod kan startes opp rett fra repo-sida på gitlab.

I starten så setter vi opp et enkelt JavaFX-prosjekt med **maven** som bygge-system. Vi setter det opp fra scratch, vha. en enkel mal for
maven-prosjekter og så justerer vi litt på det ved å kopiere elementer fra andre prosjekter f.eks. malen som ligger i *javafx-maven*-grenen i
[gitpod-templates-repoet](https://gitlab.stud.idi.ntnu.no/it1901/gitpod-templates) eller [simpleexample-repoet](https://gitlab.stud.idi.ntnu.no/it1901/simpleexamepl).
Jeg gjør det på denne måten, fordi det da er enklere å forklare hvert element i pom.xml-fila, som inneholder oppsettet.
Merk at prosjektoppsettet i starten er enklere enn det vil bli etter hvert, men vi gjør det sånn for å komme raskere i gang.
Så bygger vi ut og omstrukturerer når det trengs.

### Vår første utviklingsoppgave: Et enkelt API for todo-lister

Vi tar utgangspunkt i [brukerhistorie 1](brukerhistorier.md) og definerer tre brukeroppgaver, én for API-et, en for JSON-basert tekstformat og én for GUI-et og begynner på API-et.
API-et består av to klasser, **TodoList** og **TodoItem**, hvor den første inneholder flere av den andre.
**TodoList** har metoder for å legge til, fjerne og hente ut TodoItem-elementer.

Det ble [to episoder](https://ntnu.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?pid=54fc1b77-4d04-4fcb-8df2-ac32009ee479), episode 2 og 2b.

### JSON-basert tekstformat for handlelist-data

Vi bruker Jackson-biblioteket og implementerer serialisering- og deserialisering-klasser.

Det ble [to episoder](https://ntnu.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?pid=048886e9-1d6b-4727-87e8-ac32008f58c6), episode 3 og 3b.

### Et enkelt JavaFX-GUI for todo-lista

Vi bruker et **ListView** med en egen **ListCell**-subklasse som styrer hvordan hver rad ser ut.
Hver rad inneholder en avkrysningsboks (**CheckBox**) og en tekst (**Label**).
I redigeringsmodus brukes et tekstfelt for redigering av teksten.

Det ble [fem episoder](https://ntnu.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?pid=4c54ab10-3aeb-427c-9ed0-ac3200a06eea), episode 4, 4b, 4c, 4d og 4e.

### Konfigurering av verktøy for kodekvalitet

Vi konfigurerer opp tre verktøy knyttet til kodekvalitet:

- [checkstyle](https://checkstyle.sourceforge.io) - sjekker mer overfladiske og stilmessige egenskaper til koden som tekst
- [spotbugs](https://spotbugs.github.io/) - analyserer koden for vanlige feil
- [jacoco](https://www.jacoco.org) - samler inn og presenterer informasjon og testdekningsgrad

Det be [én episode](https://ntnu.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?id=4ff212a2-15f2-4009-9ec3-ac38010531bc), episode 5.

### Ulike typer forbedringer i brukergrensesnittet

Det ble [tre episoder](https://ntnu.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?pid=954e3cfb-8692-4d91-ac65-ac3d0140642a), episode 6,6b og 6c.


### Oppdeling i flere moduler

Vi deler opp prosjektet i to moduler, én for kjernekoden (logikk og serialisering) og en for brukergrensesnittet.

Det ble [to episoder](https://ntnu.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?pid=a3a720db-ad1e-4d04-a0c9-ac3f00a893ef), episode 7 og 7b.

### Om testing av brukergrensesnittet med TestFX

Vi implementerer (endelig) tester for brukergrensesnittkoden, vha. TestFX.

Det ble [to episoder](https://ntnu.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?pid=77561481-581a-4731-8867-ac41007ae0e9), episode 8 og 8b.

### Kapsle inn bruken av Jackson

Vi ønsker å unngå at andre moduler får en kompileringsavhengighet til Jackson, bare fordi core-modulen har det.

Det ble [to episoder](https://ntnu.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?pid=84e11eef-3289-420b-8721-ac4400fa3a80), 9 og 9b.

### Bruk av greiner for utviklingsoppgaver

Vi viser hvordan vi kan (og bør) bruke greiner for hver utviklingsoppgave.

Det ble [tre episoder](https://ntnu.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?pid=a6a3fa27-9553-4043-8374-ac4700a9d6c3), 10, 10a og 10b.

### Oppdeling av FXML og flere kontrollere

Vi ønsker å unngå at kontrollerklassen blir for stor og kompleks, ved å dele opp i flere FXML-filer og tilhørende kontrollere.

Det ble [én episode](https://ntnu.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?id=8a8df17e-45e7-4a42-ab2c-ac4b00bec6fb), episode 11.

### Variant av app med dokument-metafor

Vi lager en variant av appen, som bruker dokument-metaforen og derfor inkluderer en **File**-meny med kommendoer som **New**, **Open** og **Save**.
Koden er basert på en nokså generell implementasjon av menyen og en egen kontroller, som har jeg har liggende fra tidligere.
Derfor legges den generelle koden i en egen **fxutil**-modul, som **fxui** får en avhengighet til.

Denne varianten startes ved å angi en egen såkalt *profil* med `mvn javafx:run -Pdocumentapp` (og om nødvendig -f-opsjonen).
