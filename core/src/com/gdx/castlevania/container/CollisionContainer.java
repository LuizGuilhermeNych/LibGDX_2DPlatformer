package com.gdx.castlevania.container;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CollisionContainer {
	
	public CollisionContainer(World world, TiledMap map) {
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
        //Ground Layer (3)
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
        	Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 3, rect.getY() + rect.getHeight() / 3);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 3, rect.getHeight() / 3);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Platform layer (7)
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 3, rect.getY() + rect.getHeight() / 3);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 3, rect.getHeight() / 3);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
		
	}

}
