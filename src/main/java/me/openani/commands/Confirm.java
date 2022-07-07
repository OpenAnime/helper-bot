package me.openani.commands;

import me.openani.ConfigKt;
import me.openani.UtilKt;
import me.openani.handler.command.CommandContext;
import me.openani.handler.listener.CommandListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Confirm implements CommandListener {
    @Override
    public void onCommand(CommandContext context) throws SQLException {
        if (context.args.length < 1) {
            context.channel.sendMessage("Numara giriniz.").queue();
            return;
        }

        String caseNum = context.args[0];

        Connection conn = UtilKt.getConnection();

        Statement st = conn.createStatement();

        ResultSet res = null;

        try {
            res = st.executeQuery("SELECT * FROM suggestions WHERE caseNum = " + caseNum);
        } catch (SQLException e) {
            context.channel.sendMessage(e.toString()).queue();
        }

        if (res == null) return;

        if (!res.first()) {
            context.channel.sendMessage("Öneri bulunamadı!").queue();
            return;
        }

        boolean c = res.getBoolean(4);

        if (c) {
            context.channel.sendMessage("Öneri zaten onaylanmış!").queue();
            return;
        }

        String authorId = res.getString(2);

        User author = context.message.getJDA().retrieveUserById(authorId).complete();

        EmbedBuilder embed = new EmbedBuilder();

        embed.setDescription(res.getString(3));

        if (author == null) return;

        embed.setFooter(author.getName(), author.getEffectiveAvatarUrl());
        embed.setTitle("Suggest " + caseNum);
        embed.setColor(Color.GREEN);

        st.executeQuery("UPDATE suggestions SET confirm = TRUE WHERE caseNum = " + caseNum);

        context.channel.sendMessage("Başarılı!").queue();

        TextChannel channel = context.message.getGuild().getTextChannelById(ConfigKt.SUGGEST_CHANNEL);

        if (channel == null) return;

        channel.sendMessageEmbeds(embed.build()).queue();

        conn.close();
        st.close();
        res.close();
    }
}
