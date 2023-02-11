package com.gdx.castlevania.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;

public class StateHandler {

    public Body b2body;
	public enum State {STANDING, WALKING, JUMPING, CROUCHING};
    public State currentState;
    public State previousState;

    public State getState(){
        if (b2body.getLinearVelocity().x != 0)
            return State.WALKING;
        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(Gdx.input.isKeyPressed(Input.Keys.S))
            return State.CROUCHING;
        else
            return State.STANDING;
    }
}
