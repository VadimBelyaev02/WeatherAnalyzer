
## Running the Application
To run the app, follow these steps:
1. Run `./mvnw clean package`.
2. Run `docker-compose up`.

The application runs on port 8080.

You can interact with the app using the endpoints listed in the table below. Use any tool capable of sending HTTP requests.

| HTTP METHOD | ENDPOINT              | PARAMETERS                           | REQUEST BODY       | DESCRIPTION                                        |
|-------------|-----------------------|--------------------------------------|---------------------|----------------------------------------------------|
| GET         | `/api/weather/current`  | city (String, e.g., 'Minsk')         | -                   | Returns current weather in the specified city.      |
| GET         |`/api/weather/average`  | city (String, e.g., 'Minsk'); from (Date, '2023-12-30'), to (Date, '2023-12-31') | -       | Returns average weather information for the specified period and city. |
| GET         | `/api/locations/{id}`  | -                                    | -                   | Returns information about a location by its ID.     |
| GET         | `/api/locations`    | -                                    | -                   | Returns information about all locations.            |
| POST        | `/api/locations`    | -                                    | LocationRequestDto | Creates a new location.                            |
| PUT         | `/api/locations/{id}` | -                                    | LocationRequestDto | Updates an existing location.                       |
| DELETE      |`/api/locations/{id}` | |   | Deletes an existing location|
