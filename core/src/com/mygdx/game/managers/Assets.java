package com.mygdx.game.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

public class Assets {
    private static Assets instance = new Assets();
    private TextureAtlas atlas;
    private AssetManager assetManager;


    public static Assets getInstance() {
        return instance;
    }

    public TextureRegion getTexture(String name) {
        return atlas.findRegion(name);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    private Assets() {
        this.assetManager = new AssetManager();
        // assetManager.load("unamed.pack", TextureAtlas.class);
    }

    public void clear() {
        assetManager.clear();
    }

    public void makeLinks() {
        atlas = assetManager.get("unamed.pack", TextureAtlas.class);
    }

    public void loadAssets(ScreenManager.ScreenType type) {
        switch (type) {
            case GAME:
                assetManager.load("unamed.pack", TextureAtlas.class);
               // assetManager.load("music.wav", Music.class);
                // assetManager.load("laser.wav", Sound.class);
                createStdFont(48);
                break;
            case MENU:
                assetManager.load("unamed.pack", TextureAtlas.class);
                createStdFont(32);
                createStdFont(96);
                break;
        }
    }

    public void createStdFont(int size) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        fontParameter.fontFileName = "gomarice.ttf";
        fontParameter.fontParameters.size = size;
        fontParameter.fontParameters.color = Color.WHITE;
        fontParameter.fontParameters.borderWidth = 2;
        fontParameter.fontParameters.borderColor = Color.BLACK;
        fontParameter.fontParameters.shadowOffsetX = 2;
        fontParameter.fontParameters.shadowOffsetY = 2;
        fontParameter.fontParameters.shadowColor = Color.GRAY;
        assetManager.load("gomarice" + size + ".ttf", BitmapFont.class, fontParameter);
    }

}
