package com.gdx.castlevania.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.castlevania.CastlevaniaGDX;
import com.gdx.castlevania.container.CollisionContainer;
import com.gdx.castlevania.entity.Player;
import com.gdx.castlevania.handler.InputHandler;
import com.gdx.castlevania.handler.StateHandler;

public class PlayScreen implements Screen{
	
	//Atributos de carregamento do mapa
	private TiledMap map;
	private TmxMapLoader mapLoader;
	private OrthogonalTiledMapRenderer renderer;
	
	//Atributos de carregamento da câmera
	private CastlevaniaGDX game;
	private OrthographicCamera gameCam;
	private Viewport gamePort;
	
	//Atributo para carregamento da física no mundo
	private World world;
	
	private Box2DDebugRenderer b2dr;
	
	//Atributo de carregamento do personagem
	private Player player;
	private StateHandler stateHandler;
	private InputHandler inputHandler;
		
	public PlayScreen (CastlevaniaGDX game) {
		this.game = game;
		
		//Instanciando a câmera e ponto de vista
		gameCam = new OrthographicCamera();
		gamePort = new FitViewport(CastlevaniaGDX.V_WIDTH, CastlevaniaGDX.V_HEIGHT, gameCam);
		
		//Carregamento o mapa e unidade de escala
		mapLoader =  new TmxMapLoader();
		map = mapLoader.load("stage_0.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 1.5f);
		
		//Inicializando o posicionamento da câmera com base nas dimensões do mapa
		gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		
		//Inicializando gravidade e simulações
		world = new World(new Vector2(0, -10f), true);
		
		//Debug nos sprites
		b2dr = new Box2DDebugRenderer();
		
		//Configura colisões no mapa
		new CollisionContainer(world, map);

		inputHandler = new InputHandler();

		stateHandler = new StateHandler(player);

		//Inicializando personagem no mundo
		player = new Player(world, this, stateHandler);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public void update(float dt) {
		inputHandler.handleInput(player, dt);
		world.step(1f, 3, 1);
		
		player.update(dt);
		
		gameCam.position.x = player.b2body.getPosition().x;
		gameCam.update();
		
		renderer.setView(gameCam);
	}


	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render();
		
		b2dr.render(world, gameCam.combined);
		
		game.batch.setProjectionMatrix(gameCam.combined);
		game.batch.begin();
//		player.draw(game.batch);
		game.batch.end();
	}


	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
	}

}
