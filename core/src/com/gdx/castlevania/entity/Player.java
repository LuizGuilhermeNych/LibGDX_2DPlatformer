package com.gdx.castlevania.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.castlevania.screen.PlayScreen;

public class Player extends Sprite{
	
	public World world;
	public Body b2body;
	
	public Player(World world, PlayScreen screen) {
		this.world = world;
		definePlayer();
	}
	
	public void update(float dt) {
		
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
