package be.ecam.trafficsim;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;

public class UIVehicle {

    private final ImageObserver imgObserve;
    private final Vehicle vehicle;
    private Image mImage; // Image of the vehicle
    private AffineTransform trans; // Transformation of the vehicle

    /**
     * Constructor for the UIVehicle class
     *
     * @param src      InputStream for the vehicle image
     * @param imObs    ImageObserver for rendering
     * @param vehicle  Logical vehicle instance
     */
    public UIVehicle(InputStream src, ImageObserver imObs, Vehicle vehicle) {
        this.imgObserve = imObs;
        this.vehicle = vehicle;

        try {
            this.mImage = ImageIO.read(src);
        } catch (IOException e) {
            System.err.println("Can't find image");
        }

        this.trans = new AffineTransform();
        updateTransform();
    }

    /**
     * Updates the transformation based on the vehicle's state
     */
    public void updateTransform() {
        Vector2 position = vehicle.getVehiclePosition();
        trans.setToTranslation(position.x, position.y);
        trans.rotate(Math.toRadians(vehicle.getSpeed()),
                mImage.getWidth(imgObserve) / 2.0,
                mImage.getHeight(imgObserve) / 2.0);
    }

    /**
     * Renders the vehicle
     *
     * @param g Graphics context
     */
    public void render(Graphics2D g) {
        updateTransform();
        g.drawImage(mImage, trans, imgObserve);
    }

    /**
     * Getter for the vehicle image
     *
     * @return Image
     */
    public Image getImage() {
        return mImage;
    }

    /**
     * Getter for the transformation
     *
     * @return AffineTransform
     */
    public AffineTransform getTrans() {
        return trans;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }
}
