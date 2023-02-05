package com.vale.castlevania.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.vale.castlevania.Castlevania;
import com.vale.castlevania.Sprites.Candle;

public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Ground Layer (3)
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Castlevania.PPM, (rect.getY() + rect.getHeight() / 2) / Castlevania.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Castlevania.PPM, rect.getHeight() / 2 / Castlevania.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Platform layer (7)
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Castlevania.PPM, (rect.getY() + rect.getHeight() / 2)/ Castlevania.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Castlevania.PPM, rect.getHeight() / 2 / Castlevania.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Candles layer (5)
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Candle(world, map, rect);
        }
    }
}
