<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Заполните данные профиля</title>
    <link rel="stylesheet" type="text/css" href="/css/HomeStyle.css">
    <style>

        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            overflow: hidden;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        #backgroundCanvas {
            position: absolute;
            top: 0;
            left: 0;
            z-index: 0;
            background-color: #333;
        }


        .page {
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

        h1 {
            font-size: 24px;
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #555;
        }

        input[type="text"],
        input[type="email"],
        input[type="date"],
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="date"]:focus,
        select:focus {
            border-color: #007BFF;
            outline: none;
        }

        button {
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
            margin-top: 10px;
        }

        button:hover {
            background-color: #555;
        }

        button:active {
            background-color: #222;
        }

        form + form {
            margin-top: 10px;
        }


    </style>
</head>
<body>
<canvas id="backgroundCanvas"></canvas>
<div class="page">
    <h1>Заполните данные профиля</h1>
    <form action="/profile/incomplete" method="post">
        <label for="fullName">ФИО:</label>
        <input type="text" id="fullName" name="fullName" value="${user.fullName?default('')}" />

        <label for="gender">Пол:</label>
        <select id="gender" name="gender">
            <#list genders as gender>
                <option value="${gender.name()}" <#if user.gender?string == gender.name()>selected</#if>>${gender}</option>
            </#list>
        </select>

        <label for="position">Должность:</label>
        <input type="text" id="position" name="position" value="${user.position?default('')}" />

        <label for="birthDate">Дата рождения:</label>
        <input type="date" id="birthDate" name="birthDate"
               value="${user.birthDate?string('yyyy-MM-dd')?default('')}" />

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${user.email?default('')}" />

        <label for="phoneNumber">Номер телефона:</label>
        <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber?default('')}" />
        <label for="profilePhoto">Profile Photo URL:</label>
        <input type="text" id="profilePhoto" name="profilePhoto" value="${user.profilePhoto?default('')}" /><br/>

        <button type="submit" class="button">Сохранить</button>
    </form>

    <form action="/logout" method="post">
        <button type="submit" class="button">Выход</button>
    </form>
</div>
<script>


    const canvas = document.getElementById('backgroundCanvas');
    const ctx = canvas.getContext('2d');


    function resizeCanvas() {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
    }

    window.addEventListener('resize', resizeCanvas);
    resizeCanvas();


    let particles = [];
    const particleCount = 100;

    function initParticles() {
        particles = [];
        for (let i = 0; i < particleCount; i++) {
            particles.push({
                x: Math.random() * canvas.width,
                y: Math.random() * canvas.height,
                speedX: (Math.random() - 0.5) * 2,
                speedY: (Math.random() - 0.5) * 2,
                radius: Math.random() * 2 + 1
            });
        }
    }

    function drawParticles() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        particles.forEach(p => {
            // Рисуем точки
            ctx.beginPath();
            ctx.arc(p.x, p.y, p.radius, 0, Math.PI * 2);
            ctx.fillStyle = '#555';
            ctx.fill();

            // Движение точек
            p.x += p.speedX;
            p.y += p.speedY;

            // Отражение от краев экрана
            if (p.x < 0 || p.x > canvas.width) p.speedX *= -1;
            if (p.y < 0 || p.y > canvas.height) p.speedY *= -1;
        });
    }

    function animate() {
        drawParticles();
        requestAnimationFrame(animate);
    }

    initParticles();
    animate();

</script>
</body>
</html>
