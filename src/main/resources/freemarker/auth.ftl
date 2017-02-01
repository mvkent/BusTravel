<div style="position: absolute; left: 40%; top: 25%;">
<#if errors??>
    <#list errors as x>
    ${x}
    </#list>
</#if>


    <form action="/auth/login" method="post" enctype='application/x-www-form-urlencoded'>
        <table>
            <tr>
                <td><input type="text" name="login" value="oleg" placeholder="Login"/></td>
                <td>-</td>
                <td><input type="password" name="pwd" value="oleg123" placeholder="Password"/></td>
                <td>&nbsp;</td>
                <td><input type="submit" value="Вход"/></td>
            </tr>
        </table>
    </form>
</div>