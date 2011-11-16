package com.nodinchan.irc.divine.sixws;

import java.io.DataOutputStream;
import java.io.IOException;

import com.nodinchan.irc.divine.Divine;
import com.nodinchan.irc.divine.chat.Chat;

public class How {
	
	private static DataOutputStream writer;
	
	private static String read;
	
	public How() throws IOException {
		writer = new DataOutputStream(Divine.getSocket().getOutputStream());
		read = Divine.getRead();
	}
	
	// Whenever a sentence contains "how"
	
	public void onHow() throws IOException {
		
		String sentence = read.replace(read.split(" ")[0], "").replace(read.split(" ")[1], "").replace(read.split(" ")[2], "");
		
		if (sentence.toLowerCase().contains("how")) {
			if (sentence.toLowerCase().contains("are you")) {
				writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :I'm fine, thank you.\n");
			} else if (sentence.toLowerCase().contains("do i")) {
				if (sentence.contains("register")) {
					writer.writeBytes("PRIVMSG " + Divine.getChannel() + " :Are you asking about registering you nickname?\n");
					Chat.addHow("registernick" + read.split("!")[0], read.split("!")[0]);
				}
			}
		}
	}
}
