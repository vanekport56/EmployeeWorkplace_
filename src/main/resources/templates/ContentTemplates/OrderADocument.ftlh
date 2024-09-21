<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Зарплата</title>
    <link rel="stylesheet" type="text/css" href="/css/PageStyle.css">
    <style>
        .filter-form label,
        .filter-form select,
        .filter-form input,
        .filter-form button {
            margin-top: 20px; /* Увеличивает расстояние между элементами */
        }
        .filter-form button:hover{
            background-color: #555;
        }
       .filter-form button{
            background-color: #333;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            box-sizing: border-box;
            margin-bottom: 20px;
        }

        .filter-form {
            display: flex;
            flex-direction: column; /* Все элементы выстраиваются в колонку */
            gap: 20px; /* Пространство между элементами */
        }
        select {
            width: 100%; /* Устанавливаем ширину, которая соответствует кнопке */
            height: 40px; /* Устанавливаем высоту, аналогичную высоте кнопки */
            padding: 10px; /* Внутренние отступы */
            font-size: 16px; /* Размер шрифта */
            border-radius: 5px; /* Скругленные углы, как у кнопки */
            border: 1px solid #ccc; /* Граница, аналогичная кнопке */
            background-color: #f7f7f7; /* Цвет фона */
            cursor: pointer; /* Устанавливаем вид курсора */
        }

        /* Для изменения при наведении курсора */
        select:hover {
            border-color: #888; /* Изменение цвета границы при наведении */
        }

        /* Для соответствия внешнему виду кнопки */
        select:focus {
            outline: none; /* Убираем стандартное выделение */
            border-color: #555; /* Цвет границы при фокусе */
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.2); /* Добавляем небольшую тень */
        }
        #backgroundCanvas {
            position: fixed;
            top: 0;
            left: 0;
            z-index: -1;
            background-color: #333;
        }
        .filter-form, .order-form {
            margin-bottom: 20px;
        }
        .table {
            width: 100%;
            border-collapse: collapse;
        }
        .table th, .table td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        .table th {
            background-color: #f2f2f2;
        }
    </style>
</head>

<body>
<canvas id="backgroundCanvas"></canvas>
<div class="page">
    <header>
        <ul class="navbar">
            <#if userRoles == "admin">
                <li class="dropdown"><a href="/admin/dashboard">Администрирование</a></li>
            </#if>
            <li class="dropdown"><a href="/vacation/">Отпуск</a></li>
            <li class="dropdown"><a href="/salary/">Зарплата</a></li>
            <li class="dropdown"><a href="/orderadocument/">Заказать документ</a></li>
            <li class="dropdown"><a href="/profile/">Профиль</a></li>
            <li class="dropdown"><a href="/logout">Выйти</a></li>
        </ul>
    </header>

    <!-- Форма фильтрации -->
    <div class="filter-form">
        <div class="create-button">
            <a href="/orderadocument/create">
                <button>Создать документ</button>
            </a>
        </div>
        <form action="/orderadocument/" method="get">
            <label for="name">Наименование:</label>
            <label for="type">Вид справки:</label>
            <select id="name" name="name">
                <option value="">Все</option>

                <#list certificateNames as name>
                    <option value="${name}" <#if name == selectedName>selected</#if>>${name}</option>
                </#list>
            </select>

            <button type="submit">Фильтровать</button>
        </form>

    </div>


    <!-- Таблица для отображения данных -->
    <div class="results-table">
        <table class="table">
            <thead>
            <tr>
                <th class="first-column">Номер заявки</th>
                <th class="second-column">Тип</th>
                <th class="third-column">Наименование</th>
                <th class="fourth-column">Дата создания</th>
                <th class="fifth-column">Файл</th>

            </tr>
            </thead>
            <tbody>
            <#list certificates as certificate>
                <tr onclick="openOrderForm('${certificate.certificateNumber}')">
                    <td class="first-column">${certificate.certificateNumber}</td>
                    <td class="second-column">${certificate.typeOfTheCertificate}</td>
                    <td class="third-column">${certificate.nameOfTheCertificate}</td>
                    <td class="fourth-column">${certificate.dateOfCreation}</td>
                    <#if certificate.file?has_content>
                        <td class="fifth-column">${certificate.file}</td>
                    <#else>
                        <td class="fifth-column">Нет файла</td>
                    </#if>

                </tr>
            <#else>
                <tr>
                    <td colspan="6">
                        <h3>Документов нет</h3>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <!-- Форма для заказа документа (по умолчанию скрыта) -->
    <div id="order-form" class="order-form" style="display: none;">
        <h2>Форма заказа документа</h2>
        <form id="orderForm" action="/submitOrder" method="post">
            <input type="hidden" id="documentId" name="documentId">
            <label for="comments">Комментарии:</label>
            <textarea id="comments" name="comments"></textarea>
            <button type="submit">Отправить заявку</button>
            <button type="button" onclick="closeOrderForm()">Отмена</button>
        </form>
    </div>

    <script>
        function openOrderForm(documentId) {
            document.getElementById('documentId').value = documentId;
            document.getElementById('order-form').style.display = 'block';
        }

        function closeOrderForm() {
            document.getElementById('order-form').style.display = 'none';
        }
    </script>

    <script src="/js/MotionAndLogic.js"></script>
</div>
</body>

</html>
