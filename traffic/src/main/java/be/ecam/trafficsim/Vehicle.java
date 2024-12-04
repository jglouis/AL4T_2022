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

public class Vehicle {

    private TrafficLight trafficLight;
    private float velocity, acceleration = 0f, vehVelo; // Max speed of vehicle
    private float mCurAngle; // Current angle of vehicle relative to normal

    public enum VehicleDirection {
        LEFT, RIGHT, UP, DOWN, DOWN_LEFT, RIGHT_UP,
        RIGHT_DOWN, LEFT_UP, UP_LEFT, UP_RIGHT
    }

    public enum VehicleState { BRAKE, MOVE_X, MOVE_Y, TURNING }

    public enum Command { GO_STRAIGHT, TURN_LEFT, TURN_RIGHT }

    private Vector2 vehiclePosition; // The position of the vehicle
    private VehicleState vState;
    private final VehicleState vPrev; // Stores the previous vehicle state
    private VehicleDirection vDirection; // Stores the current vehicle direction

    private boolean passedTrafficLight;
    private Vehicle vehicleAhead; // Vehicle in front for cruise control
    private Command command;
    private boolean turn = false;

    // Traffic lights position constants
    private final int movingRightPos = 301;
    private final int movingLeftPos = 1150;
    private final int movingDownPos = 121;
    private final int movingUpPos = 652;

    /**
     * Constructor for the Vehicle class
     *
     * @param vel      Sets the vehicle max speed
     * @param vehState Initial state of the vehicle
     * @param vDir     Initial direction of the vehicle
     * @param tl       Reference to the traffic light
     * @param vAhead   Vehicle ahead for cruise control
     */
    public Vehicle(int vel, VehicleState vehState, VehicleDirection vDir, TrafficLight tl, Vehicle vAhead) {
        this.vState = vehState;
        this.vehicleAhead = vAhead;
        this.vPrev = vehState;
        this.vDirection = vDir;
        this.trafficLight = tl;
        this.command = Command.GO_STRAIGHT;
        this.velocity = vel;
        this.vehVelo = velocity;
        this.mCurAngle = 0;

        initializeVehiclePosition();
    }

    private void initializeVehiclePosition() {
        Vector2 RIGHT_LEFT_POS = new Vector2(1500, 265);
        Vector2 LEFT_RIGHT_POS = new Vector2(-300, 463);
        Vector2 DOWN_UP_POS = new Vector2(920, 840);
        Vector2 UP_DOWN_POS = new Vector2(520, -100);

        switch (vDirection) {
            case LEFT:
                vehiclePosition = RIGHT_LEFT_POS;
                break;
            case RIGHT:
                vehiclePosition = LEFT_RIGHT_POS;
                break;
            case UP:
                vehiclePosition = DOWN_UP_POS;
                break;
            case DOWN:
                vehiclePosition = UP_DOWN_POS;
                break;
            default:
                break;
        }
    }

    public float getSpeed() {
        return velocity;
    }

    public Vector2 getVehiclePosition() {
        return vehiclePosition;
    }

    public boolean isInView() {
        if (vDirection == VehicleDirection.LEFT && vehiclePosition.x < -100) {
            return false;
        }
        if (vDirection == VehicleDirection.RIGHT && vehiclePosition.x > 1500) {
            return false;
        }
        if (vDirection == VehicleDirection.UP && vehiclePosition.y < -100) {
            return false;
        }
        return vDirection != VehicleDirection.DOWN || vehiclePosition.y <= 850;
    }

    private void accelerate(int currentPos, int finalPos) {
        float dist = finalPos - currentPos;
        float t = dist / velocity;

        if (Math.abs(t) > 0)
            acceleration = (0 - velocity) / t;
        else
            acceleration = 0;
    }

    public void updateState() {
        if (vehicleAhead != null) {
            // Check distance to vehicle ahead and adjust state
            float distanceToAhead = vehicleAhead.getVehiclePosition().x - this.vehiclePosition.x;
            if (distanceToAhead < 50) { // Threshold for braking
                vState = VehicleState.BRAKE;
            } else {
                vState = VehicleState.MOVE_X;
            }
        } else if (!passedTrafficLight && trafficLight != null) {
            // Check if the vehicle is approaching a red light
            boolean stopForLight = (vDirection == VehicleDirection.RIGHT && vehiclePosition.x >= movingRightPos && !trafficLight.forwardGo)
                    || (vDirection == VehicleDirection.LEFT && vehiclePosition.x <= movingLeftPos && !trafficLight.leftGo)
                    || (vDirection == VehicleDirection.UP && vehiclePosition.y <= movingUpPos && !trafficLight.rightGo)
                    || (vDirection == VehicleDirection.DOWN && vehiclePosition.y >= movingDownPos && !trafficLight.forwardGo);

            if (stopForLight) {
                vState = VehicleState.BRAKE;
            } else {
                vState = VehicleState.MOVE_X;
            }
        }
    }


    public void move() {
        switch (vDirection) {
            case RIGHT:
                vehiclePosition.x += velocity;
                break;
            case LEFT:
                vehiclePosition.x -= velocity;
                break;
            case UP:
                vehiclePosition.y -= velocity;
                break;
            case DOWN:
                vehiclePosition.y += velocity;
                break;
            default:
                break;
        }
    }


    public void brake() {
        if (velocity > 0) {
            velocity -= 0.1f; // Gradual deceleration
            if (velocity < 0) {
                velocity = 0; // Ensure velocity doesn't go negative
            }
        }
    }


    public void turning() {
        if (turn) {
            // Update angle and position for turning
            mCurAngle += 5; // Turn in increments of 5 degrees
            if (mCurAngle >= 90) {
                // Reset after completing turn
                mCurAngle = 0;
                turn = false;
                // Update direction after the turn
                if (command == Command.TURN_LEFT) {
                    switch (vDirection) {
                        case UP -> vDirection = VehicleDirection.LEFT;
                        case LEFT -> vDirection = VehicleDirection.DOWN;
                        case DOWN -> vDirection = VehicleDirection.RIGHT;
                        case RIGHT -> vDirection = VehicleDirection.UP;
                    }
                } else if (command == Command.TURN_RIGHT) {
                    switch (vDirection) {
                        case UP -> vDirection = VehicleDirection.RIGHT;
                        case LEFT -> vDirection = VehicleDirection.UP;
                        case DOWN -> vDirection = VehicleDirection.LEFT;
                        case RIGHT -> vDirection = VehicleDirection.DOWN;
                    }
                }
            }
        }
    }

}
