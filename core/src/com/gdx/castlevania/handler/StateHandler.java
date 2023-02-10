package com.gdx.castlevania.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;

public class StateHandler {

    public Body b2body;
	public enum State {STANDING, WALKING, JUMPING, CROUCHING};
    public State currentState;
    public State previousState;

    public void checkIfStanding(){
        if(b2body.getLinearVelocity().x == 0)
            currentState = State.STANDING;
    }

    public void checkIfRunning(){
        if (b2body.getLinearVelocity().x != 0)
            currentState = State.WALKING;
    }

    public void checkIfJumping(){
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            currentState = State.JUMPING;
    }

    

}
