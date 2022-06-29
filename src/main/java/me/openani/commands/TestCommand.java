package me.openani.commands;

import me.openani.handler.listener.CommandListener;
import me.openani.handler.command.CommandContext;

public class TestCommand implements CommandListener {
    @Override
    public void onCommand(CommandContext context) {
        context.channel.sendMessage("Hello!").queue();
    }
}
