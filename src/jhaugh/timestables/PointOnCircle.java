package jhaugh.timestables;

import static java.lang.Math.cos;
import static java.lang.Math.toRadians;
import static java.lang.StrictMath.sin;

import static jhaugh.timestables.Main.OFFSET_W;
import static jhaugh.timestables.Main.OFFSET_H;

    /**
     * This class represents a point around the circle
     */
    public class PointOnCircle {
        private int id;
        private double x, y;

        public PointOnCircle(int id, double x, double y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public PointOnCircle(double x, double y, int i) {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private double getWidth(){
            return x;
        }
        /**
         * @return the x value of the point taking into account the offset
         */
        public double getX() {
            // takes width offset and divides by half and returns the value x
            return OFFSET_W+x;
        }

        public void setX(double x) {
            this.x = x;
        }

        private double getHeight(){
            return y;
        }
        /**
         * @return the y value of the point taking into account the offset
         */
        public double getY() {
            return OFFSET_H+y;
        }

        public void setY(double y) {
            this.y = y;
        }

        /**
         * Generates an array of points around a circle
         * with the given radius and number of points.
         * @param radius Radius of the circle
         * @param num Number of points around the circle
         * @return Array of points around the circle
         */
        public static jhaugh.timestables.PointOnCircle[] generatePoints(double radius, double num) {
            jhaugh.timestables.PointOnCircle[] points = new jhaugh.timestables.PointOnCircle[(int)num];
            int i = 0;
            // Degrees of separation between points on the circle
            double pointSeparation = 360.0 / num;
            for(double angle = 180; angle < 540; angle += pointSeparation) {
                double x = cos(toRadians(angle)) * radius;
                double y = sin(toRadians(angle)) * radius;
                //creates a new array to store the points in points[i]
                points[i] = new jhaugh.timestables.PointOnCircle(i,x,y);

                i++;
            }
            return points;
        }
    }


