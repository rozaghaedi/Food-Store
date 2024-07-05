<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Product</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }

        h1 {
            color: #333;
        }

        form {
            max-width: 500px;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        p {
            color: #777;
            margin-bottom: 20px;
        }

        input[type="submit"] {
            background-color: #e74c3c;
            color: white;
            padding: 10px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #c0392b;
        }

        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 16px;
            width: 100px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
            margin-top: 10px;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h1>Delete Product</h1>

<form action="/delete-product" method="post">
    <div>
        <p>Are you sure you want to delete this product?</p>
        <p>${product.id} - ${product.description}</p>
    </div>

    <input type="hidden" name="id" value="${product.id}">
    <input type="submit" value="Delete">
</form>

</body>
</html>
