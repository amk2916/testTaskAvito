# Тестовое задание в Авито. Выполнил кандидат: Козырев А. М.


## Запуск приложения
Для запуска приложения в файле **local.property** необходимо создать переменную с наименованием 
**api_key** = "*здесь ключ для запросов*"

## Запросы
Для запросов используется класс *MoviesService.kt*, в котором получаются слудующие запросы: 
1. **GET запрос для главной страницы**: 
   URL (без фильтрации)--> https://api.kinopoisk.dev/v1.4/movie?page=1&limit=1
   URL (фильтр по году)--> https://api.kinopoisk.dev/v1.4/movie?page=1&limit=1&year=2022
   URL (фильтр по возрастному рейтингу)--> https://api.kinopoisk.dev/v1.4/movie?page=1&limit=1&&ageRating=18
   URL (фильтр по наименованию стран )--> https://api.kinopoisk.dev/v1.4/movie?page=1&limit=1&countries.name=Россия
   URL (со всеми фильтрами )--> https://api.kinopoisk.dev/v1.4/movie?page=1&limit=1&year=2022&ageRating=18&countries.name=
   **пример ответа с сервера**
   {
   "docs": [
   {
   "status": null,
   "rating": {
   "kp": 8.823,
   "imdb": 8.5,
   "filmCritics": 6.8,
   "russianFilmCritics": 100,
   "await": null
   },
   "votes": {
   "kp": 2006683,
   "imdb": 923505,
   "filmCritics": 129,
   "russianFilmCritics": 12,
   "await": 15
   },
   "backdrop": {
   "url": "https://image.openmoviedb.com/tmdb-images/original/bGksau9GGu0uJ8DJQ8DYc9JW5LM.jpg",
   "previewUrl": "https://image.openmoviedb.com/tmdb-images/w500/bGksau9GGu0uJ8DJQ8DYc9JW5LM.jpg"
   },
   "movieLength": 112,
   "id": 535341,
   "type": "movie",
   "name": "1+1",
   "description": "Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека, который менее всего подходит для этой работы, – молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается привнести в размеренную жизнь аристократа дух приключений.",
   "year": 2011,
   "poster": {
   "url": "https://image.openmoviedb.com/kinopoisk-images/1946459/bf93b465-1189-4155-9dd1-cb9fb5cb1bb5/orig",
   "previewUrl": "https://image.openmoviedb.com/kinopoisk-images/1946459/bf93b465-1189-4155-9dd1-cb9fb5cb1bb5/x1000"
   },
   "genres": [
   {
   "name": "биография"
   }
   ],
   "countries": [
   {
   "name": "Франция"
   }
   ],
   "typeNumber": 1,
   "alternativeName": "Intouchables",
   "enName": null,
   "names": [
   {
   "name": "1+1"
   },
   {
   "name": "Intouchables"
   },
   {
   "name": "不可触碰",
   "language": "CN",
   "type": null
   }
   ],
   "ratingMpaa": "r",
   "shortDescription": "Аристократ на коляске нанимает в сиделки бывшего заключенного. Искрометная французская комедия с Омаром Си",
   "ticketsOnSale": false,
   "ageRating": 18,
   "logo": {
   "url": "https://avatars.mds.yandex.net/get-ott/1531675/2a0000017f0262661cde61dc260cb86f7830/orig"
   },
   "top10": null,
   "top250": 2,
   "isSeries": false,
   "seriesLength": null,
   "totalSeriesLength": null
   }
   ],
   "total": 1056229,
   "limit": 1,
   "page": 1,
   "pages": 1056229
   }



2. **Get запрос для поиска по наименованию** 
   URL --> https://api.kinopoisk.dev/v1.4/movie/search?page=1&limit=10&query=doctor



## Функциональность
При поиске по наименовнанию игнорируются остальные фильтры
Кэшируется только основная выдача