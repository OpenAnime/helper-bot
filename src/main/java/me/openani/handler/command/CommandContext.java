package me.openani.handler.command;

import me.openani.handler.CommandHandlerBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandContext {
    private CommandHandlerBuilder commandHandler;
    public Member member;
    public TextChannel channel;
    public Message message;
    public String[] args;

    public CommandContext(CommandHandlerBuilder commandHandler, Member member, TextChannel channel, Message message, String[] args) {
        this.commandHandler = commandHandler;
        this.member = member;
        this.channel = channel;
        this.message = message;
        this.args = args;
    }

    public CommandHandlerBuilder getCommandHandler() {
        return commandHandler;
    }
}
