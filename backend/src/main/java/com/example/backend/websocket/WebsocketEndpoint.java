package com.example.backend.websocket;

import com.example.backend.security.Authenticated;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/alerts/{username}")
public class WebsocketEndpoint {
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        session.getUserProperties().put("username", username);
        session.getUserProperties().put("Access-Control-Allow-Origin", "http://localhost:5173");
        WebsocketService.addSession(username, session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        // Do nothing
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        WebsocketService.removeSession(username);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        WebsocketService.logError("Error on session " + session.getId(), throwable);
        throwable.printStackTrace();
    }
}
