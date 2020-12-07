import discord4j.core.event.domain.message.MessageCreateEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.concurrent.ThreadLocalRandom;

public class PKMN extends Command {
    public PKMN (){
        this.cmd = "pkmn";
        this.about = "generates a random pokemon";
    }

    @Override
    public void execute(MessageCreateEvent event) {
        Document doc = null;
        String name = getArgs(event);
        String shiny = "ani";
        System.out.println(name);
        if(name.contains("shiny"))
        {
            name = name.substring(0, name.lastIndexOf(" "));
            shiny += "-shiny";
        }
        System.out.println(name);
        try {
            if(name.equalsIgnoreCase("")) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, 8192);
                if(randomNum == 613)
                    shiny += "-shiny";
                doc = Jsoup.connect("https://www.bestrandoms.com/random-pokemon").get();
                Elements links = doc.getElementsByClass("text-center font-18");
                 name = links.get((int) ((System.currentTimeMillis() & 0x00FF) % links.size())).text();
            }

            String name2 = "https://play.pokemonshowdown.com/sprites/"+shiny+"/"+name.toLowerCase()+".gif";

            doc = Jsoup.connect("https://www.pokemon.com/us/pokedex/"+name.toLowerCase()).get();
            Elements link = doc.getElementsByClass("version-x");

            String hyperlink = "https://bulbapedia.bulbagarden.net/wiki/"+name+"_(Pokemon)";

            SendEmbed.send_image_with_hyperlink(name, hyperlink, link.first().text(), name2, event);
        } catch (Exception e) {
            e.printStackTrace();
            SendEmbed.send_error_message("pkmn", "couldn't find pokemon \""+name+"\"!", event);
        }


    }

}
