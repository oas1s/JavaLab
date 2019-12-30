<html>

<link rel="stylesheet" type="text/css" href="style.css">
<head><title>Shop</title></head>
<body>
<#if username=="">
    <h1><a href="/auth">SignIn</a></h1>
<#else>
    <h1>Username: ${username}</h1>
</#if>
<table>
    <tr>
        <th>name</th>
        <th>cost</th>
        <#if isAdmin>
            <th>delete</th>
        </#if>

    </tr>
    <#list products as product>
        <tr>
            <td>${product.name}</td>
            <td>${product.cost}</td>
            <#if isAdmin>
                <td>
                    <form action="delProduct?id=${product.id}" method="post">
                        <button>delete</button>
                    </form>
                </td>
            </#if>
        </tr>
    </#list>
</table>

<#if isAdmin>
    <h1>New Product</h1>
    <form action="/newproduct" method="post">
        <div>
            <label> Name
                <input name="name">
            </label>
        </div>
        <div>
            <label>Cost
                <input name="cost">
            </label>
        </div>
        <div>
            <input type="submit">
        </div>
    </form>
</#if>
</body>
</html>