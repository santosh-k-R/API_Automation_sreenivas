package com.cisco.services.api_automation.tests.insights.syslogs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import com.jcraft.jsch.*;
import java.util.Properties;

import com.cisco.services.api_automation.testdata.syslogs.SyslogsData;
import com.cisco.services.api_automation.utils.Commons;

public class SendSyslogMessages {
	private static String data = "";
	private static int port = 514;
	private static String user = System.getenv("simulator_user");
	private static String password = System.getenv("simulator_password");
	private static String host = System.getenv("simulator_ip");
	private static String collectorIP = System.getenv("collector");
	private static String path = SyslogsData.getDIR();

	public static void executeCommand(String eventFile) {
		try {

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");
			
			TimeZone tz = Calendar.getInstance().getTimeZone();
			Date today = Calendar.getInstance().getTime();

			today = Calendar.getInstance().getTime();
			SimpleDateFormat formatter1 = new SimpleDateFormat("MMM dd HH:mm:ss");
			formatter1.setTimeZone(TimeZone.getTimeZone(tz.getDisplayName(false, TimeZone.SHORT)));

			InputStream filename = Commons.getResourceFile(path + eventFile);

			BufferedReader bufReader = new BufferedReader(new InputStreamReader(filename));
			String line = bufReader.readLine()			;
			while (line != null) {
				data = "echo '" + formatter1.format(today) + " " + line + "' | /usr/bin/nc -u -w 1 " + collectorIP + " "
						+ port;
				System.out.println(data)
				;			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(data);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			

			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}			}	
			line = bufReader.readLine();
			channel.disconnect();
		}
			bufReader.close();
			session.disconnect();
			System.out.println("DONE");
			Thread.sleep(300000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
