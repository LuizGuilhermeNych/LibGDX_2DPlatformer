package com.gdx.castlevania.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.castlevania.handler.InputHandler;
import com.gdx.castlevania.handler.StateHandler;
import com.gdx.castlevania.screen.PlayScreen;

public class Player extends Sprite{

	public World world;
	public Body b2body;
	public Player player;
	public float stateTimer;
	public Animation<TextureRegion> playerWalk;
	public Animation<TextureRegion> playerCrouch;
	public Animation<TextureRegion> playerJump;
	public Animation<TextureRegion> playerStand;
	public InputHandler inputHandler;
	public StateHandler stateHandler;
	public boolean walkingRight;

	public Player(World world, PlayScreen screen, StateHandler stateHandler) {
		this.world = world;
		this.stateHandler = stateHandler;

		stateHandler = new StateHandler(player);
		stateHandler.currentState = StateHandler.State.STANDING;
		stateHandler.previousState = StateHandler.State.STANDING;

		stateTimer = 0;
		walkingRight = true;

		TextureAtlas jumpAtlas;
		jumpAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_Jump.atlas"));
		playerJump = new Animation(0.1f, jumpAtlas.getRegions());

		TextureAtlas walkAtlas;
		walkAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_Walk.atlas"));
		playerWalk = new Animation(0.2f, walkAtlas.getRegions());

		TextureAtlas crouchAtlas;
		crouchAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_Crouch.atlas"));
		playerCrouch = new Animation(0.2f, crouchAtlas.getRegions());

		TextureAtlas standAtlas;
		standAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_Idle.atlas"));
		playerStand = new Animation(0.2f, standAtlas.getRegions());

		definePlayer();
	}

	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(player.stateHandler.getFrame(dt));
	}

	public void definePlayer() {

		//Instancia um corpo, configura seu posicionamento no mapa e o tipo desse corpo
		BodyDef bdef = new BodyDef();
		bdef.position.set(200, 72);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		//Configura o formato do corpo
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(15);

		//Atribuições de corpo e formato
		fdef.shape = shape;
		b2body.createFixture(fdef);
	}
}
