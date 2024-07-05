<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Log in</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
            text-align: center;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        header {
            font-size: 24px;
            font-weight: bold;
            margin: 40px 0px 0px 0px;
        }

        form {
            max-width: 300px;
            margin: auto;
        }

        input {
            padding: 10px;
            margin-bottom: 10px;
            width: 100%;
            box-sizing: border-box;
        }

        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            width: 100px;
            padding: 10px 16px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #45a049;
        }

        footer {
            text-align: center;
            padding: 10px;
        }

        .error {
            color: red;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .login {
            background-color: #ab82ff;
        }
    </style>
</head>
<body>
<header>
    Welcome to Food Store
</header>
<form action="/login" method="post">
    <h1>Log in</h1>
    <div class="error">
        <#if error?has_content>${error}<#else></#if>
    </div>
    <input type="text" name="username" placeholder="Username" required>
    <br>
    <input type="password" name="password" placeholder="Password" required>
    <br>
    <button class="login" type="button" onclick="window.location.href='/register'">Register</button>
    <button type="submit">Log in</button>
</form>
<footer>
    <p>Made by Rosa &copy; 2023 - Advanced Object-Oriented Course Project</p>
</footer>
</body>
</html>
