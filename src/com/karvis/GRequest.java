package com.karvis;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.Recognizer;

public class GRequest {

	 static JFileChooser fr = new JFileChooser();
     static FileSystemView fw = fr.getFileSystemView();
     static String fx = fw.getDefaultDirectory().toString();
     
	public static String fileflac =fx+"\\karvis.flac";
	public static String filewav = fx+"\\karvis.wav";
	
	public static void main(String[] args) throws Exception {

		
		Recognizer rec = new Recognizer();
		
		
				
		
		
			
		
			File fw = new File(filewav);
			File ff = new File(fileflac);
			if(fw.exists() && !fw.isDirectory()) { 
				
				try {
					ProcessBuilder pb = new ProcessBuilder("ffmpeg","-i",filewav, fileflac);
					pb.redirectErrorStream();
					pb.start();
					} catch (IOException dx) {
					dx.printStackTrace();
					}	
				rec.setApiKey("AIzaSyAZhMypWluFA8dFTlzU9hYhqjO7nR1bKJo");
				rec.setLanguage("en-us");
			
				
				
			       
				String val =Arrays.toString(rec.rawRequest(ff, 1020202, 44100));
				System.out.println(val);
				
				
				/* loop through commands if match found*/
				
			command(val);
			
			
			/* loop through commands if match found*/
			
			} 
			
			else {
				
			
			capture();
			
			
	}
			
			
			}
	
	
	/* loop through audio text for commands match */ 
	
	public static void command(String val) throws IOException {
		
		if(val.contains("notepad")) {
			System.out.println("notepad opened!");
			
			try {
				Runtime.getRuntime().exec(
						       "\"c:/windows/notepad.exe\"");
					 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	
		else if(val.contains("shutdown")) {
			System.out.println("shutdown in 10 seconds!");
			
			Runtime r = Runtime. getRuntime();
			r. exec("shutdown -s -t 10");
			
		}
		
		else if(val.contains("logoff")) {
			System.out.println("logsout!");
			
			Runtime r = Runtime. getRuntime();
			r. exec("shutdown -l");
			
		}
		
		
		else if(val.contains("sleep")) {
			System.out.println("hibernates!");
			
			Runtime r = Runtime. getRuntime();
			r. exec("shutdown -h");
			
		}
		
		else if(val.contains("word")) {
			System.out.println("word");
		}
		
		else if(val.contains("facebook")) {
			System.out.println("facebook opened!");
			
			try {
				  Desktop desktop = java.awt.Desktop.getDesktop();
				  URI oURL = new URI("http://www.facebook.com");
				  desktop.browse(oURL);
				} catch (Exception e6) {
				  e6.printStackTrace();
				}
		}
		
		else if(val.contains("task")) {
			System.out.println("taskmanager opened!");
			
			Runtime rt = Runtime.getRuntime();

			try {
				rt.exec("taskkill /F /IM notepad.exe");
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		
		else if(val.contains("hello")) {
			System.out.println("you said hello!");
		}
		
		else if(val.contains("how")) {
			System.out.println("you said how!");
		}
		
		else if(val.contains("speak")) {
			new SpeechText(val).speak();
		}
		
		else {
			System.out.println("Unable to retrieve command");
			
		}
		
	}

	
	
	/* capture audio for 3 seconds */
	
	public static boolean capture() throws LineUnavailableException, InterruptedException {
		// TODO Auto-generated method stub
		Microphone mic = new Microphone();
			long t= System.currentTimeMillis();
			long end = t+3000;
			
			System.out.println("Capturing Audio!");
			mic.setFileType(AudioFileFormat.Type.WAVE);
			
			while(System.currentTimeMillis() < end) {
				
				
					mic.captureAudioToFile(filewav);
				
			
					
			
			
			} 
		
			System.out.println("Capturing Audio Done!");
			mic.close();
			
			return true;
			
		
	}
}