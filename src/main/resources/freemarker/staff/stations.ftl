<#include "nav/menu.ftl">


<form action="" method="post">
    <input type="hidden" name="do" value="add"/>
    <table style="padding: 10px; background: #ccc;">
        <tr>
            <td colspan="2">New station</td>
        </tr>
        <tr>
            <td><input type="text" name="title" placeholder="Title..." style="width: 100%;"/></td>
            <td style="width: 1%;"><input type="SUBMIT" value="Add"/></td>
        </tr>
        <#list list as row>
            <tr style="background: #eaeaea;">
                <td>${row.getTitle()}</td>
                <td><a href="?do=del&id=${row.getId()}" style="float: right;">del</a></td>
            </tr>
        </#list>
    </table>
</form>
