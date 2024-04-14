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
   URL (со всеми фильтрами )--> https://api.kinopoisk.dev/v1.4/movie?page=1&limit=1&year=2022&ageRating=18&countries.name=Россия
   ![image](https://github.com/amk2916/testTaskAvito/assets/128518853/70d841a5-eee3-4bcf-963c-1bc7783eddb9)

 

3. **Get запрос для поиска по наименованию** 
   URL --> https://api.kinopoisk.dev/v1.4/movie/search?page=1&limit=10&query=doctor



## Функциональность
При поиске по наименовнанию игнорируются остальные фильтры
Кэшируется только основная выдача
