package be.ecam.trafficsim.ui;

import be.ecam.trafficsim.Vector2;
import be.ecam.trafficsim.Vehicle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;

public class VehicleDrawable {
    private final Image image;
    private final AffineTransform trans = new AffineTransform();

    private final Vehicle vehicle;

    public VehicleDrawable(InputStream src, Vehicle vehicle) throws IOException {
        this.image = ImageIO.read(src);
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
        Vector2 vehiclePosition = vehicle.getVehiclePosition();
        trans.setToTranslation(vehiclePosition.x, vehiclePosition.y);
        trans.rotate(angle);
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
