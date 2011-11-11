package com.nodinchan.irc.divine.commands;

import java.io.DataOutputStream;
import java.io.IOException;

import com.nodinchan.irc.divine.Divine;

public class BotCommands {
	
	private DataOutputStream writer;
	
	private String read;
	
	public BotCommands() {
		this.read = Divine.getRead();
		this.writer = Divine.getWriter();
	}
	
	// Bot Command for bringing up the Control Panel
	
	public void commandControlPanel() throws IOException {
		if (read.startsWith(":NodinChan!") || read.startsWith(":Nodin!") || read.startsWith(":NinjaChan!") || read.startsWith(":NoodleChan!") || read.startsWith(":DivineHero!") || read.split(" ")[4].contains("NCSC")) {
			writer.writeBytes("PRIVMSG " + Divine.getSender() + " :- Control Panel Initiated -\n");
		}
	}
	
	// Bot Command about Divine
	
	public void commandDivine() throws IOException {
		writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION is NodinChan's Friend Bot!\001\n");
	}
	
	// Bot Command for joining channels
	
	public void commandJoin() throws IOException {
		if (read.split(" ")[4].startsWith("#")) {
			writer.writeBytes("JOIN " + read.split(" ")[4] + "\n");
			
		} else {
			writer.writeBytes("JOIN #" + read.split(" ")[4] + "\n");
			
		}
	}
	
	// Bot Command for bot to do action
	
	public void commandAction() throws IOException {
		if (read.split(" ")[4].startsWith("#")) {
			if (read.startsWith(":NodinChan!") || read.startsWith(":Nodin!") || read.startsWith(":NinjaChan!") || read.startsWith(":NoodleChan!") || read.startsWith(":DivineHero!") || read.split(" ")[4].contains("NCSC")) {
				String sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "").replace(read.split(" ")[3], "").replace(read.split(" ")[4], "");
				writer.writeBytes("PRIVMSG " + read.split(" ")[4] + " :\001ACTION " + sentence.substring(5) + "\001\n");
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
		if (read.startsWith(":NodinChan!") || read.startsWith(":Nodin!") || read.startsWith(":NinjaChan!") || read.startsWith(":NoodleChan!") || read.startsWith(":DivineHero!") || read.split(" ")[4].contains("NCSC")) {
			String sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(Divine.getChannel(), "").replace(read.split(" ")[3], "").replace(read.split(" ")[4], "");
			writer.writeBytes("PRIVMSG " + read.split(" ")[4] + " :" + sentence.substring(5) + "\n");
		}
	}
	
	// Bot Command for bot slap
	
	public void commandSlap() throws IOException {
		String argOne = read.split(" ")[4];
		if (!argOne.toLowerCase().contains("nodinchan") || !argOne.toLowerCase().contains("nodin") || !argOne.toLowerCase().contains("ninjachan") || !argOne.toLowerCase().contains("noodlechan") || !argOne.toLowerCase().contains("divinehero") || !argOne.toLowerCase().contains("ncsc") || !argOne.toLowerCase().contains("divine")) {
			if (!argOne.toLowerCase().contains("herself") || !argOne.toLowerCase().contains("himself") || !argOne.toLowerCase().contains("itself")) {
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION slaps " + argOne + " with double rainbows all the way.\001\n");
			} else {
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION slaps " + Divine.getSender() + " with double rainbows all the way.\001\n");
			}
		} else {
			writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION slaps " + Divine.getSender() + " with double rainbows all the way.\001\n");
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
