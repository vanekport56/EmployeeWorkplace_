<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль пользователя</title>
   <style>
       html {
           background: #f0f2f5;
           background-image: linear-gradient(135deg, #e6e9e9 0%, #d4dde1 100%);
           font-family: "Roboto", sans-serif;
           -webkit-font-smoothing: antialiased;
           height: 100%;
           width: 100%;
           margin: 0;
           padding: 0;
       }

       body {
           margin: 0;
           padding: 0;
           height: 100%;
           width: 100%;
           display: flex;
           align-items: center;
           justify-content: center;
           position: relative;

       }

       #backgroundCanvas {
           position: absolute;
           top: 0;
           left: 0;
           width: 100%;
           height: 100%;
           z-index: 0;
       }

       .page {
           background-color: rgba(255, 255, 255, 0.8);
           box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
           color: #333;
           border-radius: 8px;
           max-width: 900px;
           width: 90%;
           padding: 30px;
           margin: 40px auto;
           position: relative; /* Чтобы размещать страницу поверх канваса */
           z-index: 1;
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

       .table {
           width: 100%;
           border-collapse: collapse;
           margin-top: 20px;
       }

       .table th, .table td {
           border: 1px solid #ddd;
           padding: 10px;
           text-align: left;
       }

       .table tbody tr:hover {
           background-color: #f1f1f1;
       }

       .first-column {
           width: 70%;
       }

       .second-column {
           width: 30%;
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

       /* Стиль для навигационной панели */
       .navbar {
           list-style-type: none;
           margin: 0;
           padding: 0;
           overflow: hidden;
           background-color: #333;
           text-align: center;
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

       /* Общие стили для форм */
       .edit-form, .change-password-form {
           display: none;
           margin-top: 1em;
       }

       .profile-info, .edit-form, .change-password-form {
           margin-bottom: 1em;
       }

       .form-container {
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
           display: block;
           padding: 12px;
           background-color: #333;
           color: white;
           text-decoration: none;
           text-align: center;
           transition: background-color 0.3s ease;
           border: none;
           width: 100%;
           border-radius: 4px;
           font-size: 16px;
       }

       .button:hover {
           background-color: #555;
       }

       /* Стилизация ссылки как кнопки */
       .link-button {
           display: block;
           padding: 12px;
           background-color: #333;
           color: white;
           text-decoration: none;
           text-align: center;
           transition: background-color 0.3s ease;
           border: none;
           font-size: 16px;
           border-radius: 4px;
       }

       .link-button:hover {
           background-color: #555;
       }


   </style>
    <link rel="stylesheet" type="text/css" href="/css/Background.css">
</head>
<body>
<canvas id="backgroundCanvas"></canvas>
<div class="page">
    <header>
        <ul class="navbar">


            <li class="dropdown"><a href="/vacation/">Отпуск</a></li>
            <li class="dropdown"><a href="/salary/">Зарплата</a></li>
            <li class="dropdown"><a href="/orderadocument/">Заказать документ</a></li>
            <li class="dropdown"><a href="/profile/">Профиль</a></li>
            <li class="dropdown"><a href="/logout">Выйти</a></li>
        </ul>
    </header>

    <div class="content">
        <h1>Профиль пользователя</h1>

        <!-- Форма редактирования профиля -->
        <form id="editProfileForm" action="/profile/update" method="post" class="edit-form form-container">
            <#-- Проверка, есть ли профильное фото -->
            <#if user.profilePhoto?exists && user.profilePhoto?has_content>
                <img src="${user.profilePhoto}" alt="User Profile Photo"/>
            <#else>
            <#-- Проверка на пол пользователя -->
                <#if user.gender == "MALE">
                    <img src="/images/AvatarMan.jpg" alt="Default Avatar for Men" width="120" height="150"/>
                <#else>
                    <img src="/images/AvatarWoman.jpg" alt="Default Avatar for Women" width="120" height="150"/>
                </#if>
            </#if>

            <p><strong>ФИО:</strong>
                <input type="text" name="fullName" value="${user.fullName}">
            </p>
            <p><strong>Пол:</strong>
                <select id="gender" name="gender">
                    <#list genders as gender>
                        <option value="${gender}" <#if user.gender == gender?string>selected</#if>>${gender}</option>
                    </#list>
                </select>
            </p>

            <p><strong>Дата рождения:</strong>
                <input type="date" name="birthDate" value="${user.birthDate?string('yyyy-MM-dd')}">
            </p>
            <p><strong>Должность:</strong>
                <input type="text" name="position" value="${user.position}">
            </p>
            <p><strong>Электронная почта:</strong>
                <input type="email" name="email" value="${user.email}">
            </p>
            <p><strong>Телефон:</strong>
                <input type="text" name="phoneNumber" value="${user.phoneNumber}">
            </p>
            <button type="submit" class="button">Сохранить изменения</button>
            <button type="button" class="button" onclick="toggleEditForm(false)">Отмена</button>
        </form>

        <!-- Отображение профиля -->
        <div id="profileDetails">
            <#if user.profilePhoto?exists && user.profilePhoto?has_content>
                <img src="${user.profilePhoto}" alt="User Profile Photo" width="120" height="150"/>
            <#else>
                <#if user.gender == "MALE">
                    <img src="/images/AvatarMan.jpg" alt="Default Avatar for Men" width="120" height="150"/>
                <#else>
                    <img src="/images/AvatarWoman.jpg" alt="Default Avatar for Women" width="120" height="150"/>
                </#if>
            </#if>

            <div class="profile-info">
                <h2>Личные данные</h2>
                <p><strong>ФИО:</strong> ${user.fullName}</p>
                <p><strong>Пол:</strong> ${user.gender}</p>
                <p><strong>Дата рождения:</strong> ${user.birthDate}</p>
                <p><strong>Должность:</strong> ${user.position}</p>
            </div>
            <div class="profile-section">
                <h2>Контактная информация</h2>
                <p><strong>Электронная почта:</strong> ${user.email}</p>
                <p><strong>Телефон:</strong> ${user.phoneNumber}</p>
            </div>
        </div>

        <div class="profile-section">
            <h2>Действия</h2>
            <button type="button" class="button" onclick="toggleEditForm(true)">Редактировать профиль</button>
            <p><a href="/changePassword" class="link-button">Изменить пароль</a></p>
        </div>
    </div>
</div>

<script>
    function toggleEditForm(editMode) {
        document.getElementById('editProfileForm').style.display = editMode ? 'block' : 'none';
        document.getElementById('profileDetails').style.display = editMode ? 'none' : 'block';
    }
</script>
<script src="/js/MotionAndLogic.js"></script>
</body>
</html>
