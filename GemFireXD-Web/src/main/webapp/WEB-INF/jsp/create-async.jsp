
<%--
Copyright (C) MARCH-2014 Pivotal Software, Inc.

All rights reserved. This program and the accompanying materials
are made available under the terms of the under the Apache License,
Version 2.0 (the "License?); you may not use this file except in compliance
with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="../css/isqlfire.css" />
    <link rel="stylesheet" type="text/css" href="../css/print.css" media="print" />
    <script src="../js/functions.js" type="text/javascript"></script>
    <SCRIPT language=Javascript>
        <!--
        function isNumberKey(evt)
        {
            var charCode = (evt.which) ? evt.which : event.keyCode
            if (charCode > 31 && (charCode < 48 || charCode > 57))
                return false;

            return true;
        }

        function validate()
        {
            if (document.getElementById("asyncname").value == "")
            {
                alert("Gateway Sender Name Cannot Be Empty");
                return false;
            }
            else if (document.getElementById("servergroup").value == "")
            {
                alert("Server Groups Cannot Be Empty");
                return false;
            }
            else
            {
                return true;
            }
        }
        //-->
    </SCRIPT>
    <title><fmt:message key="sqlfireweb.appname" /> Create Async Event Listener</title>
</head>
<body>

<h2><fmt:message key="sqlfireweb.appname" /> Create Async Event Listener</h2>

<jsp:include page="toolbar.jsp" flush="true" />

<p>
    Installs an AsyncEventListener implementation to GemFire XD peers in a specified server group.
</p>

<c:if test="${!empty result}">
    <fieldset>
        <legend>Result</legend>
        <table class="formlayout">
            <tr>
                <td align="right">Command:</td>
                <td>${result.command} </td>
            </tr>
            <tr>
                <td align="right">Message:</td>
                <td>
                    <font color="${result.message == 'SUCCESS' ? 'green' : 'red'}">
                            ${result.message}
                    </font>
                </td>
            </tr>
        </table>
    </fieldset>
</c:if>

<c:if test="${!empty sql}">
    <div class="success">
        Successfully generated SQL
    </div>
    <h3>Create Async Event Listener SQL </h3>
    <table id="table_results" class="data">
        <tbody>
        <tr class="odd">
            <td><pre>${sql}</pre></td>
        </tr>
        </tbody>
    </table>
    <br />
</c:if>

<h3>Async Event Listener Properties</h3>

<form:form action="createasync" method="POST" modelAttribute="asyncAttribute" onsubmit="return validate()">
<table>
    <thead>
    <tr>
        <th colspan="3">Async Event Listener Properties</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
        <tr class="even">
            <td align="right">Async Event Name</td>
            <td>
                <form:input type="text" path="asyncName" size="20" maxlength="30" id="asyncname" />
            </td>
            <td>(Required.) A unique identifier for the async event listener. </td>
        </tr>
        <tr class="odd">
            <td align="right">Listener Class</td>
            <td>
                <form:input type="text" path="listenerClass" size="20" maxlength="300" value="com.vmware.sqlfire.callbacks.DBSynchronizer" />
            </td>
            <td>(Required.) The fully-qualified name of the class that implements the AsyncEventListener interface.  </td>
        </tr>
        <tr class="even">
            <td align="right">Init Parameters</td>
            <td>
                <form:input type="text" path="initParams" size="20" maxlength="300" />
            </td>
            <td>(Required.) Specify initialization parameters to pass to the init() method of the AsyncEventListener implementation.  </td>
        </tr>
        <tr class="odd">
            <td align="right">Manual Start?</td>
            <td>
                <form:select path="manualStart">
                    <c:choose>
                        <c:when test="${asyncAttribute.manualStart == 'FALSE'}">
                            <form:option value="FALSE" selected="true" />
                        </c:when>
                        <c:otherwise>
                            <form:option value="FALSE" />
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${asyncAttribute.manualStart == 'TRUE'}">
                            <form:option value="TRUE" selected="true" />
                        </c:when>
                        <c:otherwise>
                            <form:option value="TRUE" />
                        </c:otherwise>
                    </c:choose>
                </form:select>
            </td>
            <td>Value of "true" or "false" that specifies whether you need to manually start the gateway sender</td>
        </tr>
        <tr class="even">
            <td align="right">Enable Batch Conflation?</td>
            <td>
                <form:select path="enableBatchConflation">
                    <c:choose>
                        <c:when test="${asyncAttribute.enableBatchConflation == 'FALSE'}">
                            <form:option value="FALSE" selected="true" />
                        </c:when>
                        <c:otherwise>
                            <form:option value="FALSE" />
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${asyncAttribute.enableBatchConflation == 'TRUE'}">
                            <form:option value="TRUE" selected="true" />
                        </c:when>
                        <c:otherwise>
                            <form:option value="TRUE" />
                        </c:otherwise>
                    </c:choose>
                </form:select>
            </td>
            <td>A Boolean value that determines whether GemFire XD should conflate messages. The default is false</td>
        </tr>
        <tr class="odd">
            <td align="right">Batch Size</td>
            <td>
                <form:input type="text" path="batchSize" size="20" maxlength="50" value="100" onkeypress="return isNumberKey(event);" />
            </td>
            <td>The maximum number of messages that a batch can contain. The default is 100 messages</td>
        </tr>
        <tr class="even">
            <td align="right">Batch Time Interval</td>
            <td>
                <form:input type="text" path="batchTimeInterval" size="20" maxlength="50" value="1000" onkeypress="return isNumberKey(event);" />
            </td>
            <td>The maximum number of milliseconds that can elapse between sending batches. The default is 1000 milliseconds</td>
        </tr>
        <tr class="odd">
            <td align="right">Enable Persistence?</td>
            <td>
                <form:select path="enablePersistence">
                    <c:choose>
                        <c:when test="${asyncAttribute.enablePersistence == 'FALSE'}">
                            <form:option value="FALSE" selected="true" />
                        </c:when>
                        <c:otherwise>
                            <form:option value="FALSE" />
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${asyncAttribute.enablePersistence == 'TRUE'}">
                            <form:option value="TRUE" selected="true" />
                        </c:when>
                        <c:otherwise>
                            <form:option value="TRUE" />
                        </c:otherwise>
                    </c:choose>
                </form:select>
            </td>
            <td>A Boolean value that determines whether GemFire XD persists the gateway queue. The default is "False."</td>
        </tr>
        <tr class="even">
            <td align="right">Disk Store</td>
            <td>
                <form:select path="diskStore">
                    <form:option value="" />
                    <c:forEach var="entry" varStatus="loop" items="${diskstores}">
                        <c:choose>
                            <c:when test="${asyncAttribute.diskStore == entry['name']}">
                                <form:option value="${entry['name']}" selected="true" />
                            </c:when>
                            <c:otherwise>
                                <form:option value="${entry['name']}" />
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </td>
            <td>Leave blank to use default DiskStore</td>
        </tr>
        <tr class="odd">
            <td align="right">Max Queue Memory</td>
            <td>
                <form:input type="text" path="maxQueueMemory" size="20" maxlength="50" value="100" onkeypress="return isNumberKey(event);" />
            </td>
            <td>The maximum amount of memory in megabytes that the queue can consume before overflowing to disk. The default is 100 megabytes</td>
        </tr>
        <tr class="even">
            <td align="right">Alert Threshold</td>
            <td>
                <form:input type="text" path="alertThreshold" size="20" maxlength="50" value="0" onkeypress="return isNumberKey(event);" />
            </td>
            <td>The maximum number of milliseconds that an event can remain in this gateway queue before GemFire XD logs an alert. The default is value is "0."</td>
        </tr>
        <tr class="odd">
            <td align="right">Server Groups</td>
            <td>
                <form:input type="text" path="serverGroups" size="20" maxlength="300" id="servergroup"  />
            </td>
            <td>A comma-separated String of server group names. Eg: group1, group2 </td>
        </tr>
    </tbody>
</table>
<br />
<input type="submit" value="Create" name="pSubmit" />
<input type="submit" value="Show SQL" name="pSubmit" />
<input type="submit" value="Save to File" name="pSubmit" />
</form:form>

<br />

<jsp:include page="footer.jsp" flush="true" />

</body>
</html>