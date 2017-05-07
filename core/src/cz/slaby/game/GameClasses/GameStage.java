package cz.slaby.game.GameClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.Random;

import cz.slaby.game.Pexeso;


public class GameStage extends Stage {
    private Table tab;
    private PexSprite pexArray[][];
    private float pieceSize;
    private ArrayList<PexSprite> hitted;
    private float timer = 0;
    private int pieceFound = 0;
    private int pieceToFound;

    public GameStage(ArrayList<TextureRegion> set) {
        tab = new Table();
        this.pieceToFound = set.size();
        loadPexArray(set);
        shufPex();
        loadTab();
        //tab.debug();
        tab.setFillParent(true);

        this.addActor(new Image(new TextureRegion(Pexeso.background, Pexeso.WIDTH, Pexeso.HEIGHT)));
        this.addActor(tab);
        hitted = new ArrayList<PexSprite>();
    }

    public boolean gameOver() {
        return pieceFound == pieceToFound;
    }

    public void addHitted(PexSprite hitted) {
        this.hitted.add(hitted);
    }

    public int hittedCount() {
        return hitted.size();
    }

    public int getPieceFound() {
        return pieceFound;
    }

    private void shufPex() {
        Random r = new Random();
        for (int i = 0; i < pexArray.length; i++) {
            for (int j = 0; j < pexArray[i].length; j++) {
                int iPos = r.nextInt(pexArray.length);
                int jPos = r.nextInt(pexArray[i].length);
                PexSprite tmp = pexArray[i][j];
                pexArray[i][j] = pexArray[iPos][jPos];
                pexArray[iPos][jPos] = tmp;
            }
        }
    }

    private void loadPexArray(ArrayList<TextureRegion> set) {
        PexSprite pex;
        int pexCount = set.size() * 2; // počet dílků pexesa
        int inLine = getInLine(pexCount); // počet dílků v jedné řadě
        pexArray = new PexSprite[pexCount / inLine][inLine];
        pieceSize = Math.min(Pexeso.HEIGHT / (pexCount / inLine), Pexeso.WIDTH / inLine); // velikost jednoho dílku
        int y = -1;
        for (int i = 0; i < pexCount; i++) {
            if (i % inLine == 0) y++;
            Object o = set.get(i / 2);
            pex = new PexSprite((TextureRegion) o, i / 2);
            try {
                pexArray[y][i % inLine] = pex;
            } catch (Exception e) {
                Gdx.app.log("chyba", "asdf", e);
            }
        }
    }

    private void loadTab() {
        for (PexSprite[] aPexArray : pexArray) {
            for (PexSprite anAPexArray : aPexArray) {
                tab.add(anAPexArray).pad(10).size(pieceSize - 20);
            }
            tab.row();
        }
        tab.padTop(30).top();
    }

    private int getInLine(int pexCount) {
        int i, j;
        i = 1;
        j = pexCount;
        while (i < j) {
            i++;
            if (pexCount % i == 0) {
                j = pexCount / i;
            }
        }
        return j;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (hitted.size() == 2) {
            timer += delta;
            if (timer >= 2) {
                PexSprite hitOne = hitted.get(0);
                PexSprite hitTwo = hitted.get(1);
                if (hitOne.getId() == hitTwo.getId()) {
                    hitOne.setFound();
                    hitTwo.setFound();
                    pieceFound++;
                } else {
                    hitOne.startTurning();
                    hitTwo.startTurning();
                }
                hitted.clear();
                timer = 0;
            }
        }
    }
}
