<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>EWA</title>
    <link rel="stylesheet" type="text/css" href="/css/PageStyle.css">
    <style>
        #backgroundCanvas {
            position: absolute;
            top: 0;
            left: 0;
            z-index: -1;
            background-color: #333;
        }
        .button-container {
            display: flex;
            flex-direction: column;
            align-items: stretch;
            width: 100%;
        }
        .button {
            display: block;
            padding: 15px;
            margin: 5px 0;
            border: none;
            border-radius: 5px;
            color: #fff;
            background-color: #333;
            text-decoration: none;
            text-align: center;
            width: 100%;
            box-sizing: border-box; /* Чтобы padding не увеличивал ширину кнопки */
            font-size: 16px;
        }
        .button:hover {
            background-color: #555;
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
            <li class="dropdown"><a href="/logout">Выйти</a></li> <!-- Добавленная кнопка выхода -->
        </ul>
        <div class="button-container">
            <a href="/approve-requests" class="button">Утверждение отпусков</a>
            <a href="/edit-users" class="button">Редактирование данных пользователей</a>
            <a href="/view-requests" class="button">Просмотр запросов пользователей</a>
            <a href="/issue-salary" class="button">Выдача зарплаты</a>
        </div>
    </header>
</div>
<script src="/js/MotionAndLogic.js"></script>
</body>
</html>
