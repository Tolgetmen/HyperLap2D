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

package games.rednblack.editor.view.stage;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.util.ToastManager;
import com.kotcrab.vis.ui.widget.VisTable;
import games.rednblack.editor.HyperLap2DFacade;
import games.rednblack.editor.utils.HyperLap2DUtils;
import games.rednblack.editor.view.ui.FollowersUIMediator;
import games.rednblack.editor.view.ui.RulersUIMediator;
import games.rednblack.editor.view.ui.UIBottomMenuBar;
import games.rednblack.editor.view.ui.UIMainTable;
import games.rednblack.h2d.common.proxy.CursorManager;

public class UIStage extends Stage {

    public static final int SANDBOX_TOP_MARGIN = 104;
    public static final int SANDBOX_BOTTOM_MARGIN = 38;
    public static final int SANDBOX_LEFT_MARGIN = 40;

    private final HyperLap2DFacade facade;
    public Group dummyTarget;
    public UIMainTable uiMainTable;
    public Group contextMenuContainer;
    private final ToastManager toastManager;

	public Group midUI;

    public UIStage() {
        super(new ScreenViewport(), new PolygonSpriteBatch());

        getViewport().getCamera().position.setZero();

        facade = HyperLap2DFacade.getInstance();

        //dummy target is basically the target of drop of items from resoruce panel
        dummyTarget = new Group();
        dummyTarget.setWidth(getWidth());
        dummyTarget.setHeight(getHeight() - SANDBOX_TOP_MARGIN);
        dummyTarget.setY(SANDBOX_BOTTOM_MARGIN);
        dummyTarget.setX(SANDBOX_LEFT_MARGIN);
        dummyTarget.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                CursorManager cursorManager = facade.retrieveProxy(CursorManager.NAME);
                cursorManager.displayCustomCursor();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    CursorManager cursorManager = facade.retrieveProxy(CursorManager.NAME);
                    cursorManager.hideCustomCursor();
                }
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                CursorManager cursorManager = facade.retrieveProxy(CursorManager.NAME);
                cursorManager.displayCustomCursor();
                return false;
            }
        });

        addActor(dummyTarget);

        midUI = new Group();
        addActor(midUI);

        RulersUIMediator rulersUIMediator = facade.retrieveMediator(RulersUIMediator.NAME);
        Actor rulersGroup = rulersUIMediator.getViewComponent();

        FollowersUIMediator followersUIMediator = facade.retrieveMediator(FollowersUIMediator.NAME);
        Group followersGroup = followersUIMediator.getViewComponent();

        midUI.addActor(followersGroup);
        midUI.addActor(rulersGroup);

        contextMenuContainer = new Group();
        uiMainTable = new UIMainTable();

        addActor(uiMainTable);
        addActor(contextMenuContainer);

		VisTable mainBottomTable = new VisTable();
		mainBottomTable.setFillParent(true);
		mainBottomTable.bottom();
		UIBottomMenuBar bottomMenuBar = new UIBottomMenuBar();
		mainBottomTable.add(bottomMenuBar).fillX().expandX().height(38);
		addActor(mainBottomTable);

        setListeners();

        toastManager = new ToastManager(this);
        toastManager.setAlignment(Align.bottomLeft);
    }

    public ToastManager getToastManager() {
        return toastManager;
    }

    public void resize(int width, int height) {
        getViewport().update(width, height, true);
        dummyTarget.setWidth(width);
        dummyTarget.setHeight(height - SANDBOX_TOP_MARGIN);
    }

    public void setKeyboardFocus() {
        setKeyboardFocus(dummyTarget);
    }

    public void setListeners() {
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return event.getTarget() != getRoot() && event.getTarget() != dummyTarget;
            }
        });

        //TODO Undecorated window is cool but too much glitches
        //HyperLap2DUtils.setWindowResizeListener(this.getRoot());
    }

    @Override
    public boolean keyDown(int keyCode) {
        return super.keyDown(keyCode);
    }
}
