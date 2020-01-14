package Utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
	private Media sound;
	private MediaPlayer audio;
	private MediaPlayer gameOverVoice;
	private Media gameOver;
	public static Double startTime = 0.0;
	public static Double endTime = 153.0;

	/**
	 * The default sound constructor. Initializes the media objects
	 * @throws URISyntaxException if bad file path
	 */
	public Sound(){
		// getting the music file from Utils directory
		try {
			sound = new Media(getClass().getResource("/Utils/Sound/Motivated.mp3").toURI().toString());
			gameOver = new Media(getClass().getResource("/Utils/Sound/gameOver.mp3").toURI().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// initializing media player with the music
		audio = new MediaPlayer(sound);
		gameOverVoice = new MediaPlayer(gameOver);
	}
	
	/**
	 * Return the object that handles the music
	 * @return The MediaPlayer object audio
	 */
	public MediaPlayer getAudio() {
		return audio;
	}

	public MediaPlayer getGameOverVoice() {
		return gameOverVoice;
	}
	
	
	
}
