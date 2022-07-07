package me.openani.handler.listener;

import me.openani.handler.command.CommandContext;

import java.sql.SQLException;

// https://github.com/mnmnr/JDA-Command-Handler

public interface CommandListener {
    // void onCommand(Member member, TextChannel channel, Message message, String[] args);
    void onCommand(CommandContext context) throws SQLException;
}
