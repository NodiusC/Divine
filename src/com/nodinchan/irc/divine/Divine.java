package com.nodinchan.irc.divine;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.nodinchan.irc.divine.general.Chat;

/** Divine 0.4
 * Copyright (C) 2011  Nodin 'NodinChan' Chan <nodinchan@nodinchan.net>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class Divine {
	
	private static Socket clientSocket;
	public static DataOutputStream writer;
	private static BufferedReader reader;
	
	public static String read;
	public static String sentence;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		clientSocket = new Socket("irc.esper.net", 6667);
		
		writer = new DataOutputStream(getSocket().getOutputStream());
		reader = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
		
		writer.writeBytes("USER NC-Bot 0 * :Divine Ver. 0.4\n");
		writer.writeBytes("NICK Divine\n");
		
		Boolean nickserv = true;
		
		while (true) {
			if (reader.ready()) {
				read = reader.readLine();
				System.out.println(read);
				
				if(read.startsWith("PING")) {
					writer.writeBytes("PONG " + read.split(" ")[1] + "\n");
					
				} else if (read.startsWith(":NickServ!NickServ@services.esper.net") && nickserv) {
					writer.writeBytes("PRIVMSG NickServ :IDENTIFY <ACCOUNT> <PASSWORD>\n");
					writer.writeBytes("JOIN #NodinChan\n");
					writer.writeBytes("JOIN #Divine\n");
					
					nickserv = false;
				}
				
				onCommand();
			}
		}
	}
	
	public static String getChannel() {
		return read.split(" ")[2];
	}
	
	// Returns the line of chat
	
	public static String getRead() {
		return read;
	}
	
	// Returns the sender of the line
	
	public static String getSender() {
		return read.split("!")[0].substring(1);
	}
	
	// Returns the server
	
	public static Socket getSocket() {
		return clientSocket;
	}
	
	// Returns the writer for writing lines
	
	public static DataOutputStream getWriter() {
		return writer;
	}
	
	// Used when ever there is chat
	
	public static void onCommand() throws IOException {
		String ircCommand = read.split(" ")[1];
		
		IRCCommand[] commandlist = IRCCommand.values();
		
		for (IRCCommand command : commandlist) {
			if (ircCommand.contains(command.toString())) {
				
				switch (command) {
				
				// Called when a person joins
				
				case JOIN:
					onJoin();
					break;
					
				// Called when there is chat
					
				case PRIVMSG:
					onChat();
					break;
					
				// Called when someone changes nickname
					
				case NICK:
					break;
					
				// Called when someone is killed?
					
				case KILL:
					writer.writeBytes("PRIVMSG " + getChannel() + " :RIP " + read.split("!")[0] + "\n");
					break;
					
				// Called when invited
					
				case INVITE:
					break;
					
				// Called when there is mode change
					
				case MODE:
					break;
					
				// Called when someone quits
					
				case QUIT:
					writer.writeBytes("PRIVMSG " + getChannel() + " :Nice seeing you, " + getSender() + "!\n");
					break;
					
				// Called when someone parts
					
				case PART:
					writer.writeBytes("PRIVMSG " + getChannel() + " :Nice seeing you, " + getSender() + "!\n");
					break;
					
				// Called when someone is kicked
					
				case KICK:
					writer.writeBytes("PRIVMSG " + getChannel() + " :Hasta la vista, " + read.split(" ")[3] + "! Mwuahahahahaha!\n");
					break;
				}
			}
		}
	}
	
	// Responses to joining
	
	public static void onJoin() throws IOException {
		if (read.startsWith(":Divine!")) {
			writer.writeBytes("PRIVMSG " + getChannel() + " :Hi guys!\n");
			
		} else {
			writer.writeBytes("PRIVMSG " + getChannel() + " :Welcome to " + getChannel() + ", " + getSender() + "!\n");
			
		}
	}
	
	// Responses to chat
	
	public static void onChat() throws IOException {
		if (read.split(" ")[3].contains(":!")) {
			new Chat().onPrivMsg();
			
		} else {
			new Chat().onPhrase();
			
		}
	}
	
	// IRC Commands
	
	public enum IRCCommand {
		JOIN,
		PRIVMSG,
		NICK,
		KILL,
		INVITE,
		MODE,
		QUIT,
		PART,
		KICK,
	}
}
