package audio;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
	
	private static Audio audio = new Audio();
	private File file;
	private Clip background;
	
	private Audio() {
	}
	
	public static Audio getInstance() {
		return audio;
	}
	
	public void playBackground(String name) {
		if(background != null)	// 이미 재생중이면 종료
			background.close();  
		background = play(name);
		
		if(name == "smb_background")
			background.loop(Clip.LOOP_CONTINUOUSLY);	// 반복 재생
	}
	
	public Clip play(String name) {
		Clip clip = null;
		try {
			//InputStream audioSrc = getClass().getResourceAsStream("/sound/" + name + ".wav");
            //InputStream bufferedIn = new BufferedInputStream(audioSrc);
			
			file = new File(getClass().getResource("/sound/" + name + ".wav").toURI());
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();	
		} catch (IOException e) {
			e.printStackTrace();	
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clip;
	}
}
