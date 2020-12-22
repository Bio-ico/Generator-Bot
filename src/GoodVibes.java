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


import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Attachment;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class GoodVibes {
    static ArrayList<Command> commands = null;
    public static void main(String[] arguments) throws Exception {
        commands = new ArrayList<>(Arrays.asList(
                new Help(),
                new hug(),
                new ubf(),
                new anime(),
                new mtg(),
                new mus(),
                new PKMN(),
                new Quote(),
                new wikiped()
        ));
        final String token = arguments[0];
        final DiscordClient dclient = DiscordClient.create(token);
        final GatewayDiscordClient client = dclient.login().block();
        client.updatePresence(Presence.online(Activity.playing("generating things."))).subscribe();
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                            final String content = event.getMessage().getContent(); // 3.1 Message.getContent() is a String
                            if(content.equalsIgnoreCase("-ping"))
                            {
                                final MessageChannel channel = event.getMessage().getChannel().block();
                                channel.createMessage("Pong!").block();
                            }
                            else if(content.equalsIgnoreCase("@update")){
                                Attachment e = (Attachment) event.getMessage().getAttachments().toArray()[0];
                                System.out.println(e.getUrl());
                                File updatePy = new File("urlPath.txt");
                                try {
                                    updatePy.createNewFile(); // if file already exists will do nothing
                                    FileOutputStream oFile = new FileOutputStream(updatePy, false);
                                    String python = e.getUrl();
                                    oFile.write(python.getBytes(StandardCharsets.UTF_8));
                                    oFile.flush();
                                    SendEmbed.send_message("ok", "rebooting!", event);
                                    Runtime.getRuntime().exec("sudo reboot");
                                } catch (IOException ioException) {
                                    SendEmbed.send_message("bad", ioException.getMessage(), event);
                                    ioException.printStackTrace();
                                }
                            }
                            else {
                                for (final Command entry : commands) {
                                    if (content.startsWith('-' + entry.cmd)) {
                                        try {
                                            entry.execute(event);
                                        }
                                        catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        break;
                                    }
                                }
                            }

                        });
        client.onDisconnect().block();
    }
}

