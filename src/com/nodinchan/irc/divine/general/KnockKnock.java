package com.nodinchan.irc.divine.general;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.nodinchan.irc.divine.Divine;

public class KnockKnock {
	
	private DataOutputStream writer;
	
	private String read;
	private String sentence;
	
	private List<String> knockknock = new LinkedList<String>();
	private List<String> knock = new LinkedList<String>();
	
	public KnockKnock() {
		read = Divine.getRead();
		writer = Divine.getWriter();
	}
	
	public void onGame() throws IOException {
		sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "");
		
		if (sentence.toLowerCase().contains("knock knock") && !sentence.toLowerCase().contains("game")) {
			writer.writeBytes("PRIVMSG " + read.split(" ")[2] + " :Who's there?\n");
			knockknock.add(read.split("!")[0].substring(1));
			return;
		}
		
		if (knockknock.contains(read.split("!")[0].substring(1))) {
			writer.writeBytes("PRIVMSG " + read.split(" ")[2] + " :" + sentence.substring(4) + " who?\n");
			knockknock.remove(read.split("!")[0].substring(1));
			knock.add(read.split("!")[0].substring(1));
			return;
		}
		
		if (knock.contains(read.split("!")[0].substring(1))) {
			writer.writeBytes("PRIVMSG " + read.split(" ")[2] + " :\001ACTION knocks on " + sentence.substring(4) + "'s head.\001\n");
			knock.remove(read.split("!")[0].substring(1));
			return;
		}
	}
}
