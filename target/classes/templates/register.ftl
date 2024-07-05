<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
            text-align: center;
        }

        form {
            max-width: 400px;
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
            width: 100px;
            border: none;
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

        .error {
            color: red;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .register {
            background-color: #ab82ff;
        }

    </style>
</head>
<body>
<h1>Register</h1>
<div class="error">
    <#if error?has_content>${error}<#else></#if>
</div>
<form action="/register" method="post">
    <input type="text" name="businessName" placeholder="Business Name" required>
    <br>
    <input type="text" name="telephoneNumber" placeholder="Telephone Number" required>
    <br>
    <input type="text" name="username" placeholder="Username" required>
    <br>
    <input type="password" name="password" placeholder="Password" required>
    <br>
    <input type="text" name="addressLine1" placeholder="Address Line 1" required>
    <br>
    <input type="text" name="addressLine2" placeholder="Address Line 2">
    <br>
    <input type="text" name="addressLine3" placeholder="Address Line 3">
    <br>
    <input type="text" name="country" placeholder="Country" required>
    <br>
    <input type="text" name="postCode" placeholder="Post Code" required>
    <br>
    <button type="button" onclick="window.location.href='/login'">Log in</button>
    <button class="register" type="submit">Register</button>
</form>
</body>
</html>
