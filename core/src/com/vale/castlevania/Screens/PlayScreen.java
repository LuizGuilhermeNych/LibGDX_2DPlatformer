package com.vale.castlevania.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.vale.castlevania.Castlevania;
import com.vale.castlevania.Scenes.Hud;
import com.vale.castlevania.Sprites.Simon;
import com.vale.castlevania.Tools.B2WorldCreator;

public class PlayScreen implements Screen {

    private Castlevania game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Simon player;
    private TextureAtlas atlas;
    public PlayScreen(Castlevania game){
        atlas = new TextureAtlas("Simon_SSB/Simon_SSB_Idle.atlas");
        this.game = game;

        //Cria a câmera para seguir Simon pelo mapa
        gamecam = new OrthographicCamera();

        //Cria um "FitViewport para manter o aspect ratio independente do tamanho da tela
        gamePort = new FitViewport(Castlevania.V_WIDTH / Castlevania.PPM, Castlevania.V_HEIGHT / Castlevania.PPM, gamecam);

        //Cria a HUD do jogo com score/timer/level
        hud = new Hud(game.batch);

        //Carrega o mapa e configura o renderizador
        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Castlevania.PPM);

        //Configuramos a camera para centralizar corretamente no inicio do mapa
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);


        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);
        player = new Simon(world, this);
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }
    @Override
    public void show() {

    }

    public void handleInput(float dt){

            //Nesse primeiro statement o personagem salta ao pressionar a direção UP. ao ajustar o valor no y-axis
            //é poossível ajustar o impulso do pulo do personagem.
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);

            //No segundo e terceiro statement o personagem é impulsionado para os lados ao pressionar a direção
            //LEFT ou RIGHT. É possível ajustar a velocidade alterando o valor numérico no final do "if".
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 1)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -1)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update(float dt){
        handleInput(dt);
        world.step(1/60f, 6, 2);
        player.update(dt);
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
