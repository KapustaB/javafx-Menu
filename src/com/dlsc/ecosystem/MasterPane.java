package com.dlsc.ecosystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MasterPane extends StackPane {

	private Region background = new Region();

	public enum ItemType {
		BOOKS("Books", "item-books"),

		PEOPLE("People", "item-people"), COMMUNITY("Community",
				"item-community"), TUTORIAL("Tutorial", "item-tutorial"), LINKS(
				"Links", "item-links"), BLOGS("Blogs", "item-blogs"), OPEN_SOURCE(
				"Open Source", "item-open-source"), CLOSED_SOURCE("Commercial",
				"item-closed-source"), HOME("Home", "item-home"), TOOLS(
				"Tools", "item-tools"), MOBILE("Mobile", "item-mobile"), YOUTUBE(
				"YouTube", "item-youtube");

		private String title;

		private String style;

		private ItemType(String title, String style) {
			this.title = title;
			this.style = style;
		}

		public String getTitle() {
			return title;
		}

		public String getStyle() {
			return style;
		}
	}

	private Map<ItemType, Button> buttonMap = new HashMap<>();
	private Map<ItemType, Rectangle2D> smallBoundsMap = new HashMap<>();
	private Map<ItemType, Rectangle2D> largeBoundsMap = new HashMap<>();

	private enum Mode {
		SMALL, LARGE;
	}

	public MasterPane() {
		getChildren().add(background);
		background.getStyleClass().add("master-pane-background");

		getStyleClass().add("master-pane");

		for (ItemType type : ItemType.values()) {
			Button button = new Button(type.getTitle());
			button.setOnAction(evt -> toggleMode());
			button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			button.setAlignment(Pos.CENTER);
			button.getStyleClass().add("menu-button");
			getChildren().add(button);
			button.setManaged(false);
			buttonMap.put(type, button);

			ImageView imageView = null;
			switch (type) {
			case BOOKS:
				imageView = new ImageView(MasterPane.class.getResource(
						"eco_white/book.png").toExternalForm());
				break;
			case BLOGS:
				imageView = new ImageView(MasterPane.class.getResource(
						"eco_white/rss.png").toExternalForm());
				break;
			case CLOSED_SOURCE:
				imageView = new ImageView(MasterPane.class.getResource(
						"eco_white/box_closed.png").toExternalForm());
				break;
			case COMMUNITY:
				imageView = new ImageView(MasterPane.class.getResource(
						"eco_white/users3.png").toExternalForm());
				break;
			case HOME:
				imageView = new ImageView(MasterPane.class.getResource(
						"eco_white/home.png").toExternalForm());
				break;
			case LINKS:
				imageView = new ImageView(MasterPane.class.getResource(
						"eco_white/link.png").toExternalForm());
				break;
			case MOBILE:
				imageView = new ImageView(MasterPane.class.getResource(
						"eco_white/smartphone.png").toExternalForm());
				break;
			case OPEN_SOURCE:
				imageView = new ImageView(MasterPane.class.getResource(
						"eco_white/box_open.png").toExternalForm());
				break;
			case PEOPLE:
				imageView = new ImageView(MasterPane.class.getResource(
						"eco_white/user.png").toExternalForm());
				break;
			case TOOLS:
				imageView = new ImageView(MasterPane.class.getResource(
						"eco_white/tools.png").toExternalForm());
				break;
			case TUTORIAL:
				imageView = new ImageView(MasterPane.class.getResource(
						"eco_white/graduate.png").toExternalForm());
				break;
			case YOUTUBE:
				imageView = new ImageView(MasterPane.class.getResource(
						"eco_white/movie.png").toExternalForm());
				break;
			}

			// imageView.setFitWidth(128);
			// imageView.setFitHeight(128);

			button.setGraphic(imageView);
		}

		widthProperty().addListener(it -> calculateBounds());
		heightProperty().addListener(it -> calculateBounds());
	}

	private void toggleMode() {
		if (getMode().equals(Mode.LARGE)) {
			mode.set(Mode.SMALL);
		} else {
			mode.set(Mode.LARGE);
		}

		animate();
	}

	private void animate() {
		List<KeyValue> keyValues = new ArrayList<>();
		for (ItemType itemType : ItemType.values()) {
			Rectangle2D targetBounds = null;
			double scale = 1;
			switch (getMode()) {
			case LARGE:
				targetBounds = largeBoundsMap.get(itemType);
				break;
			case SMALL:
				targetBounds = smallBoundsMap.get(itemType);
				scale = .25;
				break;
			}

			Button button = buttonMap.get(itemType);

			button.layoutXProperty().addListener(
					it -> System.out.println(button.getLayoutX()));

			KeyValue value1 = new KeyValue(button.scaleXProperty(), scale);
			KeyValue value2 = new KeyValue(button.scaleYProperty(), scale);
			KeyValue value3 = new KeyValue(button.layoutXProperty(),
					targetBounds.getMinX());
			KeyValue value4 = new KeyValue(button.layoutYProperty(),
					targetBounds.getMinY());

			keyValues.add(value1);
			keyValues.add(value2);
			keyValues.add(value3);
			keyValues.add(value4);

			button.scaleXProperty().addListener(
					it -> button.resize(button.getScaleX() * 128,
							button.getScaleY() * 128));
		}

		KeyValue[] values = new KeyValue[keyValues.size()];
		keyValues.toArray(values);
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(.5), values);
		Timeline timeline = new Timeline(keyFrame);
		timeline.play();
	}

	private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(this,
			"mode", Mode.LARGE);

	private Mode getMode() {
		return mode.get();
	}

	private void calculateBounds() {
		double smallIconSize = 32;
		double largeIconSize = 128;

		double smallGap = 20;
		double largeGap = 40;

		double x = 40;
		double y = 20;

		for (ItemType type : ItemType.values()) {
			smallBoundsMap.put(type, new Rectangle2D(x, y, smallIconSize,
					smallIconSize));
			x += smallIconSize + smallGap;
		}

		x = getWidth() / 2 - 2 * largeIconSize - largeGap - largeGap / 2;
		y = getHeight() / 2 - 1.5 * largeIconSize - largeGap - largeGap / 2;

		int counter = 0;
		for (ItemType type : ItemType.values()) {
			largeBoundsMap.put(type, new Rectangle2D(x, y, largeIconSize,
					largeIconSize));
			x += largeIconSize + largeGap;
			counter++;
			if (counter % 4 == 0) {
				y += largeIconSize + largeGap;
				x = getWidth() / 2 - 2 * largeIconSize - largeGap - largeGap
						/ 2;
			}
		}

		System.out.println("small: " + smallBoundsMap.get(ItemType.BOOKS));
		System.out.println("large: " + largeBoundsMap.get(ItemType.BOOKS));

		requestLayout();
	}

	@Override
	protected void layoutChildren() {
		super.layoutChildren();

		for (ItemType type : ItemType.values()) {
			Button button = buttonMap.get(type);
			Rectangle2D bounds = null;
			switch (getMode()) {
			case LARGE:
				bounds = largeBoundsMap.get(type);
				break;
			case SMALL:
				bounds = smallBoundsMap.get(type);
				break;
			}
			button.resizeRelocate(bounds.getMinX(), bounds.getMinY(),
					bounds.getWidth(), bounds.getHeight());
		}
	}
}
