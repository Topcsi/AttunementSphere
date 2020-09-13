package org.dwemer.listeners;

import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;
import org.dwemer.util.MessageUtil;
import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GallerynessEnforcer extends ListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(GallerynessEnforcer.class);

    public static final String GALLERY = "gallery";

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (isLegalPost(event)) {
            return;
        }

        logger.debug(event.getAuthor().getName() + " tried to post \"" + event.getMessage().getContentRaw() + "\" into " + event.getChannel().getName());
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
