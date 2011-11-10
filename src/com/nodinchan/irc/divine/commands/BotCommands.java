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
	
	// Bot Command about Divine
	
	public void commandDivine() throws IOException {
		writer.writeBytes("PRIVMSG " + read.split(" ")[2] + " :\001ACTION is NodinChan's Friend Bot!\n");
	}
	
	// Bot Command for joining channels
	
	public void commandJoin() throws IOException {
		if (read.split(" ")[4].startsWith("#")) {
			writer.writeBytes("JOIN " + read.split(" ")[4] + "\n");
			
		} else {
			writer.writeBytes("JOIN #" + read.split(" ")[4] + "\n");
			
		}
	}
	
	// Bot Command for leaving channels
	
	public void commandPart() throws IOException {
		writer.writeBytes("PART " + read.split(" ")[2] + "\n");
	}
	
	// Bot Command for bot to speak
	
	public void commandSay() throws IOException {
		if (read.startsWith(":NodinChan!") || read.startsWith(":Nodin!") || read.startsWith(":NinjaChan!") || read.startsWith(":NoodleChan!") || read.startsWith(":DivineHero!") || read.split(" ")[4].contains("NCSC")) {
			String sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "").replace(read.split(" ")[3], "").replace(read.split(" ")[4], "");
			writer.writeBytes("PRIVMSG " + read.split(" ")[4] + " :" + sentence.substring(5) + "\n");
		}
	}
	
	// Bot Command for bot slap
	
	public void commandSlap() throws IOException {
		if (!read.split(" ")[4].contains("NodinChan") || !read.split(" ")[4].contains("Nodin") || !read.split(" ")[4].contains("NinjaChan") || !read.split(" ")[4].contains("NoodleChan") || !read.split(" ")[4].contains("DivineHero") || !read.split(" ")[4].contains("NCSC") || !read.split(" ")[4].contains("Divine")) {
			writer.writeBytes("PRIVMSG " + read.split(" ")[2] + " :\001ACTION slaps " + read.split(" ")[4] + " with double rainbows all the way.\001\n");
		}
	}
	
	// Bot Command for checking version
	
	public void commandVersion() throws IOException {
		writer.writeBytes("NOTICE " + read.split("!")[0].substring(1) + " :--{ Divine Version 0.4 }--\n");
		writer.writeBytes("NOTICE " + read.split("!")[0].substring(1) + " :InDev NC IRC Bot\n");
		writer.writeBytes("NOTICE " + read.split("!")[0].substring(1) + " :Developer - NodinChan\n");
		writer.writeBytes("NOTICE " + read.split("!")[0].substring(1) + " :Last updated - 9.11.2011\n");
	}
}
