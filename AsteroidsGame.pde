SpaceShip ship1;
Star[] stars;

public void setup() 
{
	size (400, 400);
	ship1 = new SpaceShip();

	stars = new Star[100];
	for (int i = 0; i < stars.length; i++)
	{
		stars[i] = new Star();
	}
}

public void draw() 
{
	background(10, 40);
	ship1.control(); //controlling keys
	// ship1.hyperspace(); //hyperspace puts ship in new location
	ship1.move();
	ship1.show();

	//stars
	for (int i = 0; i < stars.length; i++)
	{
		stars[i].show();
	}
}

public void keyReleased()
{
	if (key == 32)
	{
		ship1.hyperspace();
	}
}	

//classes
class SpaceShip extends Floater  
{   
	public SpaceShip()
	{
		//how the ship looks
		corners = 3; 
		xCorners = new int[corners];
		yCorners = new int[corners];
		//coordinates for the corners of ship 
		xCorners[0] = -6;
		yCorners[0] = -6;
		xCorners[1] = 12;
		yCorners[1] = 0;
		xCorners[2] = -6;
		yCorners[2] = 6;

		//position related
		myColor = color(150);
		myCenterX = width/2;
		myCenterY = height/2;
		myDirectionX = 0;
		myDirectionY = 0;
		myPointDirection = 0;
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
		if (keyPressed && key == 'w') {accelerate(0.2);} 
		if (keyPressed && key == 's') {accelerate(-0.2);}
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
		fill(250);
		ellipse(posX, posY, mySize, mySize);
	}
}