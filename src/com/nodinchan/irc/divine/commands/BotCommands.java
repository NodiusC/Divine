package com.nodinchan.irc.divine.commands;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.nodinchan.irc.divine.Divine;
import com.nodinchan.irc.divine.Divine.FullAccess;
import com.nodinchan.irc.divine.chat.Chat.SelfProtection;

public class BotCommands {
	
	private DataOutputStream writer;
	
	private String read;
	
	private static List<FullAccess> checkList = new LinkedList<FullAccess>();
	private static List<String> protectedList = new LinkedList<String>();
	
	public BotCommands() {
		this.read = Divine.getRead();
		this.writer = Divine.getWriter();
	}
	
	// Bot Command for bringing up the Control Panel
	
	public void commandControlPanel() throws IOException {
		for (FullAccess fullAccess : FullAccess.values()) {
			
			if (read.toUpperCase().startsWith(":" + fullAccess.toString() + "!")) {
				writer.writeBytes("PRIVMSG " + Divine.getSender() + " :- Control Panel Initiated -\n");
				
			} else {
				checkList.add(fullAccess);
				
				if (checkList.size() == FullAccess.values().length + 1) {
					Divine.accessDenied();
					checkList.clear();
				}
			}
		}
	}
	
	// Bot Command about Divine
	
	public void commandDivine() throws IOException {
		writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION is NodinChan's Friend Bot!\001\n");
	}
	
	// Bot Command for joining channels
	
	public void commandJoin() throws IOException {
		for (FullAccess fullAccess : FullAccess.values()) {
			
			if (read.toUpperCase().startsWith(":" + fullAccess.toString() + "!")) {
				if (read.split(" ")[4].startsWith("#")) {
					writer.writeBytes("JOIN " + read.split(" ")[4] + "\n");
					
				} else {
					writer.writeBytes("JOIN #" + read.split(" ")[4] + "\n");
					
				}
			} else {
				checkList.add(fullAccess);
				
				if (checkList.size() == FullAccess.values().length) {
					Divine.accessDenied();
					checkList.clear();
				}
			}
		}
		
	}
	
	// Bot Command for bot to do action
	
	public void commandAction() throws IOException {
		if (read.split(" ")[4].startsWith("#")) {
			for (FullAccess fullAccess : FullAccess.values()) {
				
				if (read.toUpperCase().startsWith(":" + fullAccess.toString() + "!")) {
					String sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "").replace(read.split(" ")[3], "").replace(read.split(" ")[4], "");
					writer.writeBytes("PRIVMSG " + read.split(" ")[4] + " :\001ACTION " + sentence.substring(5) + "\001\n");
					
				} else {
					checkList.add(fullAccess);
					
					if (checkList.size() == FullAccess.values().length) {
						Divine.accessDenied();
						checkList.clear();
					}
				}
			}
			
		} else {
			writer.writeBytes("NOTICE " + Divine.getSender() + " :Please include a channel name\n");
		}
	}
	
	// Bot Command for leaving channels
	
	public void commandPart() throws IOException {
		writer.writeBytes("PART " + Divine.getChannel() + "\n");
	}
	
	// Bot Command for bot to speak
	
	public void commandSay() throws IOException {
		for (FullAccess fullAccess : FullAccess.values()) {
			
			if (read.toUpperCase().startsWith(":" + fullAccess.toString() + "!")) {
				String sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(Divine.getChannel(), "").replace(read.split(" ")[3], "").replace(read.split(" ")[4], "");
				writer.writeBytes("PRIVMSG " + read.split(" ")[4] + " :" + sentence.substring(5) + "\n");
				
			} else {
				checkList.add(fullAccess);
				
				if (checkList.size() == FullAccess.values().length) {
					Divine.accessDenied();
					checkList.clear();
				}
			}
		}
	}
	
	// Bot Command for bot slap (Still experimental, may spam)
	
	public void commandSlap() throws IOException {
		String target = read.split(" ")[4];
		for (FullAccess fullAccess : FullAccess.values()) {
			for (SelfProtection selfProtect : SelfProtection.values()) {
				if (!target.equals(fullAccess.toString()) && !target.equals(selfProtect.toString())) {
					writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION slaps " + target + " with double rainbows all the way.\001\n");
					
				} else {
					protectedList.add(fullAccess.toString());
					protectedList.add(selfProtect.toString());
					
					if (protectedList.size() == FullAccess.values().length + SelfProtection.values().length - 1) {
						writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION slaps " + Divine.getSender() + " with double rainbows all the way.\001\n");
						protectedList.clear();
					}
				}
			}
		}
	}
	
	// Bot Command for checking version
	
	public void commandVersion() throws IOException {
		writer.writeBytes("NOTICE " + Divine.getSender() + " :--{ Divine Version 0.4 }--\n");
		writer.writeBytes("NOTICE " + Divine.getSender() + " :InDev NC IRC Bot\n");
		writer.writeBytes("NOTICE " + Divine.getSender() + " :Developer - NodinChan\n");
		writer.writeBytes("NOTICE " + Divine.getSender() + " :Last updated - 10.11.2011\n");
	}
}
