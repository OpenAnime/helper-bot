package me.openani.commands

import me.openani.handler.command.CommandContext
import me.openani.handler.listener.CommandListener

class TestCommand: CommandListener {
    override fun onCommand(context: CommandContext) {
            context.channel.sendMessage("Hello from Kotlin!").queue()
    }
}