package com.vale.castlevania.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.vale.castlevania.Castlevania;
import com.vale.castlevania.Screens.PlayScreen;
import org.w3c.dom.Text;

import java.io.Console;

public class Simon extends Sprite {

    public World world;
    public Body b2body;
    private TextureRegion simonStand;
    public enum State {FALLING, JUMPING, STANDING, RUNNING, CROUCHING, STATTACK, CRATTACK, JPATTACK};
    public State currentState;
    public State previousState;
    public Animation<TextureRegion> simonWalk;
    public Animation<TextureRegion> simonJump;
    public Animation<TextureRegion> simonCrouch;
    public Animation<TextureRegion> simonStAttack;
    public Animation<TextureRegion> simonCrAttack;
    public Animation<TextureRegion> simonJpAttack;
    private boolean walkingRight;
    public float stateTimer;
    public boolean isAttacking;


    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region =null;
        switch(currentState){
            case JUMPING:
                region = simonJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = simonWalk.getKeyFrame(stateTimer, true);
                break;
            case CROUCHING:
                region = simonCrouch.getKeyFrame(stateTimer, true);
                break;
            case STATTACK:
                if (isAttacking){
                    region = simonStAttack.getKeyFrame(stateTimer);
                    stateTimer += dt;
                } else if (simonStAttack.isAnimationFinished(stateTimer)){
                    isAttacking = false;
                }
                break;
            case CRATTACK:
                region = simonCrAttack.getKeyFrame(stateTimer);
                break;
            case JPATTACK:
                region = simonJpAttack.getKeyFrame(stateTimer);
                break;
            case FALLING:
            case STANDING:
            default:
                region = simonStand;
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !walkingRight) && !region.isFlipX()){
            region.flip(true, false);
            walkingRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || walkingRight) && region.isFlipX()) {
            region.flip(true, false);
            walkingRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){

        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                return State.JPATTACK;
            }
            return State.JUMPING;
        }
        else if(b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        }
        else if (b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                return State.CRATTACK;
            }
            return State.CROUCHING;
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            isAttacking = true;
            stateTimer = 0;
            return State.STATTACK;
        }
        else {
            return State.STANDING;
        }
    }

    public void defineSimon(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(200 / Castlevania.PPM, 72 / Castlevania.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / Castlevania.PPM);

        fdef.shape = shape;
//        fdef.friction = 1;
        b2body.createFixture(fdef);
    }

    public Simon(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("idle"));

        this.world = world;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        walkingRight = true;

        TextureAtlas walkingAtlas;
        walkingAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_Walk.atlas"));
        simonWalk = new Animation(0.2f, walkingAtlas.getRegions());

        TextureAtlas jumpingAtlas;
        jumpingAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_Jump.atlas"));
        simonJump = new Animation(0.1f, jumpingAtlas.getRegions());

        TextureAtlas crouchingAtlas;
        crouchingAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_Crouch.atlas"));
        simonCrouch = new Animation(0.2f, crouchingAtlas.getRegions());

        TextureAtlas stAttackAtlas;
        stAttackAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_stAttack.atlas"));
        simonStAttack = new Animation(0.1f, stAttackAtlas.getRegions());

        TextureAtlas crAttackAtlas;
        crAttackAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_crAttack.atlas"));
        simonCrAttack = new Animation(0.1f, crAttackAtlas.getRegions());

        TextureAtlas jpAttackAtlas;
        jpAttackAtlas = new TextureAtlas(Gdx.files.internal("Simon_SSB/Simon_SSB_jpAttack.atlas"));
        simonJpAttack = new Animation(0.1f, jpAttackAtlas.getRegions());

        simonStand = new TextureRegion(getTexture(), 0, 2, 32, 32);
        defineSimon();

        setBounds(0, 0, 32 / Castlevania.PPM, 32 / Castlevania.PPM);
        setRegion(simonStand);
    }
}
