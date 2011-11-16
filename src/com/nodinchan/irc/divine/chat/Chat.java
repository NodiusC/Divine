package com.nodinchan.irc.divine.chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.nodinchan.irc.divine.Divine;
import com.nodinchan.irc.divine.commands.BotCommands;
import com.nodinchan.irc.divine.misc.Misc;
import com.nodinchan.irc.divine.sixws.How;

public class Chat {
	
	private DataOutputStream writer;
	
	private String read;
	private String sentence;
	
	private static Map<String, String> howReplyPending = new HashMap<String, String>();
	
	public Chat() {
		this.read = Divine.getRead();
		this.writer = Divine.getWriter();
	}
	
	// Adds the questioner to the How Reply List
	
	public static void addHow(String query, String name) {
		howReplyPending.put(query, name);
	}
	
	// Called when Divine is mentioned
	
	public void onDivine() throws IOException {
		sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "");
		
		if (read.replace(read.split("!")[0], "").substring(1).toLowerCase().contains("divine")) {
			if (sentence.toLowerCase().contains("thank")) {
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :You're welcome, " + Divine.getSender() + "\n");
				
			}
				
			if (sentence.toLowerCase().contains("hi") || sentence.toLowerCase().contains("hello") || sentence.toLowerCase().contains("hey")) {
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :Howdy, " + Divine.getSender() + "\n");
					
			}
				
			if (sentence.toLowerCase().contains("slaps") || sentence.contains("slap")) {
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION slaps " + Divine.getSender() + " with double rainbows all the way\001\n");
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :Revenge feels good.\n");
					
			}
				
			if (sentence.toLowerCase().contains("return")) {
				if (read.startsWith(":NodinChan!") || read.startsWith(":Nodin!") || read.startsWith(":NinjaChan!") || read.startsWith(":NoodleChan!") || read.startsWith(":DivineHero!") || read.split(" ")[4].contains("NCSC")) {
					writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION returns into NodinChan's Pokeball\001\n");
				} else {
					writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :You are not my master!\n");
					writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION uses Thunderbolt on " + Divine.getSender() + " with double rainbows all the way.\001\n");
				}
			}
			
			new How().onHow();
		}
	}
	
	// Called whenever a command might be used
	
	public void onBotCommand() throws IOException {
		String command = read.split(" ")[3].toLowerCase();
		
		if (command.contains(":!test")) {
			writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :" + read + "\n");
			
		} else if (command.contains(":!controlpanel") || command.contains(":!cp")) {
			new BotCommands().commandControlPanel();
			
		} else if (command.contains(":!divine")) {
			new BotCommands().commandDivine();
			
		} else if (command.contains(":!join")) {
			if (!read.endsWith(":!join")) {
				new BotCommands().commandJoin();
				
			} else {
				writer.writeBytes("NOTICE " + Divine.getSender() + " :Please include a channel name\n");
			}
		} else if (command.contains(":!me")) {
			if (!read.endsWith(":!me")) {
				new BotCommands().commandAction();
			} else {
				writer.writeBytes("NOTICE " + Divine.getSender() + " :Please include a channel name\n");
			}
			
		} else if (command.contains(":!part")) {
				new BotCommands().commandPart();
				
		} else if (command.contains(":!say")) {
			String args = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(Divine.getChannel(), "").replace(read.split(" ")[3], "").substring(4);
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
	
	// Called whenever there is chat
	
	public void onMisc() throws IOException {
		new Misc().onGames();
	}
	
	public void onReply() throws IOException {
		sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "");
		
		if (!howReplyPending.isEmpty()) {
			if (howReplyPending.containsKey("registernick" + read.split("!")[0])) {
				if (howReplyPending.get("registernick" + read.split("!")[0]).equals(read.split("!")[0])) {
					if (sentence.toLowerCase().contains("yes")) {
						writer.writeBytes("NOTICE " + Divine.getSender() + " :Please use [ /ns REGISTER <password> <email-address> ] without the brackets to register your current nickname.\n");
						writer.writeBytes("NOTICE " + Divine.getSender() + " :Then go to your email address and use the command given to confirm.\n");
						removeHow("registernick" + read.split("!")[0]);
					} else if (sentence.toLowerCase().contains("no")) {
						writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :Alright.\n");
						removeHow("registernick" + read.split("!")[0]);
					}
				}
			}
		}
	}
	
	// Removes the questioner from the How Reply List
	
	public static void removeHow(String query) {
		howReplyPending.remove(query);
	}
	
	// List of terms that cannot be used in certain commands
	
	public enum SelfProtection {
		DIVINE,
		HERSELF,
		HIMSELF,
		ITSELF,
	}
}
