package me.openani.commands;

import me.openani.UtilKt;
import me.openani.handler.command.CommandContext;
import me.openani.handler.listener.CommandListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetSuggest implements CommandListener {
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

        String authorId = res.getString(2);

        User author = context.message.getJDA().retrieveUserById(authorId).complete();
        if (author == null) return;

        boolean c = res.getBoolean(4);

        EmbedBuilder embed = new EmbedBuilder();

        if (c) embed.setColor(Color.GREEN);
        else embed.setColor(Color.RED);

        embed.setDescription(res.getString(3));
        embed.setFooter(author.getName(), author.getEffectiveAvatarUrl());
        embed.setTitle("Suggest " + caseNum);

        context.channel.sendMessageEmbeds(embed.build()).queue();

        conn.close();
        st.close();
        res.close();
    }
}