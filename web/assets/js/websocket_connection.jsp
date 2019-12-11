<%@ taglib prefix="s" uri="/struts-tags" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@ page contentType="text/javascript;charset=UTF-8" language="java" %>

    var websocket = null;
    window.onload = function () {
        connect("ws://" + window.location.host + "/projetoSDMeta2/ws");
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
        alert(message.data);
        console.log(message.data);
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