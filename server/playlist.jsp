<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags/global" %>

<c:set var="_galleryArticle" value="${item.content}" scope="request"/>
<c:set var="_gallerySummary" value="${item}" scope="request"/>
<c:set var="_galleryFull" value="true" scope="request"/>
<c:set var="_galleryDefaultAppendJs" value="true" scope="request"/>

<g:include page="${template}elements/gallery.jsp"/>