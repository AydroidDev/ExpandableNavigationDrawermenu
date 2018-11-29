package com.example.vishalhalani.expandablenavigationdrawermenu;

public class MenuModel {
    public String menuName;
    public String icon;
    public boolean isVisible = false;
    public boolean hasChildren, isGroup;
    private String menuTag;


    public MenuModel(String menuName, String icon, boolean isGroup, boolean hasChildren, boolean isVisible, String tag) {

        this.menuName = menuName;
        this.icon = icon;
        this.isVisible = isVisible;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.menuTag = tag;
    }

    public String getMenuTag() {
        return menuTag;
    }

    public void setMenuTag(String menuTag) {
        this.menuTag = menuTag;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }
}
