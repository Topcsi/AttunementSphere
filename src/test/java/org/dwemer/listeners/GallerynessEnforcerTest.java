package org.dwemer.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GallerynessEnforcerTest {

    private static final String ID = "id";

    @Mock
    private MessageReceivedEvent event;
    @Mock
    private MessageChannel messageChannel;
    @Mock
    private Message message;
    @Mock
    private AuditableRestAction<Void> auditableRestAction;
    @Mock
    private User user;

    private GallerynessEnforcer gallerynessEnforcer;

    @Before
    public void initTest() {
        Mockito.when(event.getChannel()).thenReturn(messageChannel);
        Mockito.when(event.getMessage()).thenReturn(message);
        Mockito.when(event.getMessageId()).thenReturn(ID);
        Mockito.when(event.getAuthor()).thenReturn(user);
        Mockito.when(user.getName()).thenReturn("I_post_wherever_I_want");
        Mockito.when(messageChannel.getName()).thenReturn("A_channel_where_text_messages_are_illegal");
        Mockito.when(messageChannel.deleteMessageById(ID)).thenReturn(auditableRestAction);

        gallerynessEnforcer = new GallerynessEnforcer();
    }

    @Test
    public void testOnMessageReceived_illegal() {
        //given
        Mockito.when(messageChannel.getName()).thenReturn("test" + GallerynessEnforcer.GALLERY);
        Mockito.when(message.getContentRaw()).thenReturn("no image linked");

        //when
        gallerynessEnforcer.onMessageReceived(event);

        //then
        Mockito.verify(messageChannel, Mockito.times(1)).deleteMessageById(ID);
        Mockito.verify(auditableRestAction, Mockito.times(1)).queue();
    }

    @Test
    public void testOnMessageReceived_legal() {
        //given
        Mockito.when(messageChannel.getName()).thenReturn("discussion");

        //when
        gallerynessEnforcer.onMessageReceived(event);

        //then
        Mockito.verify(messageChannel, Mockito.never()).deleteMessageById(Mockito.anyString());
    }
}
