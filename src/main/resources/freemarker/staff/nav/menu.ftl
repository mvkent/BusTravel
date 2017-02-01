<link href="/assets/css/main.css" rel="stylesheet" />
<#if staff??>
<table style="width: 100%;" class="head">
    <tr>
        <td>
            <ul class="menu" style="display: inline-flex; list-style: none; padding: 0; margin: 0;">
                <li>&nbsp;<a href="/staff/stations">Station</a>&nbsp;</li>
                <li>&nbsp;<a href="/staff/routes">Routes</a>&nbsp;</li>
                <li>&nbsp;<a href="/staff/buses">Bus</a>&nbsp;</li>
                <li>&nbsp;<a href="/staff/passengers">Passengers</a>&nbsp;</li>
                <li>&nbsp;<a href="/staff/tickets">Tickets</a>&nbsp;</li>
            </ul>
        </td>
        <td style="width: 1%; white-space: nowrap;">
            <a href="/staff">${staff.getName()}</a> <a href="/auth/logout">Logout</a>
        </td>
    </tr>
</table>
<#else>
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
</#if>
