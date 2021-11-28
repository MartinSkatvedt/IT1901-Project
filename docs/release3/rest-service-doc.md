# REST-api documentation

This applications REST-api was build using Spring Boot. It consists of two REST-controllers, UserController.java and CalendarController.java, a REST-service class, userService.java, and a RestApplication class.

## Endpoint "/user/{username}"

### `GET`

-   Returns the user with the given username.

#### URL Params

-   `username = [string]`

#### Response body (JSON)

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

### `GET`

-   Returns the calendar of the user with given username, if the user exists.

#### URL Params

-   `username = [string]`

#### Response body (JSON)

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

### `POST`

-   Adds a new event to the calendar.

#### URL Params

-   `username = [string]`

#### Request body (JSON)

```json
{
    "header": "Party!",
    "description": "Party at my house",
    "date": "2021-10-31",
    "time": "13:00"
}
```

-   Response body (JSON):

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

### `GET`

-   Returns event with given id from the calendar of the user with given username, if the event and user exists.

#### URL Params

-   `username = [string]`
-   `id = [int]`

#### Response body (JSON)

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

### `PUT`

-   Replaces event with given id in the calendar of the user with given username, if the user exists.

#### URL Params

-   `username = [string]`
-   `id = [int]`

#### Request body (JSON)

```json
{
    "header": "Party!",
    "description": "Party at my house",
    "date": "2021-10-31",
    "time": "13:00"
}
```

-   Response body (JSON): No response body

### `DELETE`

-   Deletes event with given id from the calendar of the user with given username, if the event and user exists.

#### URL Params

-   `username = [string]`
-   `id = [int]`
