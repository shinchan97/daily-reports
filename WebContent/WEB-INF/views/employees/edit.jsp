<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${employee != null}">
                <h2>id : ${employee.id} の従業員情報　編集ページ</h2>
                <p>Enter new password ONLY when you are trying to update it</p>
                <form method="POST" action="<c:url value='/employees/update' />">
                    <c:import url="_form.jsp" />
                </form>

                <p><a href="#" onclick="confirmDestroy();">Delete this employee</a></p>
                <form method="POST" action="<c:url value='/employees/destroy' />">
                    <input type="hidden" name="_token" value="${_token}" />
                </form>
                <script>
                    function confirmDestroy() {
                        if(confirm("Are you sure to delete it?")) {
                            document.forms[1].submit();
                        }
                    }
                </script>
            </c:when>
            <c:otherwise>
                <h2>The data you are looking for is not here</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/employees/index' />">Going back to index</a></p>
    </c:param>
</c:import>