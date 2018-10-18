# UML Диаграммы
1. [Диаграмма прецедентов](#1)<br>
1.1 [Актёры](#1.1)<br>
1.2 [Варианты использования](#2) <br>
1.2.1 [Вывод курса валют](#2.1) <br>
1.2.2 [Вывод цен на товары мировой биржи](#2.2) <br>
1.2.3 [Поиск банков с "лучшим" курсом](#2.3) <br>
1.2.4 [Конвертер валют](#2.4) <br>
2. [Диаграмма активности](#2)
3. [Диаграмма последовательности](#3)


### 1. Диаграмма прецедентов<a name="1"></a>
Диаграмма прецедентов представляет собой следующую диаграмму: 
![Use Case](https://github.com/Shvingelskiy/Exchange-rates/blob/master/Documents/Diagrams/UseCase/UseCaseDiagram.png)
<a name="1"/>

# 1 Актёры

| Актёр | Описание |
|:--|:--|
| Пользователь | Человек, использующий систему |

<a name="2"/>

# 2 Варианты использования

<a name="2.1"/>

## 2.1 Вывод курса валют

**Описание.** Позволяет пользователю просмотреть текущий курс валют.  
**Основной поток.**
1. Вариант использования начинается, когда пользователь запускает приложение;
2. Приложение проверяет доступ к интернету, если доступа нет, то выполняется альтернативный поток А1;
3. Приложение парсит сайт с курсами валют Национального Банка;
4. Приложение выводит курс валют в главном окне;
5. Вариант использования завершается;

**Альтернативный поток А1.**
1. Приложение не имеет доступа к интернету и использует ранее загруженные данные;
2. Приложение выводит курс валют в главном окне;
3. Вариант использования завершается;

<a name="2.2"/>

## 2.2 Вывод цен на товары мировой биржи

**Описание.** Позволяет пользователю просмотреть текущие цены на товары мировой биржи.  
**Основной поток.**
1. Вариант использования начинается, когда пользователь запускает приложение;
2. Пользователь "Скроллит" в правую сторону главное окно;
3. Приложение отображает боковое меню;
4. Пользователь нажимает кнопку "Мировая биржа";
5. Приложение проверяет доступ к интернету, если доступа нет, то выполняется альтернативный поток А2;
6. Приложение парсит сайт с ценами на товары мировой биржи;
7. Приложение выводит цены на товары мировой биржи в главном окне;
8. Вариант использования завершается;

**Альтернативный поток А2.**
1. Приложение не имеет доступа к интернету и использует ранее загруженные данные;
2. Приложение выводит цены на товары мировой биржи в главном окне;
3. Вариант использования завершается;


<a name="2.3"/>

## 2.3 Поиск банков с "лучшим" курсом

**Описание.** Позволяет найти банки с самым выгодным курсов для двух выбранных денежных едениц.   
**Основной поток.**
1. Пользователь "Скроллит" в правую сторону главное окно;
3. Приложение отображает боковое меню;
4. Пользователь нажимает кнопку "Поиск наилучших курсов";
5. Пользователь выбирает 2 валюты;
6. Пользователь нажимает кнопку "Поиск";
7. Приложение проверяет доступ к интернету, если доступа нет, то выполняется альтернативный поток А3;
8. Приложение парсит сайт с курсами валют всех банков страны;
9. Приложение выводит банки с самым выгодным курсом выбранных валют;
10. Вариант использования завершается;

**Альтернативный поток А3.**
1. Приложение не имеет доступа к интернету и использует ранее загруженные данные;
2. Приложение выводит банки с самым выгодным курсом выбранных валют;
3. Вариант использования завершается;


<a name="2.4"/>

## 2.4 Конвертер валют

**Описание.** Позволяет конвертировать одну валюту в другую.  
**Основной поток.**
1. Пользователь "Скроллит" в правую сторону главное окно;
2. Приложение отображает боковое меню;
3. Пользователь нажимает кнопку "Конвертер валют";
4. Пользователь выбирает 2 валюты;
5. Пользователь вводит сумму;
6. Пользователь нажимает кнопку "Конвертировать";
7. Приложение выводит результат перевода валюты; 
8. Вариант использования завершается;

### 2. Диаграмма активности<a name="2"></a>
Диаграммы активности: ([Ru](https://github.com/Shvingelskiy/Exchange-rates/blob/master/Documents/Diagrams/Activity/README.md)).

### 3. Диаграмма последовательности<a name="3"></a>
Все диаграммы: ([Ru](https://github.com/Shvingelskiy/Exchange-rates/blob/master/Documents/Diagrams/Sequence/README.md)).



