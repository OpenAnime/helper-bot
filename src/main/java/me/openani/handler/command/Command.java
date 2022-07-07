package me.openani.handler.command;

import net.dv8tion.jda.api.Permission;
import java.util.ArrayList;
import me.openani.handler.listener.CommandListener;

// https://github.com/mnmnr/JDA-Command-Handler

public class Command {
    private CommandBuilder commandBuilder;

    public Command(CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    public String getName() {
        return commandBuilder.commandName;
    }

    public String getAlias() {
        return commandBuilder.commandAlias;
    }

    public String getDescription() {
        return commandBuilder.commandDescription;
    }

    public ArrayList<Permission> getPermissions() {
        return commandBuilder.commandPermissions;
    }

    public CommandListener getListener() {
        return commandBuilder.handlerListener;
    }
}
