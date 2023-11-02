# E-commerce-exercise
Задача по созданию E-commerce решения на Android и Firebase

![Id3EQcCwOg](https://github.com/catsuperberg/E-commerce-exercise/assets/9151200/3ea0f31e-cfd2-4dc2-ba70-1897eb679ed5)

# Выполненные задачи
- База товаров, доступная всем клиентам, и реализованная с помощью Firebase 
- Возможность редактировать базу товаров авторизованным менеджером 
- Создаваемые и редактируемые товары имеют определенный набор полей, не удовлетворяющие требованиям товары отсеиваются
- Приложение имеет экран, на котором представлены все актуальные товары, отображающиеся в соответствии с требованиями
- Реализован просмотр расширенной информации о товаре
- Экран покупки, открывающийся по нажатию на кнопку в описании товара, позволяет ввести контактные данные и зарегистрировать заказ в БД
- Менеджер может просматривать открытые заказы
- Кэширование фотографий товара реализовано с помощью Coil
- Клиент имеет возможность выслать информацию о товаре в виде текстового сообщения и фотографии, пересылаемых в мессенджеры

# Особенности
Кэш изображений:
    - Библиотека Coil поддерживает хедеры "cache-control" и "expires", которые позволяют задать параметры кэширования
    - При работе с Firebase Storage библиотека не использует кэш и каждый раз подгружает изображения с сервера
    - Более старые версии библиотеки, использовавшие okHTTP, позволяли создавать interceptors и подменять нужные хедеры
    - Методы .setHeader() .removeHeader присутствуют в API, но не позволяют повлиять на кэширование
    - Решением стало использование .respectCacheHeaders(false) и необходимых policy кэширования при конфигурации Coil