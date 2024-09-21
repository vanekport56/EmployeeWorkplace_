<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Зарплата</title>
    <link rel="stylesheet" type="text/css" href="/css/PageStyle.css">
    <style>#backgroundCanvas {
            position: fixed;
            top: 0;
            left: 0;
            z-index: -1;
            background-color: #333;
        }</style>
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

    </header>

    <table class="table">
        <thead>
        <tr>
            <th class="first-column">Название документа</th>
            <th class="second-column">Номер документа</th>
            <th class="third-column">Сумма</th>
            <th class="fourth-column">Официальная должность</th>
            <th class="fifth-column">Дата создания</th>
        </tr>
        </thead>
        <tbody>
        <#list SalaryOffsets as SalaryOffset>
            <tr onclick="redirectToSalaryOffset(${SalaryOffset.officialPosition})">
                <td class="first-column">${SalaryOffset.nameOfTheDocument}</td>
                <td class="second-column">${SalaryOffset.documentNumber}</td>
                <td class="third-column">${SalaryOffset.sumOfMoney}</td>
                <td class="fourth-column">${SalaryOffset.officialPosition}</td>
                <td class="fifth-column">${SalaryOffset.dateOfCreation}</td>
            </tr>
        <#else>
            <tr>
                <td colspan="5">
                    <h3>Документов нет</h3>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <div class="content"></div>
</div>
<script src="/js/MotionAndLogic.js"></script>
</body>
</html>
