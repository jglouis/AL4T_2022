package trafficsim;

import trafficsim.Vehicle.VehicleDirection;
import trafficsim.Vehicle.VehicleState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Simulation extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Image car1;
	private Image mTerrain;
	private Timer tm = new Timer(16, this); // 60 fps (16ms)
	private int x = 0, velX = 2;
	private float mAngle = 0;
	private Random random = new Random();

	// Arrays of vehicles in each direction
	private ArrayList<Vehicle> vehiclesRight;
	private ArrayList<Vehicle> vehiclesDown;
	private ArrayList<Vehicle> vehiclesLeft;
	private ArrayList<Vehicle> vehiclesUp;

	private String[] carImages = {"/car1.png", "/car2.png", "/car3.png", "/car4.png", "/ambulance.png", "/police.png", "/truck1.png", "/truck2.png"};
	private ArrayList<TrafficLight> trafficLights;
	private int carSpawnTimer = 0; // timer regulating the rate new cars are created

	Vehicle v1, v2, v3, v4;
	float move = 0;

	// Affichage véhicules pr éviter répétitions
	private void drawVehicles(Graphics2D g2D, ArrayList<Vehicle> vehicles) {
		for (Vehicle v : vehicles) {
			if (v.isInView()) {
				g2D.drawImage(v.getImage(), v.getTrans(), this);
			} else {
				vehicles.remove(v); // suppr si hors écran
			}
		}
	}

	// Affichage des feux
	private void drawTrafficLights(Graphics2D g2D) {
		for (TrafficLight t : trafficLights) {
			Color[] colors = t.getCurrentLightColor();
			if (t.getOrientation() == 0) {
				g2D.setColor(colors[1]);
				g2D.fillRect(t.getForward_pos().x, t.getForward_pos().y, 21, 29);
				g2D.setColor(colors[0]);
				g2D.fillRect(t.getLeft_light_pos().x, t.getLeft_light_pos().y, 20, 29);
				g2D.setColor(colors[2]);
				g2D.fillRect(t.getRight_light_pos().x, t.getRight_light_pos().y, 20, 29);
			} else {
				// Orientation ≠ 0
				g2D.setColor(colors[1]);
				g2D.fillRect(t.getForward_pos().x, t.getForward_pos().y, 29, 22);
				g2D.setColor(colors[0]);
				g2D.fillRect(t.getLeft_light_pos().x, t.getLeft_light_pos().y, 29, 22);
				g2D.setColor(colors[2]);
				g2D.fillRect(t.getRight_light_pos().x, t.getRight_light_pos().y, 29, 22);
			}
			g2D.drawImage(t.getLayoutImg(), t.getTrans(), this);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;

		// Terrain + v1
		g2D.drawImage(mTerrain, 0, 0, this);
		g2D.drawImage(v1.getImage(), v1.getTrans(), this);

		// Affichage des véhicules
		drawVehicles(g2D, vehiclesRight);
		drawVehicles(g2D, vehiclesLeft);
		drawVehicles(g2D, vehiclesDown);
		drawVehicles(g2D, vehiclesUp);

		// Affichage des feux
		drawTrafficLights(g2D);

		// Démarre timer si pas déjà en cours
		if (!tm.isRunning()) {
			tm.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		carSpawnTimer++; // incrémente timer pr spawn

		// Mvt voitures droite/gauche
		if (x < 0 || x > 550) velX = -velX;
		x = x + velX;

		// Mvt et angle
		if (mAngle < 450) move += 0.2;
		else move += 2;
		steerTowards(450, 120); // dirige voiture vers angle cible

		// Spawn voitures toutes les 500ms
		if (carSpawnTimer % 500 == 0) {
			spawnVehicles();
			carSpawnTimer = 0;
		}

		repaint(); // Redessine l'écran
	}

	// Fonction pour diriger vers un angle donné
	/**
	 * @param angle the final angle you want the car to positioned at relative to the normal
	 * @param time the time in seconds you want the car to take to position itself at "angle" */
	private void steerTowards(float angle, float t) {
		float angularVel = angle / t; // vitesse angulaire
		if (Math.abs(mAngle) < Math.abs(angle)) mAngle += angularVel;
	}

	// Crée un ensemble de véhicules pour toutes les directions
	private void spawnVehicles() {
		// Pour les véhicules allant à droite
		if (vehiclesRight.size() < 30) {
			int carImageId = random.nextInt(carImages.length);
			if (vehiclesRight.size() > 0) {
				vehiclesRight.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), 6, VehicleState.MOVE_X, VehicleDirection.RIGHT, trafficLights.get(1), this, vehiclesRight.get(vehiclesRight.size() - 1), vehiclesRight.size()));
			} else {
				vehiclesRight.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), 6, VehicleState.MOVE_X, VehicleDirection.RIGHT, trafficLights.get(1), this, null, vehiclesRight.size()));
			}
		}

		// Pour les véhicules allant vers le bas
		if (vehiclesDown.size() < 20) {
			int carImageId = random.nextInt(carImages.length);
			if (vehiclesDown.size() > 0) {
				vehiclesDown.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), 5, VehicleState.MOVE_Y, VehicleDirection.DOWN, trafficLights.get(0), this, vehiclesDown.get(vehiclesDown.size() - 1), vehiclesDown.size()));
			} else {
				vehiclesDown.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), 5, VehicleState.MOVE_Y, VehicleDirection.DOWN, trafficLights.get(0), this, null, vehiclesDown.size()));
			}
		}

		// Pour les véhicules allant à gauche
		if (vehiclesLeft.size() < 30) {
			int carImageId = random.nextInt(carImages.length);
			if (vehiclesLeft.size() > 0) {
				vehiclesLeft.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), 6, VehicleState.MOVE_X, VehicleDirection.LEFT, trafficLights.get(3), this, vehiclesLeft.get(vehiclesLeft.size() - 1), vehiclesLeft.size()));
			} else {
				vehiclesLeft.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), 6, VehicleState.MOVE_X, VehicleDirection.LEFT, trafficLights.get(3), this, null, vehiclesLeft.size()));
			}
		}

		// Pour les véhicules allant vers le haut
		if (vehiclesUp.size() < 30) {
			int carImageId = random.nextInt(carImages.length);
			if (vehiclesUp.size() > 0) {
				vehiclesUp.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), 5, VehicleState.MOVE_Y, VehicleDirection.UP, trafficLights.get(2), this, vehiclesUp.get(vehiclesUp.size() - 1), vehiclesUp.size()));
			} else {
				vehiclesUp.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), 5, VehicleState.MOVE_Y, VehicleDirection.UP, trafficLights.get(2), this, null, vehiclesUp.size()));
			}
		}
	}


	public Simulation() {
		// Initialisation des feux de signalisation
		trafficLights = new ArrayList<>();
		TrafficLight t1 = new TrafficLight(getClass().getResourceAsStream("/trafficLight.png"), 349, 147, 180, 0, 1, this);
		t1.setLeft_light_pos(new Vector2(349, 147));
		t1.setForward_pos(new Vector2(349 + 23, 147));
		t1.setRight_light_pos(new Vector2(349 + 47, 147));
		trafficLights.add(t1);

		TrafficLight t2 = new TrafficLight(getClass().getResourceAsStream("/trafficLight.png"), 302, 530, 90, 1, 2, this);
		t2.setLeft_light_pos(new Vector2(322, 511));
		t2.setForward_pos(new Vector2(322, 533));
		t2.setRight_light_pos(new Vector2(322, 558));
		trafficLights.add(t2);

		// Initialisation des véhicules
		v1 = new Vehicle(getClass().getResourceAsStream("/car1.jpg"), 6, VehicleState.MOVE_X, VehicleDirection.RIGHT, trafficLights.get(1), this, null, 0);
		v2 = new Vehicle(getClass().getResourceAsStream("/car1.jpg"), 5, VehicleState.MOVE_Y, VehicleDirection.DOWN, trafficLights.get(0), this, null, 0);
		v3 = new Vehicle(getClass().getResourceAsStream("/car1.jpg"), 6, VehicleState.MOVE_X, VehicleDirection.LEFT, trafficLights.get(3), this, null, 0);
		v4 = new Vehicle(getClass().getResourceAsStream("/car1.jpg"), 5, VehicleState.MOVE_Y, VehicleDirection.UP, trafficLights.get(2), this, null, 0);

		// Init des listes avant d'ajouter véhicules
		vehiclesRight = new ArrayList<>();
		vehiclesLeft = new ArrayList<>();
		vehiclesDown = new ArrayList<>();
		vehiclesUp = new ArrayList<>();

		// Ajoute les véhicules initiaux dans les directions correspondantes
		vehiclesRight.add(v1);
		vehiclesDown.add(v2);
		vehiclesLeft.add(v3);
		vehiclesUp.add(v4);

		try {
			car1 = ImageIO.read(getClass().getResourceAsStream("/car1.jpg"));
			mTerrain = ImageIO.read(getClass().getResourceAsStream("/road1.jpg"));
		} catch (IOException e) {
			// handle exceptions proprement
			System.err.println("Erreur chargement image : " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		JFrame jF = new JFrame("Traffic Simulation");
		Simulation sim = new Simulation();
		jF.setContentPane(sim);
		jF.setSize(1366, 750);
		jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jF.setVisible(true);

		final SoundManager manager = new SoundManager();
		jF.addWindowListener(new WindowListener() {
			@Override public void windowOpened(WindowEvent e) {}
			@Override public void windowIconified(WindowEvent e) {}
			@Override public void windowDeiconified(WindowEvent e) {}
			@Override public void windowDeactivated(WindowEvent e) {}
			@Override public void windowClosing(WindowEvent e) {}
			@Override public void windowClosed(WindowEvent e) { manager.clip.close(); }
			@Override public void windowActivated(WindowEvent e) {}
		});
		manager.clip.close(); // stop sound at close
	}
}
