package com.jhw.swing.models.detail;

import com.root101.swing.material.components.button.MaterialButtonIcon;
import com.root101.swing.material.components.button.MaterialButtonsFactory;
import com.root101.swing.material.components.button._MaterialButtonIconTransparent;
import com.root101.swing.material.components.container.panel._PanelGradient;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import com.root101.swing.material.standards.MaterialIcons;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class _MaterialPanelActions extends _PanelGradient {

    public static _MaterialPanelActions from() {
        return new _MaterialPanelActions();
    }

    private Action actionDelete;
    private Action actionEdit;
    private Action actionView;

    private boolean deleteVisibility = true;
    private boolean editVisibility = true;
    private boolean viewVisibility = true;

    private final ArrayList<Action> extras = new ArrayList<>();

    public _MaterialPanelActions() {
        this.setLayout(new GridLayout(1, 0));
    }

    public void addButtonDeleteActionListener(Action action) {
        actionDelete = action;
    }

    public void addButtonViewActionListener(Action action) {
        actionView = action;
    }

    public void addButtonEditActionListener(Action action) {
        actionEdit = action;
    }

    public void setButtonsVisibility(boolean delete, boolean edit, boolean view) {
        this.deleteVisibility = delete;
        this.editVisibility = edit;
        this.viewVisibility = view;
    }

    public void addExtras(ArrayList<Action> ex) {
        this.extras.clear();
        this.extras.addAll(ex);
    }

    public void personalize() {
        this.removeAll();

        for (Action c : extras) {
            MaterialButtonIcon act = MaterialButtonsFactory.buildIconTransparent();
            act.setAction(c);
            this.add(act);
        }
        if (viewVisibility) {
            MaterialButtonIcon act = MaterialButtonsFactory.buildIconTransparent();
            act.setAction(actionView);
            this.add(act);
        }
        if (editVisibility) {
            MaterialButtonIcon act = MaterialButtonsFactory.buildIconTransparent();
            act.setAction(actionEdit);
            this.add(act);
        }
        if (deleteVisibility) {
            MaterialButtonIcon act = MaterialButtonsFactory.buildIconTransparent();
            act.setAction(actionDelete);
            this.add(act);
        }

    }

    public static class builder {

        public JPopupMenu builPopup() {
            JPopupMenu popup = new javax.swing.JPopupMenu();
            for (Action extra : extras) {
                JMenuItem itemExtra = new javax.swing.JMenuItem();
                itemExtra.setAction(extra);
                popup.add(itemExtra);
            }
            if (viewVisibility) {
                JMenuItem itemView = new javax.swing.JMenuItem();
                itemView.setAction(viewAction);
                popup.add(itemView);
            }
            if (editVisibility) {
                JMenuItem itemEdit = new javax.swing.JMenuItem();
                itemEdit.setAction(editAction);
                popup.add(itemEdit);
            }
            if (deleteVisibility) {
                JMenuItem itemDelete = new javax.swing.JMenuItem();
                itemDelete.setAction(deleteAction);
                popup.add(itemDelete);
            }
            return popup;
        }

        private Action deleteAction;
        private Action editAction;
        private Action viewAction;
        private boolean deleteVisibility = true;
        private boolean editVisibility = true;
        private boolean viewVisibility = true;

        private final ArrayList<Action> extras = new ArrayList<>();

        public builder extra(Action c) {
            this.extras.add(c);
            return this;
        }

        public builder deleteAction(Action lis) {
            this.deleteAction = new AbstractAction("Eliminar", MaterialIcons.DELETE_FOREVER.deriveIcon(18)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    lis.actionPerformed(e);
                }
            };
            return this;
        }

        public builder editAction(Action lis) {
            this.editAction = new AbstractAction("Editar", MaterialIcons.EDIT.deriveIcon(18)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    lis.actionPerformed(e);
                }
            };
            return this;
        }

        public builder viewAction(Action lis) {
            this.viewAction = new AbstractAction("Ver", MaterialIcons.VISIBILITY.deriveIcon(18)) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    lis.actionPerformed(e);
                }
            };
            return this;
        }

        public builder deleteVisibility(boolean vis) {
            this.deleteVisibility = vis;
            return this;
        }

        public builder editVisibility(boolean vis) {
            this.editVisibility = vis;
            return this;
        }

        public builder viewVisibility(boolean vis) {
            this.viewVisibility = vis;
            return this;
        }

        public builder buttonsVisibility(boolean delete, boolean edit, boolean view) {
            this.deleteVisibility = delete;
            this.editVisibility = edit;
            this.viewVisibility = view;
            return this;
        }

        public int getComponents() {
            int count = extras.size();
            count += deleteVisibility ? 1 : 0;
            count += editVisibility ? 1 : 0;
            count += viewVisibility ? 1 : 0;
            return count;
        }

        public _MaterialPanelActions build() {
            _MaterialPanelActions action = _MaterialPanelActions.from();
            action.addButtonDeleteActionListener(deleteAction);
            action.addButtonEditActionListener(editAction);
            action.addButtonViewActionListener(viewAction);
            action.setButtonsVisibility(deleteVisibility, editVisibility, viewVisibility);
            action.addExtras(extras);
            action.personalize();
            return action;
        }
    }

    @Override
    public String toString() {
        return "";
    }
}
