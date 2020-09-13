package org.dwemer.util;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.dwemer.listeners.GallerynessEnforcer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class MessageUtilTest {

    @Mock
    private MessageReceivedEvent event;
    @Mock
    private MessageChannel messageChannel;
    @Mock
    private Message message;
    @Mock
    private Message.Attachment imageAttachment;
    @Mock
    private Message.Attachment nonImageAttachment;

    @Before
    public void initTest() {
        Mockito.when(event.getChannel()).thenReturn(messageChannel);
        Mockito.when(event.getMessage()).thenReturn(message);
        Mockito.when(imageAttachment.isImage()).thenReturn(true);
        Mockito.when(nonImageAttachment.isImage()).thenReturn(false);
    }

    @Test
    public void testIsPostedIntoGallery_yes() {
        //given
        Mockito.when(messageChannel.getName()).thenReturn(GallerynessEnforcer.GALLERY);

        //when
        boolean postedIntoChannel = MessageUtil.isPostedIntoChannel(event, GallerynessEnforcer.GALLERY);

        //then
        assertTrue(postedIntoChannel);
    }

    @Test
    public void testIsPostedIntoGallery_no() {
        //given
        Mockito.when(messageChannel.getName()).thenReturn(GallerynessEnforcer.GALLERY);

        //when
        boolean postedIntoChannel = MessageUtil.isPostedIntoChannel(event, "Shrek");

        //then
        assertFalse(postedIntoChannel);
    }

    @Test
    public void testHasImageAttachments_yes() {
        //given
        ArrayList<Message.Attachment> attachments = new ArrayList<>();
        attachments.add(nonImageAttachment);
        attachments.add(imageAttachment);
        Mockito.when(message.getAttachments()).thenReturn(attachments);

        //when
        boolean hasImageAttachments = MessageUtil.hasImageAttachments(event);

        //then
        assertTrue(hasImageAttachments);
    }

    @Test
    public void testHasImageAttachments_no() {
        //given
        ArrayList<Message.Attachment> attachments = new ArrayList<>();
        attachments.add(nonImageAttachment);
        Mockito.when(message.getAttachments()).thenReturn(attachments);

        //when
        boolean hasImageAttachments = MessageUtil.hasImageAttachments(event);

        //then
        assertFalse(hasImageAttachments);
    }

    @Test
    public void testHasImageLinks_yes() {
        //given
        Mockito.when(message.getContentRaw()).thenReturn("something \n, no one reads this, here is a picture: http://something/image.jpg");

        //when
        boolean hasImageLinks = MessageUtil.hasImageLinks(event);

        //then
        assertTrue(hasImageLinks);
    }

    @Test
    public void testHasImageLinks_no() {
        //given
        Mockito.when(message.getContentRaw()).thenReturn("something \n, no one reads this");

        //when
        boolean hasImageLinks = MessageUtil.hasImageLinks(event);

        //then
        assertFalse(hasImageLinks);
    }
}
