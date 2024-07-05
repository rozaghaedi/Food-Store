<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Product</title>
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

        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
        }

        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 16px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 16px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
            width: 100px;
            border-radius: 4px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<h1>Update Product</h1>

<button onclick="navigateTo('/products')"
        style="background-color: #4CAF50; color: white; border: none; padding: 10px 16px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; cursor: pointer; border-radius: 4px;">
    Back
</button>

<form action="/update-product" method="post" onsubmit="prepareForm()">
    <input type="hidden" name="id" value="${product.id}">

    <label for="sku">SKU:</label>
    <input type="text" id="sku" name="sku" value="${product.sku}" required><br>

    <label for="category">Category:</label>
    <input type="text" id="category" name="category" value="${product.category}" required><br>

    <label for="description">Description:</label>
    <input type="text" id="description" name="description" value="${product.description}" required><br>

    <label for="expiry_date">Expiry Date (yyyy-MM-dd):</label>
    <input type="text" id="expiry_date" name="expiry_date" value="${product.expiryDate}" required><br>

    <label for="price">Price:</label>
    <input type="text" id="price" name="price" value="${product.price}" required><br>

    <input type="submit" value="Update Product">
</form>

<script>
    function navigateTo(url) {
        window.location.href = url;
    }

    function prepareForm() {
        var priceInput = document.getElementById('price');
        priceInput.value = priceInput.value.replace(/,/g, '');
    }
</script>

</body>
</html>
