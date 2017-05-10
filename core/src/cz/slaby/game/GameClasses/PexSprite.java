package cz.slaby.game.GameClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import cz.slaby.game.Screens.GameScreen;

public class PexSprite extends Image {

    private int id;
    private TextureRegion texture;
    private Texture backTexture;
    private boolean turned = false;
    private boolean turning = false;
    private boolean turnOn = true;
    private boolean found = false;
    private float originX;

    public PexSprite(TextureRegion texture, int id) {
        super(GameScreen.pexBack);
        this.texture = texture;
        this.backTexture = GameScreen.pexBack;
        this.id = id;
        this.originX = this.getX();
    }


    public void startTurning() {
        this.turning = true;
        turnOn = true;
    }

    public int getId() {
        return id;
    }

    public boolean isTurned() {
        return turned;
    }

    public void setFound() {
        this.found = true;
    }

    public boolean isFound() {
        return found;
    }

    public boolean isTurning() {
        return turning;
    }

    public void turn() {
        if (turned) {
            this.setDrawable(new SpriteDrawable(new Sprite(backTexture)));
        } else {
            this.setDrawable(new SpriteDrawable(new Sprite(texture)));
        }
        turned = !turned;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (turning) {
            if (turnOn) {
                if (this.getScaleX() > 0) {
                    this.setScaleX(this.getScaleX() - delta);
                    this.setX(this.originX + (this.getWidth() - (this.getWidth() * this.getScaleX())) / 2);
                } else {
                    turn();
                    turnOn = false;
                }
            } else {
                if (this.getScaleX() < 1) {
                    this.setScaleX(this.getScaleX() + delta);
                    this.setX(this.originX + (this.getWidth() - (this.getWidth() * this.getScaleX())) / 2);
                } else {
                    turning = false;
                    turnOn = true;
                }
            }
        } else {
            originX = this.getX();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!found)
            super.draw(batch, parentAlpha);
    }
}
