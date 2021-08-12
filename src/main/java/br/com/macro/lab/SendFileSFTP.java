package br.com.macro.lab;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SendFileSFTP {

	public SendFileSFTP() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	private ChannelSftp setupJsch() throws JSchException {
	    String password="";
	    String username="";
	    String remoteHost="";
		
	    JSch 
	    jsch = new JSch();
	    jsch.setKnownHosts("/Users/john/.ssh/known_hosts");
	    
	    Session 
	    jschSession = jsch.getSession(username, remoteHost);
	    jschSession.setPassword(password);
	    jschSession.connect();
	    return (ChannelSftp) jschSession.openChannel("sftp");
	}	
	
}
