<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Создание документа</title>
    <link rel="stylesheet" type="text/css" href="/css/HomeStyle.css">
    <style>
        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            overflow: hidden;
        }

        .page {
            background-color: rgba(255, 255, 255, 0.8);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
            margin: 50px auto;
            position: relative;
            z-index: 1;
        }

        h1 {
            margin-bottom: 20px;
            color: #333;
            text-align: center;
        }

        .form-table {
            width: 100%;
            border-collapse: collapse;
        }

        .form-table td {
            padding: 10px;
            border: none;
            text-align: center;
        }

        .form-table input[type="text"],
        .form-table input[type="date"],
        .form-table select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .form-table input[type="submit"] {
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
        .form-table a {
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
        }

        .form-table input[type="submit"]:hover,
        .form-table a:hover {
            background-color: #555;
        }

        /* Стили для радиокнопок */
        .radio-group {
            display: flex;
            flex-direction: column;
        }

        .radio-group label {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
            font-size: 16px;
        }

        .radio-group input[type="radio"] {
            margin-right: 10px;
        }

        #backgroundCanvas {
            position: absolute;
            top: 0;
            left: 0;
            z-index: 0;
            background-color: #333;
        }

        .back-button {
            display: inline-block;
            margin-bottom: 20px;
            background-color: #333;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        .back-button:hover {
            background-color: #555;
        }
    </style>
</head>
<body>

<canvas id="backgroundCanvas"></canvas>

<div class="page">

    <h1>Создание документа</h1>
    <form action="/orderadocument/create" method="post">
        <table class="form-table">
            <tr>
                <td>Тип документа:</td>
                <td>
                    <select name="type" required>
                        <option value="2-НДФЛ">2-НДФЛ</option>
                        <option value="Справка">Справка</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Имя документа:</td>
                <td><input type="text" name="name" required></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Создать документ">
                    <br>
                    <a href="javascript:history.back()" class="back-button">Назад</a>
                </td>
            </tr>
        </table>
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

            ctx.beginPath();
            ctx.arc(p.x, p.y, p.radius, 0, Math.PI * 2);
            ctx.fillStyle = '#555';
            ctx.fill();

            p.x += p.speedX;
            p.y += p.speedY;

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
