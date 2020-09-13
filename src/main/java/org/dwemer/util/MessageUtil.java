package org.dwemer.util;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MessageUtil {

    // Whoever came up with this regex, you have no life. But thanks.
    public static final String IMAGE_URI_PATTERN = "(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:jpg|jpeg|gif|png))(?:\\?([^#]*))?(?:#(.*))?";

    private MessageUtil() {}

    public static boolean isPostedIntoChannel(MessageReceivedEvent event, String channelName) {
        MessageChannel channel = event.getChannel();
        return channel.getName().contains(channelName);
    }

    public static boolean hasImageAttachments(MessageReceivedEvent event) {
        List<Message.Attachment> attachments = event.getMessage().getAttachments();
        return attachments.stream().anyMatch(Message.Attachment::isImage);
    }

    public static boolean hasImageLinks(MessageReceivedEvent event) {
        Pattern pattern = Pattern.compile(IMAGE_URI_PATTERN);
        String contentRaw = event.getMessage().getContentRaw(); //If the message is just an image with no text, contentRaw will be empty, not null.
        Matcher matcher = pattern.matcher(contentRaw);
        return matcher.find();
    }


}
