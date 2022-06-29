package me.openani.events;

import me.openani.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.Color;

public class MessageEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getGuild() == null) return;

        if (!event.getChannel().getId().equals(Config.SUGGEST_CHANNEL)) return;

        
        event.getMessage().delete().queue();

        Message msg = event.getMessage();
        User author = event.getAuthor();

        EmbedBuilder embed = new EmbedBuilder();
        
        embed.setColor(Color.red);
        embed.setFooter(author.getName(), author.getAvatarUrl() != null ? author.getAvatarUrl() : author.getDefaultAvatarUrl());

        TextChannel channel = event.getGuild().getTextChannelById(Config.PRIVATE_SUGGEST_CHANNEL);

        if (channel == null) 
            return;

        channel.sendMessage(msg.getContentRaw()).setEmbeds(embed.build()).queue();
    }
}
