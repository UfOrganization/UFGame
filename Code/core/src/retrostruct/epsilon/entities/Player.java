package retrostruct.epsilon.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xml.internal.resolver.helpers.Debug;

import retrostruct.epsilon.enums.Verbs;
import retrostruct.epsilon.graphics.Animation;
import retrostruct.epsilon.handlers.ItemHandler;
import retrostruct.epsilon.handlers.MouseHandler;

public class Player extends Character {
	
	private Vector2 direction = new Vector2();
	private int targetX = 0;
	private int targetY = 0;
	private int floorHeight = 150;
	private float speed = 100.0f;
	private int[] storage = new int[8]; // Create an inventory with 8 slots
	
	public int getItemIdFromStorage(int i) { return storage[i]; } // Should probably check for array out of bounds exeption...

	public Player(float x, float y) {
		super(x, y);
		animation = new Animation("badlogic.jpg", 256, 256);
	}
	
	public void update(MouseHandler mouse, OrthographicCamera camera) {
		if(mouse.isPressed()) {
			// If nothing is pressed, instead set the target
			if(!ItemHandler.interact(mouse.getX(), mouse.getY(), Verbs.PICK_UP, this))
			{
				targetX = mouse.getX();
				targetY = mouse.getY();				
			}
		}
		
		// Reset direction
		direction.x = 0;
		direction.y = 0;
		
		// Get direction 
		if(targetX > position.x + speed) direction.x = 1;
		else if(targetX < position.x) direction.x = -1;
		if(targetY > position.y + speed) direction.y = 1;
		else if(targetY < position.y) direction.y = -1;
		
		// Calculate velocity with delta time
		float velocityx = direction.x * speed * Gdx.graphics.getDeltaTime();
		float velocityy = direction.y * speed * Gdx.graphics.getDeltaTime();
		
		// Translate camera and move player
		camera.translate(velocityx, 0);
		camera.position.x = Math.max(0, Math.min(100, camera.position.x));
		position.x += velocityx;
		position.y += velocityy;
		
		String posMes = Float.toString(position.x) + ":" + Float.toString(position.y);
		
		System.out.println(posMes);
		
		
		position.y = clamp(position.y, (float)(0), (float)(floorHeight));
	}
	
	public static float clamp(float val, float min, float max) {
	    return Math.max(min, Math.min(max, val));
	}
}
