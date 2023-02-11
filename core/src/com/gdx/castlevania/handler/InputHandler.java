package com.gdx.castlevania.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.castlevania.entity.Player;

public class InputHandler {

    public Player player;
    public World world;

    public void handleInput(Player player, float dt){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 50f), player.b2body.getWorldCenter(), true);

        if (Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 1)
            player.b2body.applyLinearImpulse(new Vector2(40f, 0), player.b2body.getWorldCenter(), true);

        if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -1)
            player.b2body.applyLinearImpulse(new Vector2(-40f, 0), player.b2body.getWorldCenter(), true);
    }
}
