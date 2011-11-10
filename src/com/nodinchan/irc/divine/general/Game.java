package com.nodinchan.irc.divine.general;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.nodinchan.irc.divine.Divine;

public class Game {
	
	private DataOutputStream writer;
	
	private List<String> knockknock = new ArrayList<String>();
	private List<String> knock = new ArrayList<String>();
	
	public Game() {
		this.writer = Divine.getWriter();
	}
	
	public void onGames() throws IOException {
		String read = Divine.getRead();
		String sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "");
		String name = Divine.getSender();
		
		// Knock Knock
		
		if (sentence.substring(4).toLowerCase().startsWith("knock knock")) {
			writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :Who's there?\n");
			knockknock.add(name);
			return;
		}
		
		if (knockknock.contains(name)) {
			writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :" + sentence.substring(4) + " who?\n");
			knockknock.remove(name);
			knock.add(name);
			return;
		}
		
		if (knock.contains(name)) {
			writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION knocks on " + sentence.substring(4) + "'s head.\001\n");
			knock.remove(name);
			return;
		}
	}
}
