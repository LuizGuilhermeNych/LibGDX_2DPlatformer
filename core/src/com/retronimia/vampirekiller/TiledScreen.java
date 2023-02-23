package com.retronimia.vampirekiller;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TiledScreen extends ScreenAdapter {

    private final Viewport viewport = new ExtendViewport(16, 9);
    private final OrthogonalTiledMapRenderer renderer;
    private final int tileSize;
    private VampireKiller game;

    public TiledScreen(VampireKiller game){
        this.game = game;
        TiledMap map = new TmxMapLoader().load("debug_map.tmx");
        tileSize = ((TiledMapTileLayer) map.getLayers().get(1)).getTileWidth();
        renderer = new OrthogonalTiledMapRenderer(map, 1f / tileSize);
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(Color.BLACK);
        renderer.setView((OrthographicCamera) viewport.getCamera());
        renderer.render();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
