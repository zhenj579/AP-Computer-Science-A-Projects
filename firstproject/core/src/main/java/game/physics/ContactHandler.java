package game.physics;



import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import game.screens.PlayScreen;

public class ContactHandler implements ContactListener{
	
	private PlayScreen play;
	

	public ContactHandler(PlayScreen play) {
		this.play = play;
	}
	

	@Override
	public void beginContact(Contact contact) {
		if(contact.getFixtureB().getBody().getUserData().equals("portal")) {
			play.mapChange(true);
		}
		if(contact.getFixtureB().getBody().getUserData().equals("portalBack")) {
			play.revertMap(true);
		}
		if(contact.getFixtureB().getBody().getUserData().equals("tree")) {
			play.addItem("wood");
			play.bodyToRemove = contact.getFixtureB().getBody();
		}
		if(contact.getFixtureB().getBody().getUserData().equals("rock")) {
			play.addItem("rock");
			play.bodyToRemove = contact.getFixtureB().getBody();
		}
		
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}
	
	
	
	

}
