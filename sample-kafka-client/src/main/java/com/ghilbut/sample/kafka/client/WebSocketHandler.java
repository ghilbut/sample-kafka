package com.ghilbut.sample.kafka.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Coupang on 2016. 12. 28..
 */
@Slf4j
@Controller
public class WebSocketHandler extends TextWebSocketHandler {

	private static WebSocketHandler self = new WebSocketHandler();

	private final Set<WebSocketSession> sessions = new HashSet<>();

	public static WebSocketHandler instance() {
		return self;
	}

	private WebSocketHandler() {
		// nothing
	}

	public void send(String message) throws IOException {
		for (WebSocketSession session : sessions) {
			session.sendMessage(new TextMessage(message));
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//final String name = message.getPayload();
		//log.info("[My WebSocket Message]: {}", name);
		//session.sendMessage(new TextMessage(name.getBytes(StandardCharsets.UTF_8)));
		log.warn("[WebSocketHandler]: {} ({})", "Not supported!!", message);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}
}
