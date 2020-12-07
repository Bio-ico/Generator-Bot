import discord4j.core.event.domain.message.MessageCreateEvent;
import org.jsoup.Jsoup;

public class Quote extends Command {
    public Quote(){
        this.cmd = "quote";
        this.about = "generates an inspirobot quote";
    }
    @Override
    public void execute(MessageCreateEvent event) {
        try {
            String link =  Jsoup.connect("http://inspirobot.me//api?generate=true").get().body().text();
            SendEmbed.send_image_with_hyperlink("Inspirobot Quote",link, "here's your inspirobot quote", link, event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
