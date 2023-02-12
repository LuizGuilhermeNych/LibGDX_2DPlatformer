package com.gdx.castlevania.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.gdx.castlevania.entity.Player;

public class StateHandler {


    private Player player;
    public Body b2body;
	public enum State {STANDING, WALKING, JUMPING, CROUCHING};
    public State currentState;
    public State previousState;

    public StateHandler(Player player){
        this.player = player;
    }

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

    public TextureRegion getFrame(float dt){

//		player.stateHandler.currentState = player.stateHandler.getState();

        TextureRegion region;
        switch (player.stateHandler.currentState){
            case WALKING:
                region = player.playerWalk.getKeyFrame(player.stateTimer, true);
                break;
            case JUMPING:
                region = player.playerJump.getKeyFrame(player.stateTimer);
                break;
            case CROUCHING:
                region = player.playerCrouch.getKeyFrame(player.stateTimer);
                break;
            default:
                region = player.playerStand.getKeyFrame(player.stateTimer);
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !player.walkingRight) && !region.isFlipX()){
            region.flip(true, false);
            player.walkingRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || player.walkingRight) && region.isFlipX()) {
            region.flip(true, false);
            player.walkingRight = true;
        }
        return region;
    }
}
