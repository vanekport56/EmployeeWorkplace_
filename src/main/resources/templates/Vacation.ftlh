<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Отпуск</title>
    <link rel="stylesheet" type="text/css" href="/css/PageStyle.css">
    <style>
        #backgroundCanvas {
            position: fixed;
            top: 0;
            left: 0;
            z-index: -1;
            background-color: #333;
        }


        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }


        .pagination-row {
            text-align: center;
        }

        .pagination-row td {
            padding: 0;
            border: none;
            background-color: #fff;
        }


        .btn-table {
            display: block;
            padding: 12px;
            background-color: #333;
            color: white;
            text-decoration: none;
            text-align: center;
            transition: background-color 0.3s ease;
            border: none;
        }


        .btn-table:hover {
            background-color: #555;
        }


        a {
            color: #333;
            text-decoration: none;
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

    <h1>Отпуск</h1>
    <a href="/vacation/create" class="btn">Оформить отпуск</a>

    <table class="table">
        <thead>
        <tr>
            <th class="second-column">Номер документа</th>
            <th class="first-column">Название документа</th>
            <th class="fifth-column">Причина</th>
            <th class="third-column">Дата начала</th>
            <th class="fourth-column">Дата окончания</th>
            <th class="sixth-column">Статус</th>
            <th class="seventh-column">Пользователь</th>
        </tr>
        </thead>
        <tbody id="vacationTableBody">
        <#if vacationDocuments?has_content>
            <#list vacationDocuments as document>
                <tr>
                    <td class="second-column">${document.documentNumber!''}</td>
                    <td class="first-column">${document.nameOfTheDocument!''}</td>
                    <td class="fifth-column">${document.reason!''}</td>
                    <td class="third-column">${document.formattedVacationStartDate!''}</td>
                    <td class="fourth-column">${document.formattedVacationEndDate!''}</td>
                    <td class="sixth-column">${document.approvalStatus!''}</td>
                    <td class="seventh-column">${document.userFullName!''}</td>
                </tr>
            </#list>

            <#if hasMoreData>
                <tr class="pagination-row">
                    <td colspan="7">
                        <a href="/vacation/?page=${currentPage}&size=${size + 10}&sortDirection=${sortDirection}" class="btn-table">
                            Показать больше
                        </a>
                    </td>
                </tr>
            </#if>

        <#else>
            <tr>
                <td colspan="7">
                    <h3>Документов нет</h3>
                </td>
            </tr>
        </#if>
        </tbody>
    </table>
</div>
<script src="/js/MotionAndLogic.js"></script>
</body>
</html>
