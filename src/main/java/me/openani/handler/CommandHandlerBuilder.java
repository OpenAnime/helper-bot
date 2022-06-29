package me.openani.handler;

import java.util.ArrayList;
import me.openani.handler.command.Command;
import net.dv8tion.jda.api.JDA;

// https://github.com/mnmnr/JDA-Command-Handler

public class CommandHandlerBuilder {
    protected ArrayList<Command> commands = new ArrayList<>();
    JDA jdaObject;
    String prefix;

    public CommandHandlerBuilder(JDA jdaObject) {
        if (jdaObject == null) {
            throw new IllegalArgumentException("JDA object cannot be null");
        }

        this.jdaObject = jdaObject;
    }

    public CommandHandlerBuilder setPrefix(String prefix) {
        this.prefix = prefix;

        return this;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public CommandHandlerBuilder addCommand(Command command) {
        commands.add(command);

        return this;
    }

    public CommandHandlerBuilder addCommands(Command... commands) {
        for (Command command : commands) {
            addCommand(command);
        }

        return this;
    }

    public CommandHandler build() {
        return new CommandHandler(this);
    }
}
