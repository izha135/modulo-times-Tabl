
package jhaugh.timestables;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Scanner;


    /**
     * This class represents all of the state necessary to generate
     * the times table Visualization.
     */
    public class Visualization {
        private double r = 0;
        private boolean increment = true;

        private double timesTableNum;
        private double radius;

        public Visualization(double timesTableNum,
                             double radius) {
            this.timesTableNum = timesTableNum;
            this.radius = radius;
        }

        public double getR() {
            return r;
        }

        public double getRadius() {
            return radius;
        }

        public double getTimesTableNum() {
            return timesTableNum;
        }

        public void setTimesTableNum(double timesTableNum) {
            this.timesTableNum = timesTableNum;
        }

        public void incrementTimesTableNum(double stepNum) {

            timesTableNum+= stepNum;

        }

        /**
         * Increment the "r" instance variable from 0 up to 1
         * then decrement back down to 0 then repeat.
         * The direction is determined by the "increment"
         * instance variable.
         * In order to properly compare doubles you need to
         * use Double.compare(d1, d2).
         */
        private void incrementRed() {
            if (r> 0.9){
                increment = false;
            }
            if(increment){
                r = r+0.1;
            }
            if (r < 0.1){
                increment = true;
            }
            if(!increment){
                r = r-0.1;
            }

        }

        /**
         * Generates all of the lines within the circle.
         * The color of each line is determined by the "r" instance variable
         * and the "g" and "b" arguments are kept constant.
         * @param numPoints Number of points around the circle
         * @return
         */
        public Group generateLines(double numPoints) {
            incrementRed();

            Color color; // fill this in by instantiating a new Color
            color = Color.color(r,0.1,0.5, 0.9);
            // Make a new group to store the lines in
            Group lines = new Group();
            PointOnCircle[] points = PointOnCircle.generatePoints(radius, numPoints);
            /**
             * Loop over each point and draw a line from it to the result
             * of the equation discussed in the video.
             */
            for (PointOnCircle poc : points) {

                double correspondingPointId = (getTimesTableNum() * poc.getId()) % numPoints; // multiplies getTimestablenumber
                // with the id generated when the line 'hits the point in circle' and modulus of that is generated to be stored in correspondingpointid
                PointOnCircle pointTo   = points[(int)correspondingPointId];
                PointOnCircle pointFrom = points[poc.getId()];

                Line line = new Line(pointFrom.getX(),pointFrom.getY(), pointTo.getX(), pointTo.getY());
                // Add the line to the group
                line.setStroke(color);
                lines.getChildren().add(line);
            }
            return lines;
        }
    }


