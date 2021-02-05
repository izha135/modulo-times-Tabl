
package jhaugh.timestables;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


import java.text.DecimalFormat;

    public class Main extends Application {
        /**
         * Screen constants. If you change these make sure you
         * "Rebuild" the project. A simple "Build" will not
         * properly update the offsets.
         */
        public static final double WIDTH = 1500;
        public static final double HEIGHT = 700;
        public static final double OFFSET_W = WIDTH / 2;
        public static final double OFFSET_H = HEIGHT / 2;
        @Override
        public void start(Stage primaryStage) throws Exception {
            /**
             * Window Setup
             */
            primaryStage.setTitle("Times Table Visualization");

            // Initial times table number
            double ttn = 2;
            // Circle that surrounds the lines
            final Circle circle = new Circle(WIDTH / 2, HEIGHT / 2, 300);
            circle.setFill(Color.TRANSPARENT);
            circle.setStroke(Color.BLACK);

            // Spacing between controls
            double spacing = 10;

            // Vertical alignment box to hold all the controls
            VBox controls = new VBox(spacing);
            controls.setLayoutX(10);
            controls.setLayoutY(100);

            Button runButton = new Button("run");
            Button pauseButton = new Button("pause");


            VBox vbox = new VBox(runButton, pauseButton);
            Scene scene_ = new Scene(vbox, 200,100);
            primaryStage.setScene(scene_);
            primaryStage.show();





            // Times table number display and corresponding HBox
            HBox ttnBox = new HBox(spacing);
            Label ttnLabel = new Label("Times Table Number:");
            DecimalFormat dfTtn = new DecimalFormat("#.0");
            Label ttnValueLabel = new Label(Double.toString(ttn));
            ttnBox.getChildren().addAll(ttnLabel, ttnValueLabel);

            // Increment slider and corresponding HBox
            HBox stepNumBox = new HBox(spacing);
            Label stepNumLabel = new Label("Increment By:");
            Slider stepNumSlider = new Slider(0, 5, 1);
            stepNumSlider.setShowTickMarks(true);
            stepNumSlider.setShowTickLabels(true);
            stepNumSlider.setMajorTickUnit(0.25f);
            stepNumSlider.setBlockIncrement(0.1f);
            stepNumBox.getChildren().addAll(stepNumLabel, stepNumSlider);

            // Delay slider and corresponding HBox
            HBox delayBox = new HBox(spacing);
            Label delayLabel = new Label("Delay By(seconds):");
            Slider delaySlider = new Slider(0, 5, 0.5);
            delaySlider.setShowTickMarks(true);
            delaySlider.setShowTickLabels(true);
            delaySlider.setMajorTickUnit(0.25f);
            delaySlider.setBlockIncrement(0.1f);
            delayBox.getChildren().addAll(delayLabel, delaySlider);

            // Controls for jumping to specific parameters
            Label jumpToLabel = new Label("Jump To Parameters Section");

            // Times table number to jump to and corresponding HBox
            HBox timesTableNumBox = new HBox(spacing);
            Label timesTableNumLabel = new Label("Times Table Number:");
            TextField timesTableNumTF = new TextField("2");
            timesTableNumTF.setTextFormatter(DecimalTextVerifier.getFormatter());
            timesTableNumBox.getChildren().addAll(timesTableNumLabel, timesTableNumTF);

            // Number of points around the circle input box and
            // corresponding HBox
            HBox numPointsBox = new HBox(spacing);
            Label numPointsLabel = new Label("Number Of Points:");
            TextField numPointsTF = new TextField("360");
            numPointsTF.setTextFormatter(DecimalTextVerifier.getFormatter());
            numPointsBox.getChildren().addAll(numPointsLabel, numPointsTF);

            // Button to jump to given parameters and pause visualization
            Button jumpToButton = new Button("Jump To");
            //Button runButton = new Button("Run");
            //Button pauseButton = new Button("Pause");
            // Add all controls to the vertical box


            controls.getChildren().addAll(
                    ttnBox, stepNumBox, delayBox,
                    jumpToLabel, timesTableNumBox, numPointsBox, jumpToButton,runButton,pauseButton);
            // Create the canvas
            Canvas canvas = new Canvas(WIDTH, HEIGHT);

            Pane root = new Pane(canvas, circle, controls);
            Scene scene = new Scene(root, WIDTH, HEIGHT);

            primaryStage.setScene(scene);
            primaryStage.show();
            // Create a new visualization object which will generate the
            // lines within the circle for each frame.
            Visualization visualization = new Visualization(ttn, 300);
            // The main "loop"
            class MyAnimationTimer extends AnimationTimer {
                private long lastUpdate = 0;

                public void run(boolean jumpTo) {
                    // Remove old lines
                    root.getChildren().removeIf(node -> node instanceof Group);
                    // Generate the new lines
                    Group lines = visualization.generateLines(
                            Double.parseDouble(numPointsTF.getText()));
                    // Add the new lines to the graph
                    root.getChildren().add(lines);
                    // Update controls
                    ttnValueLabel.setText(dfTtn.format(visualization.getTimesTableNum()));
                    // Only increment the times table number if this is being run
                    // as part of the main visualization loop.
                    if (!jumpTo) {
                        // Increment the current times table number
                        visualization.incrementTimesTableNum(stepNumSlider.getValue());
                    }
                }
                /**
                 * First check if desired amount of seconds have elapsed since last
                 * update. This is determined by the delaySlider value which is in
                 * seconds. This value is then converted to nanoseconds and checked
                 * to see if its been long enough. If it has been long enough then
                 * update the screen else do nothing.
                 * @param now Current time in nanoseconds
                 */
                @Override
                public void handle(long now) {
                    if (now - lastUpdate >= delaySlider.getValue() * 1_000_000_000) {
                        // Make sure there is a number of points in the text field
                        if (!numPointsTF.getText().equals("")) {
                            run(false);
                            lastUpdate = now;
                        }
                    }
                }
            }
            MyAnimationTimer timer = new MyAnimationTimer();


            runButton.setOnAction(event -> {
                timer.start();
            });
            pauseButton.setOnAction(event -> {
                timer.stop();
            });

            jumpToButton.setOnAction(event -> {
                timer.stop();
                visualization.setTimesTableNum(Double.parseDouble(timesTableNumTF.getText()));
                timer.run(true);
            });
        }

        public static void main(String[] args) {
            Application.launch(args);
        }
    }