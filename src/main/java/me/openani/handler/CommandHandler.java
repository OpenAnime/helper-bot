package me.openani.handler;

import me.openani.handler.command.Command;

// https://github.com/mnmnr/JDA-Command-Handler

public class CommandHandler {
    public CommandHandlerBuilder commandHandlerBuilder;
    
    CommandHandler(CommandHandlerBuilder commandHandlerBuilder) {
        this.commandHandlerBuilder = commandHandlerBuilder;

        commandHandlerBuilder.setCommandEventListener(new CommandEvent(commandHandlerBuilder));
    }

    public void addCommand(Command command) {
        this.commandHandlerBuilder.commands.add(command);
    }
}
