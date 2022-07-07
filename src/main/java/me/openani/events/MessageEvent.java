package me.openani.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.openani.ConfigKt;
import me.openani.UtilKt;

public class MessageEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.isFromGuild()) return;

        if (!event.getChannel().getId().equals(ConfigKt.SUGGEST_CHANNEL)) return;

        event.getMessage().delete().queue();

        User author = event.getAuthor();

        EmbedBuilder embed = new EmbedBuilder();

        int caseNum = UtilKt.getCaseNum();

        embed.setAuthor("Suggest " + caseNum);
        embed.setColor(Color.red);
        embed.setFooter(author.getName(), author.getEffectiveAvatarUrl());

        TextChannel channel = event.getGuild().getTextChannelById(ConfigKt.PRIVATE_SUGGEST_CHANNEL);

        if (channel == null) return;

        channel.sendMessage(event.getMessage().getContentRaw()).setEmbeds(embed.build()).queue(msg -> {
            UtilKt.createSuggestion(author.getId(), event.getMessage().getContentRaw());
        });
    }
}