package cz.slaby.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;

import cz.slaby.game.GUI.CheckImage;
import cz.slaby.game.GUI.PagedScrollPane;
import cz.slaby.game.Pexeso;


public class PexSelectScreen implements Screen {

    private SpriteBatch batch;
    private Stage stage;
    private ArrayList<ArrayList<TextureRegion>> pexesoSets;
    private PagedScrollPane pagedScrollPane;
    private Table container;
    private int tileCount = 0;
    private float time = 0;
    private int width, height;
    private ArrayList<TextureRegion> set;

    public PexSelectScreen(SpriteBatch batch) {
        this.batch = batch;
        set = new ArrayList<TextureRegion>();
    }

    public void setTileCount(int tileCount) {
        this.tileCount = tileCount;
    }

    public void setTime(float time) {
        this.time = time;
    }

    @Override
    public void show() {
        stage = new Stage();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        Container titleWrap = new Container();
        Texture titleText = new Texture(Gdx.files.internal("vyber-sady-text.png"));
        NinePatch patchUp = new NinePatch(new Texture(Gdx.files.internal("buttons/btnUp.png")), 4, 4, 4, 4);
        NinePatch patchDown = new NinePatch(new Texture(Gdx.files.internal("buttons/btnDown.png")), 4, 4, 4, 4);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new NinePatchDrawable(patchUp);
        style.down = new NinePatchDrawable(patchDown);
        style.font = createFont(20);
        style.fontColor = new Color(253 / 255f, 26 / 255f, 20 / 255f, 1f);
        TextButton button = new TextButton("Hraj sadu", style);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                set = new ArrayList<TextureRegion>(pexesoSets.get(0).subList(0, tileCount));
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(batch,
                        set,
                        new Texture(Gdx.files.internal("pexBacks/pexeso-lic.png")), time));
            }
        });

        Image background = new Image(new TextureRegion(Pexeso.background, width, height * 5 / 6));
        Image title = new Image(titleText);

        container = new Table();
        pexesoSets = new ArrayList<ArrayList<TextureRegion>>();

        try {
            loadSets();
        } catch (Exception e) {
            Gdx.app.log("PexLoad", "err", e);
        }

        pagedScrollPane = new PagedScrollPane();
        pagedScrollPane.setFlingTime(0.1f);
        pagedScrollPane.setPageSpacing(25);


        loadPages();


        title.setScaling(Scaling.fillX);

        titleWrap.width(width * 3 / 4).height(height / 6);
        titleWrap.setActor(title);

        //container.debug();
        container.top();
        container.row().height(height / 6).width(width);
        container.add(titleWrap);
        container.row();
        container.add(pagedScrollPane).top();
        container.row().expandY();
        container.add(button).bottom().right().width(width / 4 - 20).height(100).pad(20);


        container.setFillParent(true);
        stage.addActor(background);
        stage.addActor(container);
        Gdx.input.setInputProcessor(stage);
    }

    private void openGal() {
        //file = new Label("file", skin);
        /*TextButton btn = new TextButton("Text", skin);
        btn.setWidth(400f);
        btn.setHeight(100f);
        btn.getLabel().setFontScale(2f);
        btn.setPosition(Gdx.graphics.getWidth() / 2 - 200f, Gdx.graphics.getHeight() / 2 - 100f);

        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cz.slaby.game.Pexeso.galleryOpener.getGalleryImagePath();
            }
        });*/
    }

    private void loadPages() {
        int c = 0;
        int pagecounter = 0;
        float size = width / 4 - 20;

        for (ArrayList<TextureRegion> set : pexesoSets) {
            Table page = new Table();
            page.top();
            for (TextureRegion pexTex : set) {
                if (c != 0 && c % 4 == 0) page.row();
                page.add(new CheckImage(pexTex)).pad(10).size(size);
                c++;
                pagecounter++;
                if (pagecounter == 16) {
                    pagedScrollPane.addPage(page);
                    page = new Table();
                    page.top();
                    pagecounter = 0;
                    c = 0;
                }
            }
            pagedScrollPane.addPage(page);
            c = 0;
            pagecounter = 0;
        }
    }

    private BitmapFont createFont(float dp) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = Pexeso.parameter;
        FreeTypeFontGenerator generator = Pexeso.generator;
        int fontSize = (int) (dp * Gdx.graphics.getDensity());
        parameter.size = fontSize;
        return generator.generateFont(parameter);
    }

    private void loadSets() {
        FileHandle pexesosDir = null;
        FileHandle[] internalSets;
        try {
            pexesosDir = Gdx.files.internal("pexeso/");
        } catch (Exception e) {
            Gdx.app.log("OpenFile", "Chyba při pokusu načíst pexesa", e);
        }
        if (pexesosDir != null) {
            internalSets = pexesosDir.list(); // všechny složky v adresáři pexeso
            for (FileHandle set : internalSets) // pro každý adresář
            {
                if (set.isDirectory()) {
                    FileHandle[] setImgs = set.list();
                    ArrayList<TextureRegion> imgSet = new ArrayList<TextureRegion>();
                    for (FileHandle img : setImgs) {
                        imgSet.add(new TextureRegion(new Texture(img)));
                    }
                    pexesoSets.add(imgSet);
                } else {
                    Texture sheet = new Texture(set);
                    ArrayList<TextureRegion> sheetSet = new ArrayList<TextureRegion>();
                    TextureRegion[][] tiles = new TextureRegion(sheet).split(256, 256);
                    for (TextureRegion[] row : tiles) {
                        for (TextureRegion tile : row) {
                            sheetSet.add(tile);
                        }
                    }
                    pexesoSets.add(sheetSet);
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(253 / 255f, 26 / 255f, 20 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        batch.begin();
        stage.draw();
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
        batch.dispose();
    }
}

