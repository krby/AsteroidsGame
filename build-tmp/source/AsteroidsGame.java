import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class AsteroidsGame extends PApplet {

SpaceShip ship1;

Star[] stars;
Asteroid[] asteroids;

public void setup() 
{
	size (400, 400);
	ship1 = new SpaceShip();

	//asteroids
	asteroids = new Asteroid[1];
	for (int i = 0; i < asteroids.length; i++)
	{
		asteroids[i] = new Asteroid();
	}

	//stars
	stars = new Star[100];
	for (int i = 0; i < stars.length; i++)
	{
		stars[i] = new Star();
	}
}

public void draw() 
{
	background(10);
	//ship
	ship1.control(); //controlling keys wasd
	ship1.move();
	ship1.show();

	//asteroids
	for (int i = 0; i < asteroids.length; i++)
	{
		asteroids[i].move();
		asteroids[i].show();
	}

	//stars
	for (int i = 0; i < stars.length; i++)
	{
		stars[i].show();
	}

	//show numbers
	fill(255);
	text("xDirection: " + (float)ship1.getDirectionX(), 20, 50);
	text("yDirection: " + (float)ship1.getDirectionY(), 20, 60);
}

public void keyReleased()
{
	if (key == 32) //space
	{
		ship1.hyperspace(); //hyerspace stops ship and puts in new location
	}
}

public void mousePressed() //for debugging
{
	setup();
	redraw();
}	

//___classes___
class SpaceShip extends Floater  
{
	private int maxSpd;
	public SpaceShip()
	{
		//how the ship looks
		corners = 4; 
		xCorners = new int[corners];
		yCorners = new int[corners];
		//coordinates for the corners of ship
		xCorners[0] = 0;
		yCorners[0] = 0;		
		xCorners[1] = -6;
		yCorners[1] = -6;
		xCorners[2] = 12;
		yCorners[2] = 0;
		xCorners[3] = -6;
		yCorners[3] = 6;


		//position related
		myColor = color(250);
		myCenterX = width/2;
		myCenterY = height/2;
		myDirectionX = 0;
		myDirectionY = 0;
		myPointDirection = 0;
		maxSpd = 5;

	}

	public void setX(int x) {myCenterX = x;}
	public int getX() {return (int)myCenterX;}
	public void setY(int y) {myCenterY = y;}
	public int getY() {return (int)myCenterY;}
	public void setDirectionX(double x) {myDirectionX = x;}
	public double getDirectionX() {return myDirectionX;}
	public void setDirectionY(double y) {myDirectionY = y;}
	public double getDirectionY() {return myDirectionY;}
	public void setPointDirection(int degrees) {myPointDirection = degrees;}
	public double getPointDirection() {return myPointDirection;}

	public void control()
	{
		//controlling keys: w/s to forward/backward, d/a left/right
		if (keyPressed && key == 'w') {accelerate(0.05f);}
		if (keyPressed && key == 's') {accelerate(-0.05f);} 
		if (keyPressed && key == 'd') {rotate(3);}
		if (keyPressed && key == 'a') {rotate(-3);}
	}

	public void hyperspace()
	{
		//hyperspace
		setX((int)(Math.random()*width));
		setY((int)(Math.random()*height));
		setDirectionX(0);
		setDirectionY(0);
		accelerate(0);
		rotate((int)(Math.random()*360));
	}
};

class Asteroid extends Floater 
{
	private int rotSpd, scaler;
	public Asteroid()
	{
		if (Math.random() >= 0.5f) //can spin both ways
		{
			rotSpd = (int)(Math.random()*5)+1;
		}
		else
		{
			rotSpd = -((int)(Math.random()*5)+1);
		}
		
		//asteroid appearance //the working stuff
		corners = 4;
		int[] xCoords = {12, -12, -12, 12};
		int[] yCoords = {12, 12, -15, -15};
		xCorners = xCoords;
		yCorners = yCoords;

		//random method of drawing random sided asteroids
		scaler = (int)(Math.random()*4)+1;
		corners = (int)(Math.random()*2)+4;
		xCorners = new int[corners];
		yCorners = new int[corners];
		//coordinates for the corners of ship
		for (int i = 0; i < xCorners.length; i++)
		{
			xCorners[i] = (int)(scaler*12*Math.cos(i*(360/(corners))));
			yCorners[i] = (int)(scaler*12*Math.sin(i*(360/(corners))));
		}

		// numbers to relate
		// 4: 12, -12, -12, 12
		// 4: 12, 12, -12, -12

		// 5: 12, -12, -12, 0, 12
		// 5: 12, 12, -12, -15, -12

		// 6: 12, 0, -12, -12, 0, 12
		// 6: 12, 15, 12, -12, -15, -12

		//position related
		myColor = color(150);
		myCenterX = Math.random()*width;
		myCenterY = Math.random()*height;
		myDirectionX = (int)(Math.random()*5)-2;
		myDirectionY = (int)(Math.random()*5)-2;
		myPointDirection = (int)(Math.random()*5)-2;
	}
	public void setX(int x) {myCenterX = x;}
	public int getX() {return (int)myCenterX;}
	public void setY(int y) {myCenterY = y;}
	public int getY() {return (int)myCenterY;}
	public void setDirectionX(double x) {myDirectionX = x;}
	public double getDirectionX() {return myDirectionX;}
	public void setDirectionY(double y) {myDirectionY = y;}
	public double getDirectionY() {return myDirectionY;}
	public void setPointDirection(int degrees) {myPointDirection = degrees;}
	public double getPointDirection() {return myPointDirection;}

	public void move() //move the floater in the current direction of travel
	{
		rotate(rotSpd); //asteroid constantly rotates
		//change the x and y coordinates by myDirectionX and myDirectionY       
		myCenterX += myDirectionX;
		myCenterY += myDirectionY;

		//wrap around screen    
		if(myCenterX >width)
		{
			myCenterX = 0;
		}
		else if (myCenterX<0)
		{
			myCenterX = width;
		}
		if(myCenterY >height)
		{
			myCenterY = 0;
		}
		else if (myCenterY < 0)
		{
			myCenterY = height;
		}
	}
};

abstract class Floater //Do NOT modify the Floater class! Make changes in the SpaceShip class 
{   
	protected int corners;  //the number of corners, a triangular floater has 3
	protected int[] xCorners;
	protected int[] yCorners;
	protected int myColor;
	protected double myCenterX, myCenterY; //holds center coordinates   
	protected double myDirectionX, myDirectionY; //holds x and y coordinates of the vector for direction of travel   
	protected double myPointDirection; //holds current direction the ship is pointing in degrees
	abstract public void setX(int x);
	abstract public int getX();
	abstract public void setY(int y);
	abstract public int getY();
	abstract public void setDirectionX(double x);
	abstract public double getDirectionX();
	abstract public void setDirectionY(double y);
	abstract public double getDirectionY();
	abstract public void setPointDirection(int degrees);
	abstract public double getPointDirection();

	//Accelerates the floater in the direction it is pointing (myPointDirection)   
	public void accelerate(double dAmount)
	{          
		//convert the current direction the floater is pointing to radians
		double dRadians = myPointDirection*(Math.PI/180);
		//change coordinates of direction of travel
		myDirectionX += ((dAmount) * Math.cos(dRadians));
		myDirectionY += ((dAmount) * Math.sin(dRadians));
	}

	public void rotate(int nDegreesOfRotation)   
	{
		//rotates the floater by a given number of degrees    
		myPointDirection+=nDegreesOfRotation;
	}

	public void move() //move the floater in the current direction of travel
	{
		//change the x and y coordinates by myDirectionX and myDirectionY       
		myCenterX += myDirectionX;
		myCenterY += myDirectionY;

		//wrap around screen    
		if(myCenterX >width)
		{
			myCenterX = 0;
		}
		else if (myCenterX<0)
		{
			myCenterX = width;
		}
		if(myCenterY >height)
		{
			myCenterY = 0;
		}
		else if (myCenterY < 0)
		{
			myCenterY = height;
		}
	}

	public void show()  //Draws the floater at the current position
	{
		fill(myColor);
		stroke(myColor);
		//convert degrees to radians for sin and cos
		double dRadians = myPointDirection*(Math.PI/180);
		int xRotatedTranslated, yRotatedTranslated;
		beginShape();
		for(int nI = 0; nI < corners; nI++)
		{
			//rotate and translate the coordinates of the floater using current direction 
			xRotatedTranslated = (int)((xCorners[nI]* Math.cos(dRadians)) - (yCorners[nI] * Math.sin(dRadians))+myCenterX);
			yRotatedTranslated = (int)((xCorners[nI]* Math.sin(dRadians)) + (yCorners[nI] * Math.cos(dRadians))+myCenterY);
			vertex(xRotatedTranslated,yRotatedTranslated);
		}
		endShape(CLOSE);
	}
} 

public class Star
{
	int posX, posY, mySize;
	public Star()
	{
		posX = (int)(Math.random()*width);
		posY = (int)(Math.random()*height);
		mySize = (int)(Math.random()*5);
	}
	public void show()
	{
		noStroke();
		fill(255, 200);
		ellipse(posX, posY, mySize, mySize);
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "AsteroidsGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
