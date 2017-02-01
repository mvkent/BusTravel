<form action="" method="post">
    <input type="hidden" name="action" value="find"/>
    <table>
        <tr>
            <td>
                <input type="text" name="tm1" placeholder="8:00" maxlength="5" width="40"/>
                <select name="from" onchange="form.submit()">
                    <option value="0">From ...</option>
                <#list stations as obj>
                    <option
                            value="${obj.getId()}"
                            <#if (from == obj.getId())> selected</#if>
                    >
                    ${obj.getTitle()}
                    </option>
                </#list>
                </select>
            </td>
            <td>
                <hr/>
            </td>
            <td>
                <input type="text" name="tm2" placeholder="15:00" maxlength="5" width="40"/>
                <select name="to" onchange="form.submit()">
                    <option value="0">To ...</option>
                <#list stations as obj>
                    <option
                            value="${obj.getId()}"
                            <#if (to == obj.getId())>selected</#if>
                    >
                    ${obj.getTitle()}
                    </option>
                </#list>
                </select>
            </td>
            <td>
                <input type="submit" value="Find"/>
            </td>
        </tr>
    </table>
</form>


<#list buses as bus>
    <div style="width: 30%; float: left; border: #333 1px solid; margin: 10px; overflow: hidden; overflow-y: scroll; height: 200px;">
    ${bus.getNumber()}
        <#list routes as route>
            <ul>
                <#if route.getBus().getId() == bus.getId()>
                    <li>
                        <#if route.isSelected()>
                            <b>${route.getStation().getTitle()} - ${route.getDuration()} min.</b>
                        <#else>
                            ${route.getStation().getTitle()} - ${route.getDuration()} min.
                        </#if>
                    </li>
                </#if>
            </ul>
        </#list>
    </div>
</#list>


