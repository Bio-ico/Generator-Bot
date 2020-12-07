import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Color;

public class SendEmbed {
    static String sURL = "https://cdn.discordapp.com/avatars/464181282583085056/5b8d2bae28e2e67779aaef217b9648b3.png?size=4096";

    public static void send_message(String header, String body, MessageCreateEvent event) {
        final Message message = event.getMessage();
        final MessageChannel channel = message.getChannel().block();
        String finalName = header;
        channel.createMessage(messageSpec -> {
            messageSpec.setEmbed(embedSpec -> {
                embedSpec.setColor(Color.of(00,200, 200))
                         .setTitle(finalName)
                         .setDescription(body)
                         .setFooter("Good Vibes Bot v0.1", sURL);
            });
        }).block();
    }
    public static void send_direct_message(String header, String message, String footer, MessageCreateEvent event){
     //   event.reply(new EmbedBuilder().setTitle(header).setDescription(message).addField("footer", footer, false).build());
    }
/*
    public static void send_message(String header, String message, CommandEvent event, String[]... extras){
        EmbedBuilder emb = new EmbedBuilder().setTitle(header).setDescription(message);
        if (extras.length > 0)
            for (String[] f : extras)
                emb.addField(f[0], f[1], false);
        event.getChannel().sendMessage(emb.build()).queue();
    }

 */
    public static void send_error_message(String command, String error, MessageCreateEvent event){
        final Message message = event.getMessage();
        final MessageChannel channel = message.getChannel().block();
        channel.createMessage(messageSpec -> {
            messageSpec.setEmbed(embedSpec -> {
                embedSpec
                        .setColor(Color.RED)
                        .setTitle("oops")
                        .setDescription("issue with "+command+":\n"+error)
                        .setFooter("Good Vibes Bot v0.1", sURL);
            });
        }).block();
    }

    public static void send_image_with_hyperlink(String name, String hyperlink, String body, String imgUrl,MessageCreateEvent event) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        final Message message = event.getMessage();
        final MessageChannel channel = message.getChannel().block();
        String finalName = name;
        channel.createMessage(messageSpec -> {
            messageSpec.setEmbed(embedSpec -> {
                embedSpec.setColor(Color.DEEP_SEA)
                         .setTitle(finalName)
                         .setUrl(hyperlink)
                         .setImage(imgUrl)
                         .setDescription(body)
                         .setFooter("Good Vibes Bot v0.1", sURL);
            });
        }).block();
    }

/*
    public static void sendImage(String name, String body, String imgUrl, CommandEvent event) {
        String sURL = "https://cdn.discordapp.com/avatars/464181282583085056/5b8d2bae28e2e67779aaef217b9648b3.png?size=4096";
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        //event.reply(new EmbedBuilder().setTitle(name).setImage(imgUrl).setDescription(body).setColor(Color.BLUE)
       //         .setFooter("Good Vibes Bot v0.1", sURL).build());
    }

 */

    public static void send_message_with_hyperLink(String name, String hyperlink, String body, MessageCreateEvent event){
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        final Message message = event.getMessage();
        final MessageChannel channel = message.getChannel().block();
        String finalName = name;
        channel.createMessage(messageSpec -> {
            messageSpec.setEmbed(embedSpec -> {
                embedSpec.setColor(Color.SUMMER_SKY)
                         .setTitle(finalName)
                         .setUrl(hyperlink)
                         .setDescription(body)
                         .setFooter("Good Vibes Bot v0.1", sURL);
            });
        }).block();
    }
/*
    public static void sendUrl(String title, String body, CommandEvent event){
        String sURL = "https://cdn.discordapp.com/avatars/464181282583085056/5b8d2bae28e2e67779aaef217b9648b3.png?size=4096";
      //  event.reply(new EmbedBuilder().setTitle(title).setDescription(body).setColor(Color.BLUE)
         //       .setFooter("Good Vibes Bot v0.1", sURL).build());
    }

 */
}
