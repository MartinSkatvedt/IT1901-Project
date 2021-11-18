# REST-api documentation

This applications REST-api was build using Spring Boot. It consists of two REST-controllers, UserController.java and CalendarController.java, and a RestApplication class.

## Endpoint "/user/{username}"

### GET requests

- Returns the user with the given username, if it exists.
- Request body (JSON): No request body
- Response body (JSON):

```json
{
  "username": "martine",
  "calendar": {
    "events": [
      {
        "header": "Party!",
        "description": "Party at my house",
        "date": "2021-10-31",
        "id": 1,
        "timeHour": 13,
        "timeMinute": 0,
        "timeString": "13:00"
      }
    ]
  }
}
```

## Endpoint "/calendar/{username}"

### GET requests

- Returns the calendar of the user with given username, if the user exists.
- Request body (JSON): No request body
- Response body (JSON):

```json
{
  "header": "Party!",
  "description": "Party at my house!",
  "date": "2021-10-31",
  "id": 1,
  "timeMinute": 0,
  "timeString": "13:00",
  "timeHour": 13
}
```

### POST requests

- Adds a new event to the calendar.
- Request body (JSON):

```json
{
  "header": "Party!",
  "description": "Party at my house",
  "date": "2021-10-31",
  "time": "13:00"
}
```

- Response body (JSON):

```json
{
  "header": "Party!",
  "description": "Party at my house",
  "date": "2021-10-31",
  "id": 1,
  "timeHour": 13,
  "timeMinute": 0,
  "timeString": "13:00"
}
```

## Endpoint "/calendar/{username}/{id}"

### GET requests

- Returns event with given id from the calendar of the user with given username, if the event and user exists.
- Request body (JSON): No request body
- Response body (JSON):

```json
{
  "header": "Party!",
  "description": "Party at my house",
  "date": "2021-10-31",
  "id": 1,
  "timeHour": 13,
  "timeMinute": 0,
  "timeString": "13:00"
}
```

### PUT requests

- Replaces event with given id in the calendar of the user with given username, if the user exists.
- Request body (JSON):

```json
{
  "header": "Party!",
  "description": "Party at my house",
  "date": "2021-10-31",
  "time": "13:00"
}
```

- Response body (JSON): No response body

### DELETE requests

- Deletes event with given id from the calendar of the user with given username, if the event and user exists.
- Request body (JSON): No request body
- Response body (JSON): No response body
