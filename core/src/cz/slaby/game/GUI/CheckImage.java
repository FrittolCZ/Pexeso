package cz.slaby.game.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import cz.slaby.game.Pexeso;

public class CheckImage extends Stack {

    private Image image;
    private CheckBox checkBox;
    private Skin skin;

    public CheckImage(TextureRegion texture) {
        //skin = Pexeso.skin;
        image = new Image(texture);
        //checkBox = new CheckBox("", skin);
        this.add(image);
        //  this.add(checkBox);
    }
}
