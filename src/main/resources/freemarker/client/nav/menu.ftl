<link href="/assets/css/main.css" rel="stylesheet" />
<table style="width: 100%;" class="head">
    <tr>
        <td>
            <ul class="menu">
                <li><a href="/">home</a></li>
            </ul>
        </td>
        <td style="width: 1%; white-space: nowrap;">
            <#if !staff??>
                <form action="/auth/login" method="post">
                    <table>
                        <tr>
                            <td><input type="text" name="login" placeholder="Login"/></td>
                            <td>-</td>
                            <td><input type="password" name="pwd" placeholder="Password"/></td>
                            <td>&nbsp;</td>
                            <td><input type="submit" value="Вход"/></td>
                        </tr>
                    </table>
                </form>
            <#else>
                <a href="/staff">${staff.getName()}</a> <a href="/auth/logout">Logout</a>
            </#if>
        </td>
    </tr>
</table>
