<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <style>
        #backgroundCanvas {
            position: fixed;
            top: 0;
            left: 0;
            z-index: -1;
            background-color: #333;
        }
        .error-container {
            background-color: #fff;
            padding: 2em;
            border-radius: 8px;
            box-shadow: 0 0 4px rgba(0, 0, 0, 0.1);
            margin-top: 50px;
            max-width: 600px;
            margin-left: auto;
            margin-right: auto;
            text-align: center;
        }

        .error-container h1 {
            color: #d9534f;
        }

        .error-container p {
            color: #666;
        }

        .error-container a.button {
            display: block;
            padding: 12px;
            background-color: #333;
            color: white;
            text-decoration: none;
            text-align: center;
            transition: background-color 0.3s ease;
            border: none;
        }

        .error-container a.button:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
<canvas id="backgroundCanvas"></canvas>
<div class="error-container">
    <h1>Error</h1>
    <#if errorMessage??>
        <p>${errorMessage}</p>
    <#else>
        <p>Произошла непредвиденная ошибка.</p>
    </#if>
    <a href="/profile/" class="button">На главную</a>
</div>
<script src="/js/MotionAndLogic.js"></script>
</body>
</html>
