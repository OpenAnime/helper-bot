package me.openani.commands;

import org.json.JSONObject;
import me.openani.handler.command.Command;
import me.openani.handler.command.CommandContext;
import me.openani.handler.listener.CommandListener;

public class HelpCommand implements CommandListener {
    @Override
    public void onCommand(CommandContext context) {
        JSONObject commands = new JSONObject();

        for (Command command : context.getCommandHandler().getCommands()) {
            commands.put(command.getName(), command.getDescription());
        }

        String help = "```json\n" + commands.toString(4) + "```";

        context.channel.sendMessage(help).queue();
    }
}
