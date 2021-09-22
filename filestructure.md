# Filstruktur

```ascii
project
│   README.md
│
└───calendar
    │   pom.xml
    │
    └───core
    |   │   pom.xml
    |   │
    |   └───src
    |   |   |
    |   |   └───main/java/calendar/core
    |   |       |   Calendar.java
    |   |       |   Event.java
    |   |       |   User.java
    |   |
    |   └───test/java/calendar/core
    |       |   EventTest.java
    |       |   UserTest.java
    |
    └───fxui
        |   pom.xml
        │
        └───src
            |
            └───main
            |   |
            |   └───java/calendar/ui
            |   |   |   CalendarApp.java
            |   |   |   CalendarController.java
            |   |
            |   └───resources/calendar/ui
            |       |   Calendar.fxml
            |
            └───test
            |   |
            |   └───java/calendar/ui
            |   |   |   CalendarControllerTest.java
            |   |
            |   └───resources/calendar/ui
            |       |   ...
```
