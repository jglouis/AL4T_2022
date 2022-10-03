package be.ecam.solid.ocp.bad;

import java.util.List;

public class GeometryUtils {
    /**
     * Compute the total area of all given shapes.
     *
     * @param shapes a {@link List} of shapes objects.
     * @return the computed sum of all shapes area
     */
    public static double computeArea(List<Object> shapes) {
        double totalArea = 0d;
        for (Object shape : shapes) {
            if (shape instanceof Rectangle) {
                totalArea += ((Rectangle) shape).getHeight() * ((Rectangle) shape).getWidth();
            } else if (shape instanceof Circle) {
                double radius = ((Circle) shape).getRadius();
                totalArea += radius * radius * Math.PI;
            }
        }
        return totalArea;
    }
}
