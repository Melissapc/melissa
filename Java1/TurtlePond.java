/****** MUST BE USED WITH JAVA 1.6 *****/

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.TurtlePanel;

public class TurtlePond implements KeyEventDispatcher
{

	// 1. Set a location for the food
	int cookieX = 50;
	int cookieY = 90;
	  
	

	// 2. Choose the speed you want the Tortoise to go at
	int speed = 10;

	void setup()
	{

		// 3. Add an intro message to tell the user what to do
		JOptionPane.showMessageDialog(null, "find food asap!");
		// 4. For debugging purposes, show the food
		showFood();
		
		hideFood();
	}

	private void goUp()
	{
		Tortoise.move(speed);
	}

	private void goDown()
	{
		Tortoise.move(-speed);
	}

	private void goLeft()
	{
		// 5. make the tortoise move left (3 lines of code)
		Tortoise.turn(-90);
		Tortoise.move(20);
		Tortoise.turn(90);

	}

	private void goRight()
	{
		// 6. make the tortoise move right
		Tortoise.turn(90);
		Tortoise.move(20);
		Tortoise.turn(-90);

	}

	private void checkForFood() throws Exception
	{
		int LocationX = Tortoise.getX();
		int LocationY = Tortoise.getY();

		// 7. If the Tortoise is within 100 pixels of the food, set the
		// background color to yellow
		if(LocationX<=100){
			setBackgroundColor(Color.yellow);
		}

		// 8. If the Tortoise is within 50 pixels of the food, set the
		// background color to orange
		if(LocationX<=50){
			setBackgroundColor(Color.orange);
		}

		// 9. If the Tortoise is within 20 pixels of the food, set the
		// background color to red
		if(LocationX<=20){
			setBackgroundColor(Color.red);
		}

		// 10. If tortoise is within 5 pixels of the cookie, make a pop-up to
		// tell them they found it
		if(LocationX==5){
			JOptionPane.showMessageDialog(null, "YOU FOUND the food");
		}

		// 11. If the Tortoise crosses it's own path, move them back to the
		// beginning
	   if(wasHereBefore(Tortoise.getX(), Tortoise.getY())){
		   getFirstLocation();
	   }

		// 12. If more than 20 seconds have elapsed, tell them the turtle died
		// of hunger
	   if(getTimeElapsed()>=20){
		   JOptionPane.showMessageDialog(null, "THE FRIGGIN TURTLE DIED!");
		   
		   
	   }

	}

	private long getTimeElapsed()
	{
		Date currentTime = new Date();
		return (currentTime.getTime() - startTime.getTime()) / 1000;
	}

	void setBackgroundColor(Color color)
	{
		Tortoise.getBackgroundWindow().setBackground(color);
	}

	private void hideFood()
	{
		window.remove(component);
	}

	private void showFood()
	{
		// If the food doesn't show up, make sure you are on Java 1.6
		component.setLocation(cookieX, cookieY);
		window.add(component);
	}

	private boolean wasHereBefore(int xPosition, int yPosition)
	{
		if (previousLocations.contains(new Point(xPosition, yPosition)))
			return true;
		else
			return false;
	}

	private Point getFirstLocation()
	{
		return previousLocations.get(0);
	}

	/*********************** don't worry about the stuff under here ******************/

	TurtlePanel window = Tortoise.getBackgroundWindow();
	Label component = new Label("*");
	Date startTime = new Date();

	public static void main(String[] args)
	{
		TurtlePond feeder = new TurtlePond();
		feeder.controlTheTortoise();
		feeder.setup();
	}

	private void controlTheTortoise()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		Tortoise.show();
		Tortoise.setPenColor(Colors.Purples.DarkMagenta);
		Tortoise.getBackgroundWindow().setBackground(Colors.Grays.SlateGray);
		Tortoise.setSpeed(10);
	}

	public boolean dispatchKeyEvent(KeyEvent e)
	{
		if (e.getID() == KeyEvent.KEY_PRESSED)
		{

			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				goRight();
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				goLeft();
			if (e.getKeyCode() == KeyEvent.VK_UP)
				goUp();
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
				goDown();

			try
			{
				checkForFood();
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}

			savePosition(Tortoise.getX(), Tortoise.getY());
		}
		return false;
	}

	ArrayList<Point> previousLocations = new ArrayList<Point>();

	private void savePosition(int xPosition, int yPosition)
	{
		previousLocations.add(new Point(xPosition, yPosition));
	}
}
