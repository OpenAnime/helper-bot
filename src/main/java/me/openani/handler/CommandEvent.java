package me.openani.handler;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import me.openani.handler.command.Command;
import me.openani.handler.command.CommandContext;

// https://github.com/mnmnr/JDA-Command-Handler

public class CommandEvent extends ListenerAdapter {
    private final CommandHandlerBuilder commandHandlerBuilder;

    CommandEvent(CommandHandlerBuilder commandHandlerBuilder) {
        this.commandHandlerBuilder = commandHandlerBuilder;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getGuild() == null) return;

        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String commandPrefix = commandHandlerBuilder.prefix;

        if (!args[0].startsWith(commandPrefix)) return;

        String command = args[0].replace(commandPrefix, "");

        commandHandlerBuilder.commands.forEach(cmd -> {
            if (cmd.getName().equals(command) || cmd.getAlias().equals(command)) {
                executeCommand(cmd, event.getMember(), event.getTextChannel(), event.getMessage(), args);
            }
        });
    }

    private void executeCommand(Command command, Member member, TextChannel channel, Message message, String[] args) {
        if (message.getAuthor().isBot()) return;

        ArrayList<Permission> perms = command.getPermissions();
        if (!perms.isEmpty() && !member.getPermissions().containsAll(perms)) return;

        CommandContext context = new CommandContext(commandHandlerBuilder, member, channel, message, Arrays.copyOfRange(args, 1, args.length));

        command.getListener().onCommand(context);
    }
}
