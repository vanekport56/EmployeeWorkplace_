<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход</title>
    <link rel="stylesheet" type="text/css" href="/css/HomeStyle.css">
    <link rel="stylesheet" type="text/css" href="/css/Background.css"><!-- Подключаем файл стилей для canvas -->
    <style>
        .login-form {
            background-color: rgba(255, 255, 255, 0.8); /* Прозрачный фон для видимости canvas */
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
            box-sizing: border-box;
            position: relative;
            z-index: 1;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #555;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #333;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            box-sizing: border-box;
        }

        input[type="submit"]:hover {
            background-color: #555;
        }

        .error-message {
            color: red;
            margin-bottom: 10px;
            text-align: center;
        }

        .success-message {
            color: green;
            margin-bottom: 10px;
            text-align: center;
        }

        .login-form a {
            color: #007BFF;
            text-decoration: none;
        }

        .login-form a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<canvas id="backgroundCanvas"></canvas>


<div class="login-form" id="loginForm">
    <h2>Вход</h2>
    <form action="/login" method="post">
        <label for="username">Логин:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required>

        <input type="submit" value="Войти">


        <#if request.getParameter("error")?exists>
            <div class="error-message">
                ${request.getParameter("error")}
            </div>
        </#if>
    </form>
    <p><a href="/register">Регистрация</a></p>
</div>

<script src="/js/MotionAndLogic.js"></script>
</body>
</html>
