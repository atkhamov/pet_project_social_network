<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="row">
    <div class="col-md-8 col-md-offset-2">

        <%--        Below code was made for debugging purposes--%>
        <%--        Request statusUpdate attribute: <%= request.getAttribute("statusUpdate")%><br/>--%>
        <%--        JSP object: <%= this %><br/>--%>
        <%--        JSP class: <%= this.getClass() %><br/>--%>

        <div class="panel panel-default">

            <div class="panel-heading">
                <div class="panel-title">Add a Status Update</div>
            </div>

            <%--@elvariable id="statusUpdate" type=""--%>
            <form:form modelAttribute="statusUpdate">
                <div class="errors">
                    <form:errors path="text"/>
                </div>
                <div class="form-group">
                        <%--In the 'path' below we indicated "text", which means--%>
                        <%--that we are using 'setText' method of the StatusUpdate.class.--%>
                        <%--Spring automatically adds 'set...' word into the 'path'.--%>
                        <%--In general we are invoking 'setText()' method of the StatusUpdate.class--%>
                    <form:textarea path="text" name="text" rows="10" cols="50"></form:textarea>
                </div>
                <input type="submit" name="submit" value="Add Status"/>
            </form:form>


        </div>

        <%--Display Panel--%>
        <div class="panel panel-default">
            <div class="panel-heading">
                <%--Below we are using 'added' attribute, however, it is 'getAdded()' method of the StatusUpdate.class--%>
                <div class="panel-title">Status update added on: <fmt:formatDate pattern="EEEE d MMMM y 'at' H:mm:ss" value="${latestStatusUpdate.added}"/></div>
            </div>

            <div class="panel-body">
                <%--Below we are using 'text' attribute, however, it is 'getText()' method of the StatusUpdate.class--%>
                ${latestStatusUpdate.text}
            </div>
        </div>

    </div>



</div>

<script src='//cdn.tinymce.com/4/tinymce.min.js'></script>
<script>
    tinymce.init({
        selector: 'textarea',
        plugins: 'link'
    })
</script>