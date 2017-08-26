package com.karvis;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import com.darkprograms.speech.recognizer.FlacEncoder;
import com.darkprograms.speech.recognizer.Recognizer;

public class Karvis extends JFrame implements KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea dialog = new JTextArea(10,50);
	JTextField input = new JTextField(50);
	
	
	 static JFileChooser fr = new JFileChooser();
     static FileSystemView fw = fr.getFileSystemView();
     static String fx = fw.getDefaultDirectory().toString();
     
	public static String fileflac =fx+"\\karvis.flac";
	public static String filewav = fx+"\\karvis.wav";
	

	JScrollPane scroll=new JScrollPane(
			dialog,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);
	
	
	GRequest rec = new GRequest();
	
	public static void main(String[] args) throws IOException{
		new Karvis();
	}
		
	public Karvis() throws IOException {
		JPanel panel = new JPanel();
		

		setTitle("KARVIS");
		setSize(800,600);
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		dialog.setBackground(Color.decode("#FFFFFF"));
		dialog.setEditable(false);
		
		
		Image img;
		
			img = ImageIO.read(getClass().getResource("/mic.png"));
		
		JButton btn = new JButton();
		btn.setIcon(new ImageIcon(img));
		btn.setBorder(null);
		btn.setBackground(null);
		btn.setBounds(50, 110, 200, 30);
		input.setBounds(100, 400, 200, 400);
		
		
		input.addKeyListener(this);
		panel.setLayout(new FlowLayout());
		 panel.setBackground(Color.decode("#008000"));
		 panel.add(scroll);
		panel.add(btn);
		panel.add(dialog);
		panel.add(input);
		
		JScrollPane scrolle = new JScrollPane(panel);

		
		scrolle.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scrolle.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		
      add(scrolle);
		 add(panel);
		 
		 
		 validate();
		 repaint();
		 setVisible(true);
		 
		 /* welcomes user */
		 
		 String username = System.getProperty("user.name");
	 
		 addText("\n-->Karvis:\tWelcome "+username+" How May I Be Of Asistance To You!");
		 new SpeechText("Welcome "+username+" How May I Be Of Asistance To You!").speak();
		 
		 
		 
		 /* Listener for Microphone Button Click */
		 
		 btn.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent t) {
					
					boolean r = false;
					
					addText("\n-->Karvis:\tCapturing Audio!");

	try {
		GRequest.capture();
		
		
	} catch (LineUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	addText("\n-->Karvis:\tCapturing Done!");
	try {
		
		con();
		
		conVert();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
				
	}
				
		 });
				
	}
		
	
	
			
	
			
			
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			input.setEditable(false);
			//-----grab quote-----------
			String quote=input.getText();
			input.setText("");
			addText("\n-->You:\t"+quote);
			try {
				command(quote);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean con() {
			Process ffmpeg;
		
		try {
		ProcessBuilder pb = new ProcessBuilder("ffmpeg","-i",filewav, fileflac);
		pb.redirectErrorStream();
		
		ffmpeg = pb.start();
		} catch (IOException dx) {
		dx.printStackTrace();
		}
		return true;	
	}
	
	public void conVert() throws IOException {
		File fw = new File(filewav);
		File ff = new File(fileflac);
		
		final Recognizer reco = new Recognizer();
		
		 while(!ff.exists()){
	          try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		if(fw.exists() && !fw.isDirectory()) { 
			
		   reco.setApiKey("AIzaSyDlgWQfzcfaruic2Lmq0FM_AhmsOn5vPlc");
			reco.setLanguage("en-us");
		
          
		
			
			
			String val = Arrays.toString(reco.rawRequest(ff, 10000, 44100));
			System.out.println(val);
			command(val);
			
		ff.delete();
		
}

	}
	
	
	
	
	/* Commands  From Audio Output */
	
public void command(String val) throws IOException {
		val = val.toLowerCase();
		
		
		if(val.contains("speak")) {
			val = val.replace("speak", "");
			new SpeechText(val).speak();
		}
		
		else if(val.contains("notepad")) {
			System.out.println("notepad opened!");
			new SpeechText("notepad opened!").speak();
			addText("\n-->Karvis:\t notepad opened!");
			
			try {
				Process	n = Runtime.getRuntime().exec(
						       "\"c:/windows/notepad.exe\"");
					 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	
		else if(val.contains("shutdown") || val.contains("shut") && val.contains("down")) {
			System.out.println("shutdown in 10 seconds!");
			new SpeechText("shutdown in 10 seconds!").speak();
			addText("\n-->Karvis:\t System will shutdown in 10 seconds");
			
			Runtime r = Runtime. getRuntime();
			r. exec("shutdown -s -t 10");
			
		}
		
		else if(val.contains("logoff")) {
			System.out.println("logsout!");
			new SpeechText("system logging out!").speak();
			addText("\n-->Karvis:\t logging out!");
			
			Runtime r = Runtime. getRuntime();
			r. exec("shutdown -l");
			
		}
		
		
		else if(val.contains("sleep") || val.contains("hibernate")) {
			System.out.println("hibernates!");
			new SpeechText("system entered sleep mode!").speak();
			addText("\n-->Karvis:\t entered sleep mode!");
			
			Runtime r = Runtime. getRuntime();
			r. exec("shutdown -h");
			
		}
		
		
		else if(val.contains("exit")) {
			System.out.println("karvis shutdown");
			new SpeechText("Goodbye Sir, Let Me Know If You Need Anything!!").speak();
			addText("\n-->Karvis:\t Goodbye Sir, Let Me Know If You Need Anythong!!");
			System.exit(0); 
		}
		
		else if(val.contains("word")) {
			System.out.println("word");
			new SpeechText("Microsoft word opened!").speak();
			addText("\n-->Karvis:\t Msword opened!");
			 try {
			     if (Desktop.isDesktopSupported()) {
			       Desktop.getDesktop().open(new File("c:\\karvis.doc"));
			     }
			   } catch (IOException ioe) {
			     ioe.printStackTrace();
			  }
		}
		
		
		else if(val.contains("email") || val.contains("mail")) {
			System.out.println("Email opened!");
			new SpeechText("Email opened!").speak();
			addText("\n-->Karvis:\t Email opened!");
			
			try {
				  Desktop desktop = java.awt.Desktop.getDesktop();
				  URI oURL = new URI("http://gmail.com");
				  URI tURL = new URI("http://login.yahoo.com");
					 
				  desktop.browse(oURL);
				  desktop.browse(tURL);
				} catch (Exception e6) {
				  e6.printStackTrace();
				}
		}
		
		else if(val.contains("facebook")) {
			System.out.println("facebook opened!");
			new SpeechText("facebook opened!").speak();
			addText("\n-->Karvis:\t facebook opened!");
			
			try {
				  Desktop desktop = java.awt.Desktop.getDesktop();
				  URI oURL = new URI("http://www.facebook.com");
				  desktop.browse(oURL);
				} catch (Exception e6) {
				  e6.printStackTrace();
				}
		}
		
		else if(val.contains("twitter") || val.contains("tweet")) {
			System.out.println("twitter opened!");
			new SpeechText("twitter opened!").speak();
			addText("\n-->Karvis:\t twitter opened!");
			
			try {
				  Desktop desktop = java.awt.Desktop.getDesktop();
				  URI oURL = new URI("http://twitter.com");
				  desktop.browse(oURL);
				} catch (Exception e6) {
				  e6.printStackTrace();
				}
		}
		
		
		else if(val.contains("task")) {
			System.out.println("taskmanager opened!");
			new SpeechText("taskmanager opened").speak();
			addText("\n-->Karvis:\t taskmanager opened!");
			
			Runtime rt = Runtime.getRuntime();

			try {
				Process proc = Runtime.getRuntime().exec ("tasklist.exe");
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		
		else if(val.contains("hello")) {
			System.out.println("Hi, how are you");
			new SpeechText("Hi    , how are you").speak();
			addText("\n-->Karvis:\t Hi, How are you!");
			
		}
		
		else if(val.contains("command")) {
			System.out.println("listing commands");
			new SpeechText("listing commands!").speak();
			addText("\n-->Karvis:\t Commands List!\nspeak: text to pronounce\n open email: opens email login\n open notepad: opens notepad\n shutdown: shuts down system\n logoff: logs out of account\n sleep: hibernates system\n ms word: opens ms word\n facebook: opens facebook\n open twitter: opens twitter\n task: opens taskmanager\n exit program: exits Karvis");
			
		}
		
		else if(val.contains("how")) {
			System.out.println("you said how!");
			addText("\n-->Karvis:\t you said how!");
			
		}
		
		
		else {
			System.out.println("Unable to retrieve command");
			addText("\n-->Karvis:\tUnable to retrieve audio command, maybe due to internet issues!");
			
			
		}
		
	}
	

public void addText(String str){
	dialog.setText(dialog.getText()+str);
	input.setEditable(true);
}
		 }
	
