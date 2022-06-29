package me.openani.commands

import org.json.JSONObject
import me.openani.handler.command.CommandContext
import me.openani.handler.listener.CommandListener

class HelpCommand: CommandListener {
    public override fun onCommand(context: CommandContext) {
        val commands = JSONObject()

        for (cmd in context.commandHandler.commands) {
            commands.put(cmd.name, cmd.description)
        }

        val result = "```json\n${commands.toString(4)}```"

        context.channel.sendMessage(result).queue()
    }
}