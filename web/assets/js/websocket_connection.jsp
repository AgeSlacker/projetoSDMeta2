<%@ taglib prefix="s" uri="/struts-tags" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@ page contentType="text/javascript;charset=UTF-8" language="java" %>

    var websocket = null;
    window.onload = function () {
        connect("wss://" + window.location.host + "/ws");
    }

    function connect(host) { // connect to the host websocket servlet
        if ("WebSocket" in window) websocket = new WebSocket(host);
        else if ("MozWebSocket" in window) websocket = new MozWebSocket(host);
        else {
            // TODO error
            return;
        }
        websocket.onopen = onOpen;
        websocket.onclose = onClose;
        websocket.onmessage = onMessage;
        websocket.onerror = onError;
    }

    function onOpen(event) {
        websocket.send("<s:property value="ClientBean.name"></s:property>");
        return;
    }

    function onClose(event) {
        return;
    }

    function onMessage(message) {
        var tokenized = message.data.split("|");
        var tag = tokenized[0];
        var data = tokenized[1];
        if (tag == "TOP_PAGES") {
            var topPagesTable = document.getElementById("pagesTable");
            if (topPagesTable != null)
                topPagesTable.innerHTML = data;
        } else if (tag == "TOP_SEARCHES") {
            var topSearchesTable = document.getElementById("searchesTable");
            if (topSearchesTable != null) {
                topSearchesTable.innerHTML = data;
            }
        } else if (tag == "ACTIVE_SERVERS") {
            var multicastServersTable = document.getElementById("multicastServersTable");
            if (multicastServersTable != null) {
                multicastServersTable.innerHTML = data;
            }
        } else if (tag == "NOTIFICATION") {
            alert(data);
        }
        return;
    }

    function onError(event) {
        return;
    }

    function doSend() {
        var message = document.getElementById("chat ").value;
        if (message != "")
            websocket.send(message); // send the message document . getElementById (’chat ’). value = ’’;
    }