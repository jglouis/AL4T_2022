package be.ecam.solid.ocp.better;

import java.util.List;

public class GeometryUtils {
    /**
     * Compute the total area of all given shapes.
     *
     * @param shapes a {@link List<Area>}.
     * @return the computed sum of all shapes area
     */
    public static double computeArea(List<Area> shapes) {
        double totalArea = 0d;
        for (Area shape : shapes) {
            totalArea += shape.getArea();
        }
        return totalArea;
    }
}
