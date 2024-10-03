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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image car1;
	private Image mTerrain;
	private Timer tm = new Timer(1, this);
	private int x = 0, velX = 2;
	private float mAngle = 0;
	
	private Random random = new Random();
	//Arrays of vehicles in each direction
	private ArrayList<Vehicle> vehiclesRight;
	private ArrayList<Vehicle> vehiclesDown;
	private ArrayList<Vehicle> vehiclesLeft;
	private ArrayList<Vehicle> vehiclesUp;
	
	private String[] carImages = {"/car1.png", "/car2.png", "/car3.png", "/car4.png",
			"/ambulance.png", "/police.png", "/truck1.png", "/truck2.png"};
	
	private ArrayList<TrafficLight> trafficLights;
	private int carSpawnTimer = 0;					//timer regulating the rate new cars are created
	
	Vehicle v1, v2, v3, v4;
	float move=0;
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		
		g2D.drawImage(mTerrain,0,0,this);
		g2D.drawImage(v1.getImage(), v1.getTrans(), this);
		//g2D.drawImage(v2.getImage(), v2.getVehiclePosition().x, v2.getVehiclePosition().y, this);
		
		//displays all cars going in the right direction
		for(int i=0; i<vehiclesRight.size(); i++){
			Vehicle v = vehiclesRight.get(i);
			if(v.isInView())
				g2D.drawImage(v.getImage(), v.getTrans(), this);
			else{
				vehiclesRight.remove(v);
				//System.out.println("Removed vehicle");
			}
		}
		
		//displays all cars going in the left direction
		for(int i=0; i<vehiclesLeft.size(); i++){
			Vehicle v = vehiclesLeft.get(i);
			if(v.isInView())
				g2D.drawImage(v.getImage(), v.getTrans(), this);
			else{
				vehiclesLeft.remove(v);
				//System.out.println("Removed vehicle");
			}
		}
		
		//displays all cars going South
		for(int i=0; i<vehiclesDown.size(); i++){
			Vehicle v = vehiclesDown.get(i);
			if(v.isInView())
				g2D.drawImage(v.getImage(), v.getTrans(), this);
			else{
				vehiclesDown.remove(v);
				//System.out.println("Removed vehicle");
			}
		}
		
		//displays all cars going North
		for(int i=0; i<vehiclesUp.size(); i++){
			Vehicle v = vehiclesUp.get(i);
			if(v.isInView())
				g2D.drawImage(v.getImage(), v.getTrans(), this);
			else{
				vehiclesUp.remove(v);
				//System.out.println("Removed vehicle");
			}
		}
		AffineTransform identity = g2D.getTransform();
		
		identity.setToTranslation(300, 200+move);
		identity.rotate(Math.toRadians(mAngle), car1.getWidth(this), car1.getHeight(this));
		
		g2D.drawImage(car1, identity, this);
		
		
		//Draw trafficLights
		for(TrafficLight t: trafficLights){
			//Draw the lights
			Color colors[] = t.getCurrentLightColor();
			if(t.getOrientation() == 0){
				g2D.setColor(colors[1]);
				g2D.fillRect(t.getForward_pos().x, t.getForward_pos().y, 21, 29);
				g2D.setColor(colors[0]);
				g2D.fillRect(t.getLeft_light_pos().x, t.getLeft_light_pos().y, 20, 29);
				g2D.setColor(colors[2]);
				g2D.fillRect(t.getRight_light_pos().x, t.getRight_light_pos().y, 20, 29);	
			}else{
				g2D.setColor(colors[1]);
				g2D.fillRect(t.getForward_pos().x, t.getForward_pos().y, 29, 22);
				g2D.setColor(colors[0]);
				g2D.fillRect(t.getLeft_light_pos().x, t.getLeft_light_pos().y, 29, 22);
				g2D.setColor(colors[2]);
				g2D.fillRect(t.getRight_light_pos().x, t.getRight_light_pos().y, 29, 22);
			}
			g2D.drawImage(t.getLayoutImg(), t.getTrans(), this);
				
		}
		
		
		if(!tm.isRunning())
			tm.start();
	}
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		carSpawnTimer++;
		
		if(x < 0 || x > 550)
			velX = -velX;
		
		x = x + velX;
		
		if(mAngle < 450)
			move +=0.2;
		else
			move += 2;
		steerTowards(450,120);
		
		
		
		//This section is where cars are created, every 800s
				if(carSpawnTimer%500 == 0){
				//create new car objects over here
					
					int carImageId = 0;
					int vAheadID = 1000;
					for(int i = 0;i <20; i++){
						if(vehiclesRight.size() < 30){
							int line = (vehiclesRight.size())/3;
							//int laneID = vehiclesLeft.size()%3;
							
							if(line > 0){
								/*switch(laneID){
								case 0:
									vAheadID = 3*line - 3;
									break;
								case 1:
									vAheadID = 3*line - 2;
									break;
								case 2:
									vAheadID = 3*line - 1;
									break;
								}*/
								
								vAheadID = vehiclesRight.size() - 3;
							}
							carImageId = random.nextInt(carImages.length);
							if(carImageId < 0){
								carImageId = 0;
							}
							//int spd = 7- random.nextInt(2);
							if(vehiclesRight.size() > 0 && vAheadID < vehiclesRight.size())
								vehiclesRight.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), 6, VehicleState.MOVE_X, VehicleDirection.RIGHT, trafficLights.get(1), this, vehiclesRight.get(vAheadID), vehiclesRight.size()));
							else
								vehiclesRight.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), 6, VehicleState.MOVE_X, VehicleDirection.RIGHT, trafficLights.get(1), this, null, vehiclesRight.size()));
							
						}
						
						if(vehiclesDown.size() < 20){
							int line = (vehiclesDown.size())/3;
							int laneID = vehiclesDown.size()%3;
							
							if(line > 0){
								switch(laneID){
								case 0:
									vAheadID = 3*line - 3;
									break;
								case 1:
									vAheadID = 3*line - 2;
									break;
								case 2:
									vAheadID = 3*line - 1;
									break;
								}
								
								//vAheadID = vehiclesDown.size() - 3;
							}
							
							carImageId = random.nextInt(carImages.length);
							if(carImageId < 0){
								carImageId = 0;
							}
							int spd = 7- random.nextInt(2);
							//System.out.println("line: " +line + " vehicle: "+vehiclesDown.size() + "vAheadId: " +vAheadID);
							if(vehiclesDown.size() > 0 && vAheadID < vehiclesDown.size())
								vehiclesDown.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), spd, VehicleState.MOVE_Y, VehicleDirection.DOWN, trafficLights.get(0), this, vehiclesDown.get(vAheadID), vehiclesDown.size()));
							else
								vehiclesDown.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), spd, VehicleState.MOVE_Y, VehicleDirection.DOWN, trafficLights.get(0), this, null, vehiclesDown.size()));
						}
						
						if(vehiclesLeft.size() < 30){
							int line = (vehiclesLeft.size())/3;
							int laneID = vehiclesLeft.size()%3;
							
							if(line > 0){
								switch(laneID){
								case 0:
									vAheadID = 3*line - 3;
									break;
								case 1:
									vAheadID = 3*line - 2;
									break;
								case 2:
									vAheadID = 3*line - 1;
									break;
								}
								
								//vAheadID = vehiclesLeft.size() - 3;
							}
							carImageId = random.nextInt(carImages.length);
							if(carImageId < 0){
								carImageId = 0;
							}
							int spd = 7- random.nextInt(2);
							//System.out.println("vAheadID: " +vAheadID + "carImageId: "+carImageId + "vehicles: " + vehiclesLeft.size());
							if(vehiclesLeft.size() > 0 && vAheadID < vehiclesLeft.size())
								vehiclesLeft.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), spd, VehicleState.MOVE_X, VehicleDirection.LEFT, trafficLights.get(3),this, vehiclesLeft.get(vAheadID), vehiclesLeft.size()));
							else
								vehiclesLeft.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), spd, VehicleState.MOVE_X, VehicleDirection.LEFT, trafficLights.get(3),this, null, vehiclesLeft.size()));
						}
						
						if(vehiclesUp.size() < 30){
							//System.out.println("up size: " +vehiclesUp.size());
							int line = (vehiclesUp.size())/3;
							//int laneID = vehiclesUp.size()%3;
							
							if(line > 0){
								/*switch(laneID){
								case 0:
									vAheadID = 3*line - 3;
									break;
								case 1:
									vAheadID = 3*line - 2;
									break;
								case 2:
									vAheadID = 3*line - 1;
									break;
								}*/
								
								vAheadID = vehiclesUp.size() - 3;
							}
							carImageId = random.nextInt(carImages.length);
							if(carImageId < 0){
								carImageId = 0;
							}
							int spd = 7- random.nextInt(2);
							if(vehiclesUp.size() > 0 && vAheadID < vehiclesUp.size())
								vehiclesUp.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), spd, VehicleState.MOVE_Y, VehicleDirection.UP, trafficLights.get(2),this, vehiclesUp.get(vAheadID), vehiclesUp.size()));
							else
								vehiclesUp.add(new Vehicle(getClass().getResourceAsStream(carImages[carImageId]), spd, VehicleState.MOVE_Y, VehicleDirection.UP, trafficLights.get(2),this, null, vehiclesUp.size()));
						}
					}
			
			carSpawnTimer = 0;
		}
		
		repaint();
	}
	
	//simple function to test our steering
	/** 
	 * @param angle the final angle you want the car to positioned at relative to the normal
	 * @param time the time in seconds you want the car to take to position itself at "angle"*/
	private void steerTowards(float angle, float t){
		//first we calculate the angular velocity required to get the vehicle to angle in time t
		float angularVel = angle/t;
		
		if(Math.abs(mAngle) < Math.abs(angle))	
			mAngle += angularVel;
		
	}
	
	public Simulation(){

		trafficLights = new ArrayList<TrafficLight>();
		
		TrafficLight t1 = new TrafficLight(getClass().getResourceAsStream("/trafficLight.png"), 349, 147, 180, 0, 1, this);
		t1.setLeft_light_pos(new Vector2(349,147));
		t1.setForward_pos(new Vector2(349+23, 147));
		t1.setRight_light_pos(new Vector2(349+47, 147));
		trafficLights.add(t1);
		
		TrafficLight t2 = new TrafficLight(getClass().getResourceAsStream("/trafficLight.png"), 302, 530, 90, 1, 2, this);
		t2.setLeft_light_pos(new Vector2(322,511));
		t2.setForward_pos(new Vector2(322,533));
		t2.setRight_light_pos(new Vector2(322, 558));
		trafficLights.add(t2);
		
		TrafficLight t3 = new TrafficLight(getClass().getResourceAsStream("/trafficLight.png"), 1077, 585, 0, 0, 3, this);
		t3.setLeft_light_pos(new Vector2(1079, 585));
		t3.setForward_pos(new Vector2(1077+25, 585));
		t3.setRight_light_pos(new Vector2(1077+48, 585));
		trafficLights.add(t3);
		
		TrafficLight t4 = new TrafficLight(getClass().getResourceAsStream("/trafficLight.png"), 1125, 195, -90, 1, 4, this);
		t4.setLeft_light_pos(new Vector2(1145, 175));
		t4.setForward_pos(new Vector2(1145, 175+25));
		t4.setRight_light_pos(new Vector2(1145, 175+47));
		trafficLights.add(t4);
		
		v1 = new Vehicle(getClass().getResourceAsStream("/car1.jpg"), 6, VehicleState.MOVE_X, VehicleDirection.RIGHT, trafficLights.get(1),this, null, 0);
		v2 = new Vehicle(getClass().getResourceAsStream("/car1.jpg"), 5, VehicleState.MOVE_Y, VehicleDirection.DOWN, trafficLights.get(0),this, null, 0);
		v3 = new Vehicle(getClass().getResourceAsStream("/car1.jpg"), 6, VehicleState.MOVE_X, VehicleDirection.LEFT, trafficLights.get(3),this, null, 0);
		v4 = new Vehicle(getClass().getResourceAsStream("/car1.jpg"), 5, VehicleState.MOVE_Y, VehicleDirection.UP, trafficLights.get(2),this, null, 0);
		
		vehiclesRight = new ArrayList<Vehicle>();
		vehiclesLeft = new ArrayList<Vehicle>();
		vehiclesDown = new ArrayList<Vehicle>();
		vehiclesUp = new ArrayList<Vehicle>();
		vehiclesRight.add(v1); vehiclesDown.add(v2); vehiclesLeft.add(v3); vehiclesUp.add(v4);
		
		
		try {
			car1 = ImageIO.read(getClass().getResourceAsStream("/car1.jpg"));
			mTerrain = ImageIO.read(getClass().getResourceAsStream("/road1.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String args[]){
	
		JFrame jF = new JFrame("Traffic Simulation");
		Simulation sim = new Simulation();
		jF.setContentPane(sim);
		jF.setSize(1366, 750);
		jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jF.setVisible(true);
		final SoundManager manager = new SoundManager();
		jF.addWindowListener(new WindowListener() {
			
			public void windowOpened(WindowEvent e) {
				
				
			}
			
			public void windowIconified(WindowEvent e) {
			
				
			}
			
			public void windowDeiconified(WindowEvent e) {
				
				
			}
			
			public void windowDeactivated(WindowEvent e) {
			
				
			}
			
			public void windowClosing(WindowEvent e) {
				
				
			}
			
			public void windowClosed(WindowEvent e) {
			
				manager.clip.close();
				
			}
			
			public void windowActivated(WindowEvent e) {
			
				
			}
		});
		
		
		manager.clip.close();
		
		
	
		
	}

}
