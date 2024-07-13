package com.tutor.auth0r.config;

import com.tutor.auth0r.security.AuthoritiesConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebsocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
            .simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.HEARTBEAT, SimpMessageType.UNSUBSCRIBE, SimpMessageType.DISCONNECT)
            .permitAll()
            .nullDestMatcher()
            .authenticated()
            .simpDestMatchers("/topic/tracker")
            .hasAuthority(AuthoritiesConstants.ADMIN)
            .simpDestMatchers("/topic/chat")
            .permitAll()
            .simpDestMatchers("/app/**")
            .permitAll()
            // matches any destination that starts with /topic/
            // (i.e. cannot send messages directly to /topic/)
            // (i.e. cannot subscribe to /topic/messages/* to get messages sent to
            // /topic/messages-user<id>)
            .simpDestMatchers("/topic/**")
            .permitAll()
            // message types other than MESSAGE and SUBSCRIBE
            // .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE)
            // .denyAll()
            // catch all
            .anyMessage()
            .permitAll();
    }

    /**
     * Disables CSRF for Websockets.
     */
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
