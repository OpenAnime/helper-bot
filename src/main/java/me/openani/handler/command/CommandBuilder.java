package me.openani.handler.command;

import java.security.Permission;
import java.util.ArrayList;
import me.openani.handler.listener.CommandListener;

// https://github.com/mnmnr/JDA-Command-Handler

public class CommandBuilder {
    String commandName;
    String commandAlias = "";
    String commandDescription;
    ArrayList<Permission> commandPermissions = new ArrayList<>();
    CommandListener handlerListener;

    public CommandBuilder(String commandName, CommandListener handlerListener) {
        if (commandName == null || handlerListener == null) {
            throw new IllegalArgumentException("Komut ismi boş olmamalıdır");
        }

        this.commandName = commandName;
        this.handlerListener = handlerListener;
    }

    public CommandBuilder setDescription(String commandDescription) {
        this.commandDescription = commandDescription;

        return this;
    }

    public CommandBuilder addPermission(Permission permission) {
        commandPermissions.add(permission);

        return this;
    }

    public Command build() {
        return new Command(this);
    }
}
