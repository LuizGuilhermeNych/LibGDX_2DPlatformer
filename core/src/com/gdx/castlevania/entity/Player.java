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
import com.gdx.castlevania.screen.PlayScreen;

public class Player extends Sprite{
	
	public World world;
	public Body b2body;
	public float stateTimer;
	public Animation<TextureRegion> playerWalk;
	public Animation<TextureRegion> playerCrouch;
	public Animation<TextureRegion> playerJump;
	public InputHandler inputHandler;
	
	public Player(World world, PlayScreen screen) {
		this.world = world;
		definePlayer();

		stateTimer = 0;

		TextureAtlas jumpAtlas;
		jumpAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_Jump.atlas"));
		playerJump = new Animation(0.1f, jumpAtlas.getRegions());

		TextureAtlas walkAtlas;
		walkAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_Walk.atlas"));
		playerWalk = new Animation(0.2f, walkAtlas.getRegions());

		TextureAtlas crouchAtlas;
		crouchAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_Crouch.atlas"));
		playerCrouch = new Animation(0.2f, crouchAtlas.getRegions());
	}
	
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
//		setRegion(getFrame(dt));
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
