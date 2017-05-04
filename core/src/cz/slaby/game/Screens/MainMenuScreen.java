package cz.slaby.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;


public class MainMenuScreen implements Screen {

    private SpriteBatch batch;
    private Stage mainMenuStage;


    public MainMenuScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        Button btn1, btn2, btn3, btn4;
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        mainMenuStage = new Stage();
        Table table = new Table();
        Container titleWrap = new Container();
        Texture texture = new Texture(Gdx.files.internal("background.png"));
        Texture btn1Up = new Texture(Gdx.files.internal("buttons/1-obtiznost-up.png"));
        Texture btn1Down = new Texture(Gdx.files.internal("buttons/1-obtiznost-down.png"));
        Texture btn2Up = new Texture(Gdx.files.internal("buttons/2-obtiznost-up.png"));
        Texture btn2Down = new Texture(Gdx.files.internal("buttons/2-obtiznost-down.png"));
        Texture btn3Up = new Texture(Gdx.files.internal("buttons/3-obtiznost-up.png"));
        Texture btn3Down = new Texture(Gdx.files.internal("buttons/3-obtiznost-down.png"));
        Texture btn4Up = new Texture(Gdx.files.internal("buttons/4-obtiznost-up.png"));
        Texture btn4Down = new Texture(Gdx.files.internal("buttons/4-obtiznost-down.png"));
        Texture titleText = new Texture(Gdx.files.internal("vyber-obtiznost-text.png"));
        Image background = new Image(new TextureRegion(texture, width, height * 5 / 6));
        Image title = new Image(titleText);
        title.setScaling(Scaling.fillX);

        Button.ButtonStyle btn1Style = new Button.ButtonStyle();
        btn1Style.up = new TextureRegionDrawable(new TextureRegion(btn1Up));
        btn1Style.down = new TextureRegionDrawable(new TextureRegion(btn1Down));

        Button.ButtonStyle btn2Style = new Button.ButtonStyle();
        btn2Style.up = new TextureRegionDrawable(new TextureRegion(btn2Up));
        btn2Style.down = new TextureRegionDrawable(new TextureRegion(btn2Down));

        Button.ButtonStyle btn3Style = new Button.ButtonStyle();
        btn3Style.up = new TextureRegionDrawable(new TextureRegion(btn3Up));
        btn3Style.down = new TextureRegionDrawable(new TextureRegion(btn3Down));

        Button.ButtonStyle btn4Style = new Button.ButtonStyle();
        btn4Style.up = new TextureRegionDrawable(new TextureRegion(btn4Up));
        btn4Style.down = new TextureRegionDrawable(new TextureRegion(btn4Down));


        btn1 = new Button(btn1Style);
        btn2 = new Button(btn2Style);
        btn3 = new Button(btn3Style);
        btn4 = new Button(btn4Style);

        titleWrap.width(width * 3 / 4).height(height / 6);
        titleWrap.setActor(title);

        //table.debug();
        table.setFillParent(true);
        table.row().height(height / 6).width(width);
        table.add(titleWrap).colspan(2);
        table.row().width(width / 3.081f).height(width / 3.081f).padTop(width / 11.4f * 2).padBottom(width / 17.1f);
        table.add(btn1).right().padRight(width / 17.1f);
        table.add(btn2).left().padLeft(width / 17.1f);
        table.row().width(width / 3.081f).height(width / 3.081f).padTop(width / 17.1f).padBottom(width / 17.1f);
        table.add(btn3).right().padRight(width / 17.1f);
        table.add(btn4).left().padLeft(width / 17.1f);

        table.top();
        mainMenuStage.addActor(background);
        mainMenuStage.addActor(table);
        Gdx.input.setInputProcessor(mainMenuStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(253 / 255f, 26 / 255f, 20 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        mainMenuStage.draw();
        //Gdx.app.log("Click", "Density: " + Gdx.graphics.getDensity());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
