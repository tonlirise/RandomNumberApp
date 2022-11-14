Приложение «Интересные факты о числах»

Главный экран
Разделен на две части, верхняя часть это поле для ввода числа, кнопка "Получить факт" и кнопка "Получить факт о случайном числе", нижняя часть - отображение истории запросов пользователя. У каждого элемента истории должно показываться число, которое искал пользователь, и предварительный просмотр фактов (все, что умещается в одну строку), при нажатии на элемент открывается 2-й экран.

2-й экран
Отображает пользователю число и полный текст факта о нём. Также имеется возможность вернуться к предыдущему экрану.
---
Для получения информации о числе - используется api http://numbersapi.com 
Например, для числа "10" - запрос http://numbersapi.com/10, для получения факта о случайном числе - http://numbersapi.com/random/math
---
MVVM, Clean Architecture, Service locator pattern, RecyclerView, Room, Retrofit, Сoroutines, ViewModel, LiveData, WorkManager.