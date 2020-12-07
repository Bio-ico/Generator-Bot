import discord4j.core.event.domain.message.MessageCreateEvent;

public class youtube extends Command{
    public youtube (){
        this.about = "get a youtube video.";
        this.cmd = "yt";
    }
    @Override
    void execute(MessageCreateEvent event) {

    }
}
