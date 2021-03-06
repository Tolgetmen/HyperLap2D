package games.rednblack.editor.controller.commands;

import games.rednblack.editor.HyperLap2DFacade;
import games.rednblack.h2d.common.MsgAPI;

public class AddToLibraryAction extends NonRevertibleCommand {

    public AddToLibraryAction() {
        setShowConfirmDialog(false);
    }

    @Override
    public void doAction() {
        Object[] payload = notification.getBody();
        String key = (String) payload[0];
        String data = (String) payload[1];
        libraryActions.put(key, data);

        HyperLap2DFacade.getInstance().sendNotification(MsgAPI.LIBRARY_ACTIONS_UPDATED);
    }

    public static Object[] getPayload(String key, String data) {
        Object[] payload = new Object[2];
        payload[0] = key;
        payload[1] = data;
        return payload;
    }
}
