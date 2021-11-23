# Release 1: Oppstart og konfigurasjon

## Ny funksjonalitet

- Oprettet selve prosjektet, samt opprettet konfigurasjonsfiler.

- Oprettet tre klasser i core, som er vår logikkmodul.
  - Event.java - Inneholder data om selve eventet.
  - Kalender.java - Består av flere events.
  - User.java - Består av en eller flere kalendere.

- Opprettet rammen til å lagre JSON filer ved hjelp av jackson.
  - Satte opp noen få seralizer og deserializer klasser samt en klasse for persistance og en for modulen.

- Opprettet to fxml-filer med kontrollere.
  - Login.fxml - Viser frem en login skjerm.
  - Calendar.fxml - Viser frem en kalender med events.

- Opprettet noen tester til core klassene våre.
  - EventTest.java.
  - CalendarTest.java.
  - UserTest.java.

- Satte opp CI/CD på gitlab.
  - Har satt opp repositoriet til å kjøre tester og kodekvalitetsverktøy i pipelinen.
  - Repositoriet viser også frem et merke med test-prosent, ved hjelp av jaccoco.

