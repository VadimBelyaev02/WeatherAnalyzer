# WeatherAnalyzer

To run the app you need to run the following command in the following order:
1) mvn clean package
2) docker-compose up

The application port is 8080.

Now you can access the app using the table below.


| HTTP METHOD | ENDPOINT | PARAMETERS | REQUEST BODY | DESCRIPTION |
|-------------|-------------|------------|-------------|------------|
| GET    | /api/weather/current| city (String, 'Minsk') |  | Returns current weather in a specified city  |
| GET    | /api/weather/average| city (String, 'Minsk); from (Date, '2023-12-30'), to (Date, '2023-12-31') | | Returns average weather information for a specified period and a city |
| GET    | /api/locations/{id} |     || Returns information about a location by a specified id| 
| GET    | /api/locations      |     || Return information about all locations |
| POST   |/api/locations       |    |   LocationRequestDto | Creates a new location |
| PUT   | /api/locations/{id}       |    |  LocationRequestDto| Update an existing location|
| DELETE    | /api/locations/{id}   | |   | Deletes an existing location|
