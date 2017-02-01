<#include "nav/menu.ftl">

<#list buses as bus>
<div style="width: 30%; float: left; border: #333 1px solid; margin: 10px; overflow: hidden; overflow-y: scroll; height: 200px;">
    <table cellpadding="0" cellspacing="0">
        <tr style="text-align: left;">
            <th>Bus</th>
            <th>Home</th>
            <th>Start time</th>
        </tr>
        <form action="" method="post">
            <input type="hidden" name="do" value="home"/>
            <input type="hidden" name="bus" value="${bus.getId()}"/>
            <tr style="text-align: left;">
                <td style="background: #eaeaea; padding: 5px;">${bus.getNumber()}</td>
                <td style="background: #eaeaea; padding: 5px;">
                    <select name="station">
                        <option value="0">The home station is ...</option>
                        <#list stations as station>
                            <option value="${station.getId()}">${station.getTitle()}</option>
                        </#list>
                    </select>
                </td>
                <td style="background: #eaeaea; padding: 5px;">
                    <input type="text" name="departure" placeholder="08:00"/>
                </td>
            </tr>
        </form>
        <form action="" method="post">
            <input type="hidden" name="do" value="add"/>
            <input type="hidden" name="bus" value="${bus.getId()}"/>
            <tr>
                <td style="background: #ccc; padding:5px;">
                    <select name="station">
                        <option value="0">The destination ...</option>
                        <#list stations as station>
                            <option value="${station.getId()}">${station.getTitle()}</option>
                        </#list>
                    </select>
                </td>
                <td style="background: #ccc; padding:5px;">
                    <input type="text" name="duration" placeholder="Duration in minutes"/>
                </td>
                <td style="background: #ccc; padding:5px;">
                    <input type="submit" name="down" value="Add"/>
                </td>
            </tr>
        </form>
        <#assign total = 0>
        <#list routes as route>
            <#if route.getBus().getId()==bus.getId()>
                <#assign total = total+route.getDuration()>
                <tr>
                    <td>
                    ${route.getStation().getTitle()}
                    </td>
                    <td>
                    ${route.getDuration()} min
                    </td>
                    <td>
                        <form method="post" style="margin: 0;">
                            <input type="hidden" name="do" value="move"/>
                            <input type="hidden" name="id" value="${route.getId()}"/>
                            <input type="submit" name="operator" value="v"/>
                            <input type="submit" name="operator" value="^"/>
                            <input type="submit" name="operator" value="x"/>
                        </form>
                    </td>
                </tr>
            </#if>
        </#list>
        <tr>
            <td><hr/></td>
            <td style="text-align: center;">Total: ${total} minutes</td>
            <td><hr/></td>
        </tr>
    </table>
</div>
</#list>
