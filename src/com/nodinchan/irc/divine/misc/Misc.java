package com.nodinchan.irc.divine.misc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.nodinchan.irc.divine.Divine;

public class Misc {
	
	private DataOutputStream writer;
	
	private String read;
	
	public Misc() throws IOException {
		this.writer = new DataOutputStream(Divine.getSocket().getOutputStream());
		this.read = Divine.getRead();
	}
	
	// Knock Knock Game
	
	public void gameKnockKnock() throws IOException {
		List<String> knockknock = new LinkedList<String>();
		List<String> knock = new LinkedList<String>();
		
		String sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "");
		
		// Say "Who's there?" when "knock knock" is mentioned
		
		if (sentence.substring(4).toLowerCase().startsWith("knock knock")) {
			writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :Who's there?\n");
			
			// Adds the person to the list for continuation of Knock Knock
			
			knockknock.add(read.split("!")[0].substring(1));
			return;
		}
		
		// If the list contains the nickname of the person, respond accordingly
		
		if (knockknock.contains(read.split("!")[0].substring(1))) {
			writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :" + sentence.substring(4) + " who?\n");
			
			// Removes the person from the list and adds the nick to a new one
			
			knockknock.remove(read.split("!")[0].substring(1));
			knock.add(read.split("!")[0].substring(1));
			return;
			
		}
		
		// If the new list contains the nickname of the person, respond accordingly
		
		if (knock.contains(read.split("!")[0].substring(1))) {
			writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION knocks on " + sentence.substring(4) + "'s head.\001\n");
			
			// Game ended
			
			knock.remove(read.split("!")[0].substring(1));
			return;
		}		
	}
}
