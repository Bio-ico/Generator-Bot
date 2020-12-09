import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class hug extends Command {
    public hug(){
        this.cmd = "hug";
        this.about = "Hug someone (e.g. -hug @bio#0018) or receive a hug (e.g. -hug or -hug me)";
    }
    @Override
    public void execute(MessageCreateEvent event) {
        String args = getArgs(event);
        try {
            String hugger, victim;
            if (args.length() <= 0 || args.equals("me")) {
                hugger = event.getClient().getUserById(Snowflake.of("728667180131942501")).block().getMention();
                victim = event.getMessage().getAuthor().get().getMention();
            }
            else {
                hugger = event.getMessage().getAuthor().get().getMention();
                victim = event.getMessage().getUserMentions().blockFirst().getMention();
            }
            Random r = new Random();
            int randomInt;
            ArrayList<Integer> badGifs = new ArrayList(Arrays.asList(new int[]{18, 39, 44, 56, 67, 89}));
            do {
                randomInt = r.nextInt(100);
            } while(badGifs.contains(randomInt));
            String url = "https://acegif.com/wp-content/gif/anime-hug-" + randomInt + ".gif";
            System.out.println(randomInt);
            SendEmbed.send_hug(hugger + " hugs " + victim, url, event);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            String n ="cannot find.";
            SendEmbed.send_error_message("hug", "something went wrong :(", event);
        }
        catch (Exception e){
            e.printStackTrace();
            SendEmbed.send_error_message("hug", "something went wrong :(", event);
        }
    }
}
