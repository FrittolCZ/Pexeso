package slaby.cz.game.GameClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class PexSprite extends Image {

    private int id;
    private Texture texture;
    private Texture backTexture;
    private boolean turned = false;
    private boolean found = false;

    public PexSprite(Texture texture, int id) {
        super(slaby.cz.game.Screens.GameScreen.pexBack);
        this.texture = texture;
        this.backTexture = slaby.cz.game.Screens.GameScreen.pexBack;
        this.id = id;
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

    public void turn() {
        if (turned) {
            this.setDrawable(new SpriteDrawable(new Sprite(backTexture)));
        } else {
            this.setDrawable(new SpriteDrawable(new Sprite(texture)));
        }
        turned = !turned;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!found)
            super.draw(batch, parentAlpha);
    }
}
