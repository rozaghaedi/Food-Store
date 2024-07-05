<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Store</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f4;
            background-size: cover;
            background-position: center;
            position: relative;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: space-between;
        }

        nav ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        nav li {
            margin-bottom: 10px;
        }

        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            width: 180px;
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

        header {
            text-align: center;
            padding: 10px;
        }

        footer {
            text-align: center;
            align-content: center;
        }

        .user-info-box {
            padding: 20px;
            border-color: #002f00;
            border-width: 2px;
            border-style: solid;
            border-radius: 20px;
            display: flex;
            flex-direction: column;
            align-items: stretch;
            justify-content: center;
            background-color: white;
            width: 25%;
            margin: 0 auto;
        }

        .user-info-box p {
            display: flex;
            flex-direction: row;
            width: 100%;
            justify-content: space-between;
            align-items: center;
            height: 10px;
        }

        .user-info-box .label {
            color: green;
        }

        .user-info-box .value {
            margin-left: 10px;
        }

        .logout {
            background-color: #c0392b;
        }

        .add_product {
            background-color: darkgreen;
        }

        .view_products {
            background-color: #e0a307;
        }

    </style>
</head>
<body>
<header>
    <h1>Welcome to the Food Store!</h1>
</header>
<div class="user-info-box">
    <#if user?has_content>
        <p>
            <span class="label">User</span>
            <span class="value">${user.username}</span>
        </p>
        <p>
            <span class="label">Business Name</span>
            <span class="value">${user.businessName}</span>
        </p>
        <p>
            <span class="label">Phone</span>
            <span class="value">${user.telephoneNumber}</span>
        </p>
        <p>
            <span class="label">Country</span>
            <span class="value">${user.address.country}</span>
        </p>
        <p>
            <span class="label">Post Code</span>
            <span class="value">${user.address.postCode}</span>
        </p>
        <p>
            <span class="label">Address</span>
            <span class="value">${user.address.addressLine1 + user.address.addressLine2 + user.address.addressLine3}</span>
        </p>
    <#else>
        <p>User information not available</p>
    </#if>
</div>
<nav>
    <ul>
        <li>
            <button class="view_products" onclick="navigateTo('/products')">View Products</button>
        </li>
        <li>
            <button class="add_product" onclick="navigateTo('/add-product')">Add Product</button>
        </li>
    </ul>
</nav>

<footer>
    <button class="logout" onclick="navigateTo('/logout')">Log out</button>
    <p>Made by Rosa &copy; 2023 - Advanced Object-Oriented Course Project</p>
</footer>

<script>
    function navigateTo(url) {
        window.location.href = url;
    }
</script>

</body>
</html>
