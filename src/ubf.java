import discord4j.core.event.domain.message.MessageCreateEvent;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ubf extends Command {
   public ubf(){
       cmd = "ubf";
       about = "gets a requested ubfunky. If none are provided, will generate one randomly";
   }

    @Override
    public void execute(MessageCreateEvent event) {
        Document doc = null;
        String args = getArgs(event);
        String url = "https://ubfunkeys-wiki.ga";
        String charLink = "";
        String name = "";
        try {
            //find page off arguments
            if(args.equals("")) {
                doc = Jsoup.connect(url + "/index.php/Category:Funkeys").get();
                Elements list = doc.getElementsByClass("mw-category-group");
                Element char_list = list.get((int) (System.currentTimeMillis() & 0x00FF) % list.size()).getElementsByTag("ul").first();
                list = char_list.getElementsByTag("a");
                Element chara = list.get((int) (System.currentTimeMillis() & 0x00FF) % list.size());
                charLink += chara.attr("href");
            }
            else
                charLink +=  "/index.php/"+args;

            //name
            name = charLink.substring(11).replaceAll("_"," ");

            //description
            doc = Jsoup.connect(url+charLink).get();
            String body = "Sorry, no info found.";
            Element outerHTML =  doc.getElementsByClass("mw-parser-output").first();
            if(!doc.select("#In-Game_Description").isEmpty()) {
                Elements t = outerHTML.getAllElements();
                for(int x = 0; x < t.size(); x++){
                    if(t.get(x).id().equals("In-Game_Description")) {
                       body = t.get(x + 1).text();
                        break;
                    }
                }
            }
            else
                body = outerHTML.getElementsByTag("p").first().text();

            //picture url
            String img = "/resources/assets/newlogo.png?9d463";
            if(!doc.getElementsByClass("image").isEmpty()) {
                String temp = doc.getElementsByClass("image").first().attr("href");
                doc = Jsoup.connect(url+temp).get();
                img = doc.getElementsByClass("fullImageLink").first().getElementsByTag("a").first().attr("href");
            }

            SendEmbed.send_image_with_hyperlink(name, url+charLink, body, url+img, event);


        } catch (NullPointerException|HttpStatusException e) {
            e.printStackTrace();
            String n = name.substring(0,1).toUpperCase()+name.substring(1);
            SendEmbed.send_error_message("ubf", "couldn't find funkey \"" + n + "\"! Perhaps try " + n+"\\_(Figure) or " + n+"\\_(Character)?", event);
        }
        catch (Exception e){
            e.printStackTrace();
            SendEmbed.send_error_message("ubf", "something went wrong :(", event);
        }
    }
}
