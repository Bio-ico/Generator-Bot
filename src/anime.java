import discord4j.core.event.domain.message.MessageCreateEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;

public class anime extends Command {
    public anime() {
        cmd = "anime";
        about = "gets a requested anime. If none are provided, will generate one randomly. Can specify MAL before the name. (e.g. !anime MAL gakkou gurashi)";
    }

    @Override
    public void execute(MessageCreateEvent event) {
        String search_results;
        String args = getArgs(event);
        boolean mal;
        Document doc;
        URL url;
        String nam;
        if(mal = args.startsWith("MAL"))
            args = args.substring(3).trim();
        try {
            if(args.equalsIgnoreCase("")) {
                 doc = Jsoup.connect("https://anidb.net/anime/random").get();
            }
            else{
                doc = Jsoup.connect( "https://anidb.net/anime/?adb.search="+args+"&do.search=1").get();
                if(doc.getElementsByClass("animelist").hasText()) {
                    search_results = doc.getElementsByClass("thumb anime").select("a").first().attr("href");
                    doc = Jsoup.connect("https://anidb.net"+search_results).get();
                }
            }
            Elements get_name = doc.getElementsByClass("value");
            nam = get_name.first().text().split("\\(.+\\)")[0];

            String line = doc.getElementsByClass(mal ? "i_icon i_resource_mal brand" : "shortlink").attr("href");
            Elements ne = doc.select("div[itemprop = description]");
            String body = "";
            if (ne.text().length() > 0) {
              body = ne.text().replaceAll("(^\")|Source:\\s.+", "");
            }
            Elements img = doc.select("img[itemprop = image]");
            SendEmbed.send_image_with_hyperlink(nam, line, body, img.attr("src"), event);
        }catch (NullPointerException e){
            SendEmbed.send_error_message("anime", "couldn't find anime \""+args+"\"!", event);
        } catch (Exception e) {
           SendEmbed.send_error_message("anime", "an error occurred :/", event);
            e.printStackTrace();
        }
    }
}
