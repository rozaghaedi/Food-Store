<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cart</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fff;
            text-align: center;
        }

        thead {
            background-color: #4CAF50;
            color: white;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
        }

        tbody tr:hover {
            background-color: #f0f0f0;
        }

        a {
            text-decoration: none;
            padding: 8px;
            width: 100px;
            margin-right: 8px;
            background-color: #4CAF50;
            color: white;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }

        a:hover {
            background-color: #45a049;
        }

        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 16px;
            text-align: center;
            text-decoration: none;
            width: 100px;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
            margin-bottom: 10px;
        }

        button:hover {
            background-color: #45a049;
        }

        select {
            padding: 8px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<h1>Cart</h1>

<button onclick="navigateTo('/products')"
        style="background-color: #4CAF50; color: white; border: none; padding: 10px 16px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; cursor: pointer; border-radius: 4px;">
    Back
</button>

<br>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>SKU</th>
        <th>Description</th>
        <th>Price</th>
        <th>Category</th>
        <th>Expiry Date</th>
    </tr>
    </thead>
    <tbody>

    <#if products?has_content>
        <#list products as product>
            <tr>
                <td>${product.id}</td>
                <td>
                    <#if product.sku?has_content>${product.sku}<#else>N/A</#if>
                </td>
                <td>${product.description}</td>
                <td>${product.price}</td>
                <td>${product.category}</td>
                <td>${product.expiryDate}</td>
            </tr>
        </#list>
    </#if>
    </tbody>
    <div>Total Price: <#if totalPrice?has_content>${totalPrice}<#else>0</#if></div>
</table>

<script>
    function navigateTo(url) {
        window.location.href = url;
    }
</script>

</body>
</html>
