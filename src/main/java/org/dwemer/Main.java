package org.dwemer;

import net.dv8tion.jda.api.JDABuilder;
import org.dwemer.listeners.GallerynessEnforcer;
import org.dwemer.util.PropertyProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws LoginException, InterruptedException {
        logger.info("Starting the bot...");
        String discordToken = PropertyProvider.getProperty(PropertyProvider.DISCORD_TOKEN);
        if (discordToken == null) {
            throw new IllegalStateException("'" + PropertyProvider.DISCORD_TOKEN + "' must be set as a system or env property!");
        }
        JDABuilder jdaBuilder = JDABuilder.createDefault(discordToken);

        registerEventHandlers(jdaBuilder);
        logger.info("Event handlers successfully registered!");

        jdaBuilder.build().awaitReady();
    }

    private static void registerEventHandlers(JDABuilder jdaBuilder) {
        GallerynessEnforcer gallerynessEnforcer = new GallerynessEnforcer();

        jdaBuilder.addEventListeners(gallerynessEnforcer);
    }

}
