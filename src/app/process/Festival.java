package app.process;

import java.io.IOException;
import java.io.PrintWriter;

import app.AppModel;

/**
 * Created by Max Griffith on 16/09/2016 Responsible for festival calls within
 * bash, using scheme files to make the calls
 */
public class Festival {

	/**
	 * Says aloud the given word using a temporary word scheme file and process
	 * builder
	 */
	public static void sayWord(String word) throws IOException, InterruptedException {
		// creates temporary scheme txt file with the word parsed as a string
		createTempScript(word);

		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", "festival -b .app_files/.word.scm");
		builder.start();
	}

	/**
	 * Write temporary script with bash festival commands, to speak aloud the
	 * given text
	 */
	private static void createTempScript(String word) throws IOException {
		PrintWriter printWriter = new PrintWriter(".app_files/.word.scm", "UTF-8");

		// Sets voice as the one selected in settings menu
		String voice;
		if (AppModel.getVoice().equals("default")) {
			voice = "voice_kal_diphone";
		} else {
			voice = "voice_akl_nz_jdt_diphone";
		}

		// Writes festival commands to use the selected voice and say desired
		// word aloud
		printWriter.println("(" + voice + ")");
		printWriter.println("(SayText \"" + word + "\")");

		printWriter.close();
	}
}
