import discord4j.core.event.domain.message.MessageCreateEvent;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;

public class mtg extends Command {
    public mtg() {
        cmd = "mtg";
        about = "gets a requested Magic: the Gathering card. If none are provided, will generate one randomly";
    }

    @Override
    public void execute(MessageCreateEvent event) {
        String args = getArgs(event);
        String url;
        try {
            if(args.length() <= 0) {
                url = "https://api.scryfall.com/cards/random?pretty=true";
            }
            else {
                url = "https://api.scryfall.com/cards/named?pretty=true&fuzzy="+args;
            }
            String json=Jsoup.connect(url).ignoreContentType(true).execute().body();
            JSONObject obj = new JSONObject(json);
            String name  = obj.getString("name");
            String uri   = obj.getString("scryfall_uri");
            String text  = format(obj.getString("oracle_text"));
            String flavor=((obj.has("flavor_text"))? "*"+obj.getString("flavor_text")+"*":"") ;
            String img   = obj.getJSONObject("image_uris").getString("png");
            SendEmbed.send_image_with_hyperlink(name, uri,text+"\n"+flavor, img, event );
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            SendEmbed.send_error_message("mtg", "couldn't find card \""+args+"\"!", event);
        }
    }

    //add emotes for some symbols in the oracle text
    public String format(String str){
       return str
               .replaceAll("\\((.+)\\)", "(*$1*)")
                .replaceAll("\\{R\\}", ":red_circle:")
                .replaceAll("\\{B\\}", ":black_circle:")
                .replaceAll("\\{W\\}", ":white_circle:")
                .replaceAll("\\{G\\}", ":green_circle:")
                .replaceAll("\\{U\\}", ":blue_circle:")
                .replaceAll("\\{C\\}", ":new_moon:")
                .replaceAll("\\{E\\}", ":regional_indicator_e")
                .replaceAll("\\{T\\}", ":regional_indicator_t:")
                .replaceAll("\\{Q\\}", ":regional_indicator_u:")
                .replaceAll("\\{X\\}", ":regional_indicator_x:")
                .replaceAll("\\{0\\}", ":zero:")
                .replaceAll("\\{1\\}", ":one:")
                .replaceAll("\\{2\\}", ":two:")
                .replaceAll("\\{3\\}", ":three:")
                .replaceAll("\\{4\\}", ":four:")
                .replaceAll("\\{5\\}", ":five:")
                .replaceAll("\\{6\\}", ":six:")
                .replaceAll("\\{7\\}", ":seven:")
                .replaceAll("\\{8\\}", ":eight:")
                .replaceAll("\\{9\\}", ":nine:")
                .replaceAll("\\{10\\}", ":ten:")
                .replaceAll("\\{11\\}", ":eleven:")
                .replaceAll("\\{12\\}", ":twelve:")
                .replaceAll("\\{13\\}", ":thirteen:")
                .replaceAll("\\{14\\}", ":fourteen:")
                .replaceAll("\\{15\\}", ":fifteen:")
                .replaceAll("\\{16\\}", ":sixteen:");
    }
}
