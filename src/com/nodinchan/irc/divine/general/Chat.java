package com.nodinchan.irc.divine.general;

import java.io.DataOutputStream;
import java.io.IOException;

import com.nodinchan.irc.divine.Divine;
import com.nodinchan.irc.divine.commands.BotCommands;

public class Chat {
	
	private DataOutputStream writer;
	
	private String read;
	private String sentence;
	
	public Chat() {
		this.read = Divine.getRead();
		this.writer = Divine.getWriter();
	}
	
	public void onPhrase() throws IOException {
		sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "");
		
		if (sentence.toLowerCase().contains("divine")) {
			if (sentence.toLowerCase().contains("thank")) {
			writer.writeBytes("PRIVMSG " + read.split(" ")[2] + " :You're welcome, " + read.split("!")[0].substring(1) + "\n");
			
			}
			
			if (sentence.toLowerCase().contains("hi") || sentence.toLowerCase().contains("hello") || sentence.toLowerCase().contains("hey")) {
				writer.writeBytes("PRIVMSG " + read.split(" ")[2] + " :Howdy, " + read.split("!")[0].substring(1) + "\n");
				
			}
			
			if (sentence.toLowerCase().contains("slaps")) {
				writer.writeBytes("PRIVMSG " + read.split(" ")[2] + " :\001ACTION slaps " + read.split("!")[0].substring(1) + " with double rainbows all the way\001\n");
				writer.writeBytes("PRIVMSG " + read.split(" ")[2] + " :Revenge feels good.\n");
				
			}
			
			if (sentence.toLowerCase().contains("return")) {
				if (read.startsWith(":NodinChan!") || read.startsWith(":Nodin!") || read.startsWith(":NinjaChan!") || read.startsWith(":NoodleChan!") || read.startsWith(":DivineHero!") || read.split(" ")[4].contains("NCSC")) {
					writer.writeBytes("PRIVMSG " + read.split(" ")[2] + " :\001ACTION returns into NodinChan's Pokeball\001\n");
				} else {
					writer.writeBytes("PRIVMSG " + read.split(" ")[2] + " :You are not my master!\n");
					writer.writeBytes("PRIVMSG " + read.split(" ")[2] + " :\001ACTION uses Thunderbolt on " + read.split("!")[0].substring(1) + " with double rainbows all the way.\001\n");
				}
			}
		} else {
			new Game().onGames();
		}
	}
	
	public void onPrivMsg() throws IOException {
		String command = read.split(" ")[3].toLowerCase();
		
		if (command.contains(":!divine")) {
			new BotCommands().commandDivine();
			
		} else if (command.contains(":!join")) {
			if (!read.endsWith(":!join")) {
				new BotCommands().commandJoin();
				
			} else {
				writer.writeBytes("NOTICE " + read.split("!")[0].substring(1) + " :Please include a channel name\n");
				
			}
		} else if (command.contains(":!part")) {
				new BotCommands().commandPart();
				
		} else if (command.contains(":!say")) {
			String args = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "").replace(read.split(" ")[3], "").substring(4);
			if (!read.endsWith(":!say") || args.length() != 0) {
				new BotCommands().commandSay();
				
			}
		} else if (command.contains(":!slap")) {
			if (!read.endsWith(":!slap")) {
				new BotCommands().commandSlap();
				
			}
		} else if (command.contains(":!version")) {
			new BotCommands().commandVersion();
			
		}
	}
}
