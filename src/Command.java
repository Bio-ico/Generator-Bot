import discord4j.core.event.domain.message.MessageCreateEvent;

public abstract class Command {
    String about;
    String cmd;
    public String getArgs(MessageCreateEvent event){
        return event.getMessage().getContent().replaceAll("-"+cmd, "").trim();
    }
   abstract void execute(MessageCreateEvent event);
}
