package me.openani.events

import me.openani.PRIVATE_SUGGEST_CHANNEL
import me.openani.SUGGEST_CHANNEL
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color

class MessageEvent: ListenerAdapter() {
    public override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.guild.equals(null)) return

        if (event.channel.id != SUGGEST_CHANNEL) return

        event.message.delete().queue()

        val author = event.author

        val embed = EmbedBuilder()

        embed.setColor(Color.red)
        embed.setFooter(author.name, if (author.avatarUrl != null) author.avatarUrl else author.defaultAvatarUrl)

        val channel = event.guild.getTextChannelById(PRIVATE_SUGGEST_CHANNEL) ?: return

        channel.sendMessage(event.message.contentRaw).setEmbeds(embed.build()).queue()
    }
}