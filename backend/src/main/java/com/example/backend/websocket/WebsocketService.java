package com.example.backend.websocket;

import jakarta.ejb.*;
import jakarta.websocket.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class WebsocketService {
    //logger
    private static final Logger logger = Logger.getLogger(WebsocketService.class.getName());
    private static final Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
    private static final Map<String, List<String>> pendingMessages = new ConcurrentHashMap<>();

    public static void addSession(String username, Session session) {
        onlineSessions.put(username, session);
        logger.log(Level.INFO, "Session added for user: {0}", username);
        sendPendingMessages(username);
        logger.log(Level.INFO, "Pending messages sent to user: {0}", username);
    }

    public static void removeSession(String username) {
        onlineSessions.remove(username);
        logger.log(Level.INFO, "Session removed for user: {0}", username);
    }

    public static void sendNotification(String username, String message) {
        var session = onlineSessions.get(username);
        if (session != null) {
            logger.log(Level.INFO, "Sending notification to user: {0} - {1}", new Object[]{username, message});
            session.getAsyncRemote().sendText(message);
        } else {
            pendingMessages.computeIfAbsent(username, k -> new ArrayList<>()).add(message);
        }
    }

    public static void sendPendingMessages(String username) {
        var session = onlineSessions.get(username);
        if (session != null) {
            var messages = pendingMessages.get(username);
            if (messages != null) {

                for (var message : messages) {
                    session.getAsyncRemote().sendText(message);
                    logger.log(Level.INFO, "Sent [{0}] Pending message to user: {1} - {2}", new Object[]{messages.indexOf(message), username, message});
                }
                pendingMessages.remove(username);
            }
        }
    }


    public static void logError(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }
}
