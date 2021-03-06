/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package games.rednblack.editor.proxy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import games.rednblack.editor.HyperLap2DFacade;
import games.rednblack.editor.renderer.utils.MySkin;
import org.puremvc.java.patterns.proxy.Proxy;


public class EditorTextureManager extends Proxy {
    private static final String TAG = EditorTextureManager.class.getCanonicalName();
    public static final String NAME = TAG;

    private Texture pixel;

    public MySkin editorSkin;
    private TextureAtlas editorAtlas;

    public EditorTextureManager() {
        super(NAME);
    }

    @Override
    public void onRegister() {
        super.onRegister();
        facade = HyperLap2DFacade.getInstance();
        loadEditorAssets();
    }

    public void loadEditorAssets() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        pixel = new Texture(pixmap);
    }

    public Texture getEditorAsset(String name) {
        return pixel;
    }

    public TextureAtlas getEditorAssetsList() {
        return editorAtlas;
    }

    public void dispose() {

    }


}
