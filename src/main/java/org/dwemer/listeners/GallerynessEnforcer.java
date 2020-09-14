package org.dwemer.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import org.dwemer.util.MessageUtil;
import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GallerynessEnforcer extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(GallerynessEnforcer.class);

    public static final String GALLERY = "gallery";

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (isLegalPost(event)) {
            return;
        }

        logger.debug("{} tried to post \"{}\" into {}", event.getAuthor().getName(), event.getMessage().getContentRaw(), event.getChannel().getName());
        String messageId = event.getMessageId();
        AuditableRestAction<Void> deleteAction = event.getChannel().deleteMessageById(messageId);
        deleteAction.queue();
    }

    private static boolean isLegalPost(MessageReceivedEvent event) {
        return !MessageUtil.isPostedIntoChannel(event, GALLERY) ||
                MessageUtil.hasImageAttachments(event) ||
                MessageUtil.hasImageLinks(event);
    }
}
