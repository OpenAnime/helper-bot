package me.openani

import me.openani.commands.Confirm
import me.openani.commands.GetSuggest
import me.openani.commands.HelpCommand
import me.openani.commands.TestCommand
import me.openani.events.MessageEvent
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import javax.security.auth.login.LoginException
import me.openani.handler.CommandHandlerBuilder
import me.openani.handler.command.CommandBuilder
import net.dv8tion.jda.api.Permission


class Bot @Throws(LoginException::class) constructor(var token: String) {
    val jda: JDA = JDABuilder.createLight(token,
        GatewayIntent.GUILD_MESSAGES)
        .setActivity(Activity.playing("Open Anime"))
        .addEventListeners(MessageEvent())
        .build()

    var commandHandler = CommandHandlerBuilder(jda)
        .setPrefix("\$")
        .addCommands(
            CommandBuilder("test", TestCommand()).build(),
            CommandBuilder("help", HelpCommand()).build(),
            CommandBuilder("suggest", GetSuggest()).build(),
            CommandBuilder("confirm", Confirm()).addPermission(Permission.ADMINISTRATOR).build(),
        )
        .build()
}