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
	private MediaPlayer eatSound;
	private MediaPlayer audio;
	private MediaPlayer clapSound;
	private MediaPlayer gameOverVoice;
	private MediaPlayer crashSound;
	private MediaPlayer buzzSound;
	private Media gameOver;
	private Media sound;
	private Media eat;
	private Media clap;
	private Media crash;
	private Media buzzer;
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
			eat = new Media(getClass().getResource("/Utils/Sound/eating.mp3").toURI().toString());
			clap = new Media(getClass().getResource("/Utils/Sound/clap.mp3").toURI().toString());
			crash = new Media(getClass().getResource("/Utils/Sound/crash.mp3").toURI().toString());
			buzzer = new Media(getClass().getResource("/Utils/Sound/buzzer.mp3").toURI().toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// initializing media player with the music
		audio = new MediaPlayer(sound);
		gameOverVoice = new MediaPlayer(gameOver);
		eatSound = new MediaPlayer(eat);
		clapSound = new MediaPlayer(clap);
		crashSound = new MediaPlayer(crash);
		buzzSound = new MediaPlayer(buzzer);
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

	public MediaPlayer getEatSound() {
		return eatSound;
	}

	public MediaPlayer getClapSound() {
		return clapSound;
	}

	public MediaPlayer getCrashSound() {
		return crashSound;
	}

	public MediaPlayer getBuzzSound() {
		return buzzSound;
	}
	
	
	
}
