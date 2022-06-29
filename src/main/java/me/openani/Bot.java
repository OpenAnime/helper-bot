package me.openani;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;
import me.openani.handler.CommandHandlerBuilder;
import me.openani.handler.command.CommandBuilder;
import me.openani.events.MessageEvent;
import me.openani.commands.TestCommand;
import me.openani.commands.HelpCommand;

public final class Bot {
    public static JDA jda;

    private Bot() throws LoginException {
        jda = JDABuilder.create(Config.TOKEN,
            GatewayIntent.GUILD_MESSAGES
        )
        .setActivity(Activity.playing("OpenAnime"))
        .addEventListeners(new MessageEvent())
        .build();

        new CommandHandlerBuilder(jda)
            .setPrefix("$")
            .addCommands(
                new CommandBuilder("test", new TestCommand()).setDescription("Test").build(),
                new CommandBuilder("help", new HelpCommand()).setDescription("Help Command").build()
            )
            .build();
    }

    public static void main(String[] args) {
        try {
            new Bot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
