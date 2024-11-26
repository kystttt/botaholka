package bots;

import logic.BotLogic;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordBot extends ListenerAdapter {


    private final BotLogic logic;

    public DiscordBot(BotLogic logic) {
        this.logic = logic;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();
        Long longId = Long.parseLong(channel.getId());

        String output_message = logic.processMessage(content, longId);

        channel.sendMessage(output_message).queue();
    }
}