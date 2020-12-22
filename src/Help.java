/*
 * Copyright (c) 2018 Daniel J. Kirsch
 * HaGaon HaMachane [https://github.com/djkirsch/HaGaonHaMachane]
 *
 * This file is part of HaGaon HaMachane.
 *
 * HaGaon HaMachane is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License
 * version 3 as published by the Free Software Foundation
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;




public class Help extends Command {


    public Help() {
        cmd = "help";
        about = "information about the bot";
    }

    @Override
    public void execute(MessageCreateEvent event) {
        String body = "A bot created by "+ event.getClient().getUserById(Snowflake.of("726867603179438213")).block().getMention()+"\n\nyou can add me to your server by going here: \nhttps://discord.com/api/oauth2/authorize?client_id=728667180131942501&permissions=388160&scope=bot";
        for (final Command entry : GoodVibes.commands) {
            // We will be using ! as our "prefix" to any command in the system.
            body += "\n\n"+entry.cmd + ": "+entry.about;
        }

        SendEmbed.send_message("Help", body, event);
    }
}


/*
public class About extends Command {
	public About() {
		this.name = "about";
	}
	public void doStuff(MessageCreateEvent event) {
		final Message message = event.getMessage();
		if ("!ping".equals(message.getContent())) {
			final MessageChannel channel = message.getChannel().block();
			channel.createMessage("Pong!").block();
		}
	}
	public void execute(CommandEvent event) {
		Permission[] recommendedPerms = new Permission[] {Permission.MESSAGE_READ, Permission.MESSAGE_WRITE, Permission.MESSAGE_EMBED_LINKS};
		String link = event.getJDA().asBot().getInviteUrl(recommendedPerms);
		String message = "Hello! I'm the generator bot! I am a discord bot that can generate many things!\n [Invite]("
				+ link + ") me to your server!\n[Find me on Github!](https://github.com/bio-ico)";
		SendEmbed.send_message("about", message, event);
	}

}

 */
