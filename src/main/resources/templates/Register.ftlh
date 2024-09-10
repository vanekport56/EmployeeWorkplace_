<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" type="text/css" href="/css/HomeStyle.css">
    <link rel="stylesheet" type="text/css" href="/css/Background.css">
    <style>
        .hidden-fields {
            display: none;
        }
        .error-message {
            color: red;
            margin-bottom: 10px;
            text-align: center;
        }
        .register-form {
            background-color: rgba(255, 255, 255, 0.8);
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
            box-sizing: border-box;
            z-index: 1;
            position: relative;
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
        input[type="password"],
        input[type="email"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            background-color: #333;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            box-sizing: border-box;
        }

        button:hover {
            background-color: #555;
        }

        .register-form a {
            color: #007BFF;
            text-decoration: none;
            display: block;
            margin-top: 20px;
            text-align: left;
        }

        .register-form a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<canvas id="backgroundCanvas"></canvas>


<div class="register-form">
    <h2>Регистрация</h2>
    <form action="/register" method="post">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" />
        <label for="username">Логин:</label>
        <input type="text" id="username" name="username" required />

        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required />

        <label for="confirmPassword">Повторите пароль:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required />

        <div class="hidden-fields">
            <label for="fullName">ФИО:</label>
            <input type="text" id="fullName" name="fullName" />

            <label for="position">Должность:</label>
            <input type="text" id="position" name="position" />


            <label for="phoneNumber">Номер телефона:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" />

            <label for="profilePhoto">Фото профиля:</label>
            <input type="text" id="profilePhoto" name="profilePhoto" />

        </div>
        <button type="submit">Зарегистрироваться</button>
        <a href="/login">Назад</a>
        <#if error??>
            <div class="error-message">
                ${error}
            </div>
        </#if>
    </form>
</div>

<script src="/js/MotionAndLogic.js"></script>
</body>
</html>
