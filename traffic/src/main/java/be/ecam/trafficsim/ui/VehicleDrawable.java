package be.ecam.trafficsim.ui;

import be.ecam.trafficsim.Vector2;
import be.ecam.trafficsim.Vehicle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;

public class VehicleDrawable {
    private final Image image;
    private final ImageObserver observer;
    private final AffineTransform trans = new AffineTransform();

    private final Vehicle vehicle;

    public VehicleDrawable(InputStream src, Vehicle vehicle, ImageObserver observer) throws IOException {
        this.image = ImageIO.read(src);
        this.observer = observer;
        this.vehicle = vehicle;
        update();
    }

    public void actionPerformed() {
        // Update the Vehicle state
        this.vehicle.actionPerformed();
        // Update image
        update();
    }

    private void update() {
        double angle = Math.toRadians(vehicle.getCurrentAngle());
        double anchorX = (double) image.getWidth(observer) / 2;
        double anchorY = (double) image.getHeight(observer) /2;
        Vector2 vehiclePosition = vehicle.getVehiclePosition();
        trans.setToTranslation(vehiclePosition.x, vehiclePosition.y);
        trans.rotate(angle, anchorX, anchorY);
    }

    public boolean isInView() {
        return vehicle.isInView();
    }

    public Image getImage() {
        return image;
    }

    public AffineTransform getTrans() {
        return trans;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
