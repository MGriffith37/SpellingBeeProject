package app.scene;

import java.io.File;
import java.net.URI;

import app.AppModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * Created by Fraser McIntosh on 19/09/2016. Responsible for building the media
 * player scene that plays the video file
 */
public class MediaPlayerScene {

	/**
	 * Sets scene using separate pop up window
	 */
	public static void setScene() {
		// creates new window and media player that will hold the video file
		final Stage window = new Stage();
		Group root = new Group();
		URI path = new File(".media/hey.mp4").toURI();
		Media media = new Media(path.toString());
		final MediaPlayer player = new MediaPlayer(media);
		MediaView view = new MediaView(player);

		// Components and layouts
		final Slider slider = new Slider();
		final VBox sliderBox = new VBox();
		final HBox controlLayout = new HBox();
		final VBox controls = new VBox();
		sliderBox.getChildren().add(slider);

		// -------------Buttons-----------------------------------------------
		// To close player and window
		Button close = new Button("Close");
		close.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				closeVideo(player, window);
			}
		});

		// To stop video completely
		Button stop = new Button("Stop");
		stop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				player.stop();
			}
		});

		// To pause video where its currently at
		Button pause = new Button("Pause");
		pause.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				player.pause();
			}
		});
		// To play video from where it currently is at
		Button play = new Button("Play");
		play.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				player.play();
			}
		});
		// --------------------------------------------------------------------------

		// Add components to layout
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(close, stop, pause, play);
		controlLayout.setTranslateY(330);
		controls.getChildren().addAll(sliderBox, controlLayout);
		root.getChildren().addAll(view, controls);

		// Change default close behaviour
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				closeVideo(player, window);
			}
		});

		// Display window
		window.initModality(Modality.APPLICATION_MODAL);
		Scene mediaScene = new Scene(root, AppModel.getWidth(), AppModel.getHeight());
		window.setScene(mediaScene);
		window.setResizable(false);
		window.show();

		// Play video
		player.play();
		// Set player dimensions
		player.setOnReady(new Runnable() {
			@Override
			public void run() {
				int w = player.getMedia().getWidth();
				int h = player.getMedia().getHeight();

				window.setMinWidth(w);
				window.setMinHeight(h);
				sliderBox.setMinSize(w, 100);
				sliderBox.setTranslateY(h - 70);

				slider.setMin(0.0);
				slider.setValue(0.0);
				slider.setMax(player.getTotalDuration().toSeconds());
			}
		});

		// Allows slider to accurately represent the current time of video
		player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration duration, Duration current) {
				slider.setValue(current.toSeconds());
			}
		});
		// Allow user to navigate through video using slider
		slider.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				player.seek((Duration.seconds(slider.getValue())));
			}
		});
	}

	/**
	 * Method to close player properly and then close the window
	 */
	private static void closeVideo(MediaPlayer player, Stage window) {
		player.stop();
		window.close();
	}
}
