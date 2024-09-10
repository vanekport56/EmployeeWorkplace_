<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Смена пароля</title>
    <style>html {
            background: #f0f2f5;
            background-image: linear-gradient(135deg, #e6e9e9 0%, #d4dde1 100%);
            font-family: "Roboto", sans-serif;
            -webkit-font-smoothing: antialiased;
        }

        body {
            margin: 0;
            padding: 0;
            height: 100%;
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .page {
            background-color: #fff;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            color: #333;
            border-radius: 8px;
            max-width: 900px;
            width: 90%;
            padding: 30px;
            margin: 40px auto;
        }

        h1 {
            margin-bottom: 30px;
            font-size: 28px;
            color: #333;
            text-align: center;
        }

        input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-top: 10px;
            margin-bottom: 20px;
            box-sizing: border-box;
            font-size: 16px;
            color: #333;
            background-color: #f9f9f9;
            transition: border-color 0.3s, background-color 0.3s;
        }

        input:hover, input:focus {
            border-color: #007bff;
            background-color: #fff;
        }

        form {
            margin: 0;
        }

        form label {
            font-weight: 500;
            color: #555;
        }

        form button {
            display: inline-block;
            width: 100%;
            padding: 14px;
            border: none;
            border-radius: 4px;
            color: #fff;
            background-color: #007bff;
            font-size: 18px;
            cursor: pointer;
            transition: background-color 0.3s, box-shadow 0.3s;
        }

        form button:hover {
            background-color: #0056b3;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        form button:active {
            background-color: #00408d;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
        }

        form button:disabled {
            background-color: #a1c5ff;
            cursor: not-allowed;
        }



        .table th, .table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        .table tbody tr:hover {
            background-color: #f1f1f1;
        }



        @media (max-width: 600px) {
            .page {
                padding: 20px;
                margin: 20px;
            }

            form button {
                width: 100%;
                padding: 12px;
            }
        }



        .navbar li {
            display: inline-block;
            position: relative;
        }

        .navbar a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
            font-size: 16px;
            background-color: #333;
        }

        .navbar a:hover,
        .navbar a:focus {
            background-color: #555;
        }

        .navbar .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
            z-index: 1;
            left: 0;
            right: 0;
        }

        .navbar .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
            text-align: left;
        }

        .navbar .dropdown-content a:hover {
            background-color: #f1f1f1;
        }

        .navbar .dropdown:hover .dropdown-content {
            display: block;
        }

        .navbar .dropdown:hover a {
            background-color: #555;
        }

            border: 1px solid #ccc;
            border-radius: 8px;
            padding: 16px;
            background-color: #f9f9f9;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }

        .error-message, .success-message {
            color: red;
            font-weight: bold;
        }

        .success-message {
            color: green;
        }

        .button {
            background-color: #333;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            border-radius: 4px;
        }

        .button:hover {
            background-color: #555;
        }


    </style>
    <link rel="stylesheet" type="text/css" href="/css/Background.css">
    <style>
        .button {
            background-color: #333;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            border-radius: 4px;
            width: 100%;
            box-sizing: border-box;
        }

        .button:hover {
            background-color: #555;
        }

        .password-change-container .button {
            margin-bottom: 15px;
        }

        .error-message, .success-message {
            text-align: center;
            margin-bottom: 10px;
        }

        .error-message {
            color: red;
        }

        .success-message {
            color: green;
        }
        .password-change-container {
            background-color: rgba(255, 255, 255, 0.8);
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
    <script>
        function clearMessages() {
            // Очищаем сообщения об ошибках и успехе
            document.querySelector('.error-message p').textContent = '';
            document.querySelector('.success-message p').textContent = '';
        }

        function goBack() {
            window.history.back();
        }

        function changePassword() {
            const currentPassword = document.getElementById('currentPassword').value;
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            fetch('/changePassword', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    currentPassword: currentPassword,
                    newPassword: newPassword,
                    confirmPassword: confirmPassword,
                }),
            })
                .then(response => response.json())
                .then(data => {
                    if (data.error) {
                        document.querySelector('.error-message p').textContent = data.error;
                    } else {
                        document.querySelector('.success-message p').textContent = data.success;
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    </script>
</head>
<body>
<canvas id="backgroundCanvas"></canvas>
<div class="password-change-container">
    <h2>Смена пароля</h2>
    <label for="currentPassword">Текущий пароль:</label>
    <input type="password" id="currentPassword" name="currentPassword" required>

    <label for="newPassword">Новый пароль:</label>
    <input type="password" id="newPassword" name="newPassword" required>

    <label for="confirmPassword">Подтверждение нового пароля:</label>
    <input type="password" id="confirmPassword" name="confirmPassword" required>

    <button type="button" class="button" onclick="clearMessages(); changePassword()">Изменить пароль</button>

    <button class="button" type="button" onclick="goBack()">Назад</button>

    <div class="error-message">
        <p></p>
    </div>

    <div class="success-message">
        <p></p>
    </div>
</div>
<script src="/js/MotionAndLogic.js"></script>
</body>
</html>
