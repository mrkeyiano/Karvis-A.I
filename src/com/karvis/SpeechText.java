package com.karvis;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
 
public class SpeechText {
 
 private static final String VOICENAME = "kevin16";
 private String text; // string to speech
 
 public SpeechText(String text) {
  this.text = text;
 }
 
 public void speak() {
  Voice voice;
  VoiceManager voiceManager = VoiceManager.getInstance();
  voice = voiceManager.getVoice(VOICENAME);
  voice.allocate();
  voice.speak(text);
 }
 
 
}
