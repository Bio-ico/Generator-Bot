import discord4j.core.event.domain.message.MessageCreateEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class mus extends Command {
    public mus (){
        this.cmd = "mus";
        this.about = "grabs the sheet music of a randomly generated song (optional: from a series) in a pdf." +
                "Usage: -mus <series>";
    }

    @Override
    public void execute(MessageCreateEvent event) {
        String args = getArgs(event);
        String url = "https://ichigos.com";
        try {
            Document doc = Jsoup.connect(url + "/list").get();
            String gameUrl;
            if (args.length() > 0) {
                System.out.println(args);
                Elements games = doc.select("a:contains("+args.trim()+")");
                int gameIndex = (int) (((System.currentTimeMillis() & 0xFF00) >> 8) % games.size());
                gameUrl = url + games.get(gameIndex).attr("href");
            }
            else{
                Elements links = doc.getElementsByClass("content").select("a");
                int gameIndex = (int) (((System.currentTimeMillis() & 0xFF00) >> 8) % links.size());
                gameUrl = url + links.get(gameIndex).attr("href");
            }
            doc = Jsoup.connect(gameUrl).get();
            Elements t = doc.select("a:contains(pdf)");
            int r = (int) (((System.currentTimeMillis() & 0xFF00) >> 8) % t.size());
            SendEmbed.send_message_with_hyperLink("Song from: "+doc.getElementsByClass("title2").text(), url + t.get(r).attr("href"), "enjoy your song!", event);
            System.out.println("ok");

        }catch (ArithmeticException a){
            SendEmbed.send_error_message("mus", "unable to find music with the provided parameters", event);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
