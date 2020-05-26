<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" required="true" type="org.springframework.data.domain.Page" %>
<%@ attribute name="url" required="true" %>

<%--Number of page numbers to display at once.--%>
<%@ attribute name="size" required="false" %>

<c:set var="size" value="${empty size ? 10: size}"/>

<c:set var="block" value="${empty param.b ? 0: param.b}" />

<%--This is the debug output for testing purposes--%>
<%--<p>Block: ${block}</p>--%>
<%--<p>Size: ${size}</p>--%>

<c:set var="startPage" value="${block * size + 1}"/>
<c:set var="endPage" value="${(block + 1) * size}"/>
<c:set var="endPage" value="${endPage > page.totalPages ? page.totalPages : endPage}"/>

<%--This is the debug output for testing purposes--%>
<%--<p>Start Page: ${startPage}</p>--%>
<%--<p>End Page: ${endPage}</p>--%>

<c:if test="${page.totalPages != 1}"> <%--This line of code will help us to avoid displaying page numbers if there is only one status--%>
    <div class="pagination">

        <c:if test="${block != 0}">
            <a href="${url}?b=${block-1}&p=${(block-1)*size + 1}">&lt;&lt;</a>
        </c:if>

            <%--'page' is the class object--%>
        <c:forEach var="pageNumber" begin="${startPage}" end="${endPage}">

            <%--The following 'choose' block makes the current page number--%>
            <%--become different--%>
            <c:choose>
                <c:when test="${page.number != pageNumber - 1}">
                    <a href="${url}?p=${pageNumber}&b=${block}"><c:out value="${pageNumber}"/></a>
                </c:when>

                <c:otherwise>
                    <strong><c:out value="${pageNumber}"/></strong>
                </c:otherwise>
            </c:choose>

            <c:if test="${pageNumber != endPage}">
                |
            </c:if>
        </c:forEach>

        <c:if test="${endPage != page.totalPages}">
            <a href="?b=${block+1}&p=${(block+1)*size + 1}">&gt;&gt;</a>
        </c:if>
    </div>
</c:if>