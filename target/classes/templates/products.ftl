<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Products</title>
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

        .add_to_cart {
            width: 150px;
        }

        select {
            padding: 8px;
            margin-bottom: 10px;
        }

        .delete {
            background-color: #e74c3c;
        }

        .update {
            background-color: #3cc8e7;
        }
    </style>
</head>
<body>
<h1>View Products</h1>

<button onclick="navigateTo('/')"
        style="background-color: #4CAF50; color: white; border: none; padding: 10px 16px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; cursor: pointer; border-radius: 4px;">
    Back
</button>

<br>

<label for="searchBar">Search by Description:</label>
<input type="text" id="searchBar" placeholder="Enter description..." style="width: 300px;height: 35px;"/>

<button onclick="searchProducts()">
    Search
</button>

<br>

<label for="categoryFilter">Filter by Category:</label>
<select id="categoryFilter" onchange="filterProducts()">
    <option value="all" <#if selectedCategory == "all">selected</#if>>All</option>
    <#list uniqueCategories as category>
        <option value="${category}" <#if selectedCategory == category>selected</#if>>${category}</option>
    </#list>
</select>

<br>

<label for="expiryFilter">Filter by Expiry Status:</label>
<select id="expiryFilter" onchange="filterProducts()">
    <option value="all" <#if selectedExpiry == "all">selected</#if>>All</option>
    <option value="expired" <#if selectedExpiry == "expired">selected</#if>>Expired</option>
    <option value="non-expired" <#if selectedExpiry == "non-expired">selected</#if>>Non-Expired</option>
</select>

<table>
    <thead>
    <tr>
        <th></th>
        <th>ID</th>
        <th>SKU</th>
        <th>Description</th>
        <th>Price</th>
        <th>Category</th>
        <th>Expiry Date</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <#list products as product>
        <#if selectedCategory == "all" || selectedCategory == product.category || selectedCategory == "All">
            <tr>
                <td>
                    <input type="checkbox" class="product-checkbox" value="${product.id}"
                           onclick="handleCheckboxClick(this)">
                </td>
                <td>${product.id}</td>
                <td>
                    <#if product.sku?has_content>${product.sku}<#else>N/A</#if>
                </td>
                <td>${product.description}</td>
                <td>${product.price}</td>
                <td>${product.category}</td>
                <td>${product.expiryDate}</td>
                <td>
                    <a class="update" href="/product/${product.id}/update">Edit</a>
                    <a class="delete" href="/product/${product.id}/delete">Delete</a>
                </td>
            </tr>
        </#if>
    </#list>
    </tbody>
</table>

<br/>


<form id="addToCartForm" action="/cart" method="post">
    <input type="hidden" id="checkedItemsInput" name="checkedItems" value="">
    <button type="submit" class="add_to_cart">
        Go to Cart
    </button>
</form>

<script>
    var checkedItems = [];

    function filterProducts() {
        var selectedCategory = document.getElementById("categoryFilter").value;
        var selectedExpiry = document.getElementById("expiryFilter").value;

        var url = "/products?category=" + encodeURIComponent(selectedCategory) +
            "&expiry=" + encodeURIComponent(selectedExpiry);

        window.location.href = url;
    }

    function navigateTo(url) {
        window.location.href = url;
    }

    function searchProducts() {
        var searchInput = document.getElementById("searchBar");
        var searchQuery = searchInput.value;

        if (searchQuery.trim() !== "") {
            window.location.href = "/products?search=" + encodeURIComponent(searchQuery);
        }
    }

    function handleCheckboxClick(checkbox) {

        if (checkbox.checked) {
            checkedItems.push(checkbox.value);
        } else {
            var index = checkedItems.indexOf(checkbox.value);
            if (index !== -1) {
                checkedItems.splice(index, 1);
            }
        }
        document.getElementById("checkedItemsInput").value = checkedItems.join(",");
    }

</script>

</body>
</html>
