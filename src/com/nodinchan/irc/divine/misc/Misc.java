package com.nodinchan.irc.divine.misc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.nodinchan.irc.divine.Divine;

public class Misc {
	
	private static DataOutputStream writer;
	
	private static String read;
	
	public Misc() throws IOException {
		writer = new DataOutputStream(Divine.getSocket().getOutputStream());
		read = Divine.getRead();
	}
	
	// Running the games
	
	public void onGames() throws IOException {
		new KnockKnock().onGame();
	}
	
	// Running the Utils
	
	public void onUtils() throws IOException {
		new Calculator().onUtil();
	}
	
	// Knock Knock Game
	
	public static class KnockKnock {
		
		private static List<String> knockknock = new LinkedList<String>();
		private static List<String> knock = new LinkedList<String>();
		
		public KnockKnock() {
			
		}
		
		public void onGame() throws IOException {
			
			String sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "");
			
			if (sentence.substring(4).toLowerCase().startsWith("knock knock")) {
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :Who's there?\n");
				
				knockknock.add(read.split("!")[0]);
				
			} else if (knockknock.contains(read.split("!")[0])) {
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :" + sentence.substring(4) + " who?\n");
				
				knockknock.remove(read.split("!")[0]);
				knock.add(read.split("!")[0]);
				
			} else if (knock.contains(read.split("!")[0])) {
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :\001ACTION knocks on " + sentence.substring(4) + "'s head.\001\n");
				
				knock.remove(read.split("!")[0]);
				
			}
		}
	}
	
	// Still in the process of thinking of a good calculator code
	
	public class Calculator {
		
		public Calculator() {
			
		}
		
		public void onUtil() {
			String command = read.split(" ")[3].toLowerCase();
			
			if (command.contains(":!calculate")) {
				
			}
		}
	}
}
