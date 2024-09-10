<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Заказать документ</title>
    <link rel="stylesheet" type="text/css" href="/css/PageStyle.css">
    <style>
        #backgroundCanvas {
            position: absolute;
            top: 0;
            left: 0;
            z-index: -1;
            background-color: #333;
        }
    </style>

</head>

<body>
<canvas id="backgroundCanvas"></canvas>

<div class = "page">
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



    <div class = "content">
        <h1>Добро пожаловать на наш сайт!</h1>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce euismod diam sit amet magna cursus, sed sagittis neque pellentesque. Curabitur in malesuada ipsum. Donec a interdum odio. Aliquam ac felis nec justo posuere vulputate. Nulla facilisi. Sed interdum felis quis odio tempor, at tempus risus feugiat.</p>

        <h2>Наши услуги</h2>
        <p>Phasellus imperdiet mauris at arcu cursus, a ultricies ipsum venenatis. Morbi pretium urna vitae sollicitudin dictum. Sed id scelerisque augue. Vivamus vehicula mi sed lectus congue, in vestibulum eros posuere. Nam auctor dolor ac libero fermentum, in euismod purus sollicitudin.</p>

        <h2>О нас</h2>
        <p>Integer malesuada sapien ut elit cursus faucibus. Integer in malesuada est, id tempor ligula. Nunc mollis dolor nec purus luctus, a pretium nunc feugiat. Aenean consectetur diam ut arcu sodales, ac scelerisque mi tincidunt. Duis at magna ut turpis tincidunt elementum.</p>

        <h2>Контактная информация</h2>
        <p>Suspendisse potenti. Nulla a risus convallis, euismod est eu, viverra felis. Morbi vel urna quis nulla dapibus hendrerit. Sed ac urna a felis consectetur fermentum. Vestibulum at lectus sit amet justo fermentum dapibus. Donec a ex at sapien consequat aliquet.</p>







    </div>

</div>
<script src="/js/MotionAndLogic.js"></script>
</body>
</html>