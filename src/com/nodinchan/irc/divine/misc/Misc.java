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
	
	public void onGames() throws IOException {
		new KnockKnock().onGame();
	}
	
	// Knock Knock Game
	
	public class KnockKnock {
		
		List<String> knockknock = new LinkedList<String>();
		List<String> knock = new LinkedList<String>();
		
		public KnockKnock() {
			
		}
		
		public void onGame() throws IOException {
			
			String sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "");
			
			if (sentence.substring(4).toLowerCase().startsWith("knock knock")) {
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :Who's there?\n");
				
				knockknock.add(read.split("!")[0]);
				
			} else if (knockknock.contains(read.split("!")[0])) {
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :" + sentence.substring(4) + " who?\n");
				
				knock.add(read.split("!")[0]);
				
				if (knock.contains(read.split("!")[0])) {
					knockknock.remove(read.split("!")[0].substring(1));
				}
				
			} else if (knock.contains(read.split("!")[0])) {
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION knocks on " + sentence.substring(4) + "'s head.\001\n");
				
				knock.remove(read.split("!")[0]);
				
			}
		}
	}
}
