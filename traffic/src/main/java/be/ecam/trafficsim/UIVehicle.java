package be.ecam.trafficsim;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;

public class UIVehicle implements ActionListener {

    private final Vehicle vehicle;

    private final ImageObserver imgObserve;
    private Image mImage;

    private AffineTransform trans;                                        //The transformation of vehicle

    /**
     * Constructor for class
     *
     * @param src Loads the vehicle image
     * */

    public UIVehicle(Vehicle veh, InputStream src, ImageObserver imObs){

        vehicle = veh;
        imgObserve = imObs;

        try {

            mImage = ImageIO.read(src);
            if (mImage == null) {
                throw new IOException("Image is null");
            }

            trans = new AffineTransform();
            trans.setToTranslation(vehicle.getVehiclePosition().x, vehicle.getVehiclePosition().y);

            if (vehicle.vState == Vehicle.VehicleState.MOVE_Y) {
                trans.setToTranslation(vehicle.getVehiclePosition().x, vehicle.getVehiclePosition().y);
                trans.rotate(Math.toRadians(vehicle.getMCurAngle()),
                        (double) mImage.getWidth(imgObserve) / 2,
                        (double) mImage.getHeight(imgObserve) / 2);
            }

            Timer mTimer = new Timer(10, this);
            mTimer.start();

        } catch (IOException e) {
            System.err.println("Can't find image");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (vehicle.uiVehicleAhead != null && !vehicle.uiVehicleAhead.getVehicle().isInView()) {
            vehicle.uiVehicleAhead = null;
        }

        if (vehicle.getTrafficLight() != null && (vehicle.getTrafficLight().forwardGo || vehicle.getPassedTrafficLight()) && !vehicle.turn) {
            vehicle.vState = vehicle.vPrev;
        } else if (vehicle.getTrafficLight() != null && !vehicle.getTrafficLight().forwardGo) {
            vehicle.vState = Vehicle.VehicleState.BRAKE;
        }

        switch (vehicle.vState) {
            case BRAKE:
                brake();
                break;
            case TURNING:
                turning();
                break;
            case MOVE_X:
            case MOVE_Y:
                move();
                break;
        }
    }

    private void steerTowards(float angle, float time) {
        vehicle.steerTowards(angle, time);

        float mCurAngle = vehicle.getMCurAngle();

        trans.rotate(Math.toRadians(mCurAngle), mImage.getWidth(imgObserve), mImage.getHeight(imgObserve));
    }

    /**
     * Getter for vehicle image
     *
     * @return Image The vehicle image object
     */

    public Image getImage() { return mImage; }

    public AffineTransform getTrans() { return trans; }

    public Vehicle getVehicle() { return vehicle; }

    public void move() {
        vehicle.move();
        Vector2 vehiclePosition = vehicle.getVehiclePosition();
        float mCurAngle = vehicle.getMCurAngle();

        System.out.println("Moving vehicle to position: " + vehiclePosition);

        this.trans.setToTranslation(vehiclePosition.x, vehiclePosition.y);
        this.trans.rotate(Math.toRadians(mCurAngle), (double) mImage.getWidth(imgObserve) / 2, (double) mImage.getHeight(imgObserve) / 2);
    }

    public void turning() {
        vehicle.turning();

        Vehicle.Command command = vehicle.getCommand();
        Vehicle.VehicleDirection vDirection = vehicle.getVDirection();
        Vector2 vehiclePosition = vehicle.getVehiclePosition();
        float mCurAngle = vehicle.getMCurAngle();

        switch (command) {
            case TURN_LEFT -> {
                switch (vDirection) {
                    case DOWN:
                        this.trans.setToTranslation(vehiclePosition.x, vehiclePosition.y);
                        this.trans.rotate(Math.toRadians(mCurAngle), (double) mImage.getWidth(imgObserve) / 2, (double) mImage.getHeight(imgObserve) / 2);

                    case LEFT_UP:
                        this.trans.setToTranslation(vehiclePosition.x, vehiclePosition.y);
                        this.trans.rotate(Math.toRadians(mCurAngle), (double) mImage.getWidth(imgObserve) / 2, (double) mImage.getHeight(imgObserve) / 2);

                }
            }
            case TURN_RIGHT -> {
                switch (vDirection) {
                    case UP_RIGHT:
                        this.trans.setToTranslation(vehiclePosition.x, vehiclePosition.y);
                        this.trans.rotate(Math.toRadians(mCurAngle), (double) mImage.getWidth(imgObserve) / 2, (double) mImage.getHeight(imgObserve) / 2);

                    case RIGHT_DOWN:
                        this.trans.setToTranslation(vehiclePosition.x, vehiclePosition.y);
                        this.trans.rotate(Math.toRadians(mCurAngle), (double) mImage.getWidth(imgObserve) / 2, (double) mImage.getHeight(imgObserve) / 2);

                }
            }
        }
    }

    public void brake() {
        vehicle.brake();

        Vector2 vehiclePosition = vehicle.getVehiclePosition();
        float mCurAngle = vehicle.getMCurAngle();

        this.trans.setToTranslation(vehiclePosition.x, vehiclePosition.y);
        this.trans.rotate(Math.toRadians(mCurAngle), (double) mImage.getWidth(imgObserve) / 2, (double) mImage.getHeight(imgObserve) / 2);
    }

}
