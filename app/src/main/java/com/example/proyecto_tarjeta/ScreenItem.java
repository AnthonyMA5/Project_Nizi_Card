package com.example.proyecto_tarjeta;

public class ScreenItem {

    String Title, Descp;
    int ScreenAnm;

    public ScreenItem(String title, String descp, int screenAnm) {
        Title = title;
        Descp = descp;
        ScreenAnm = screenAnm;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescp() {
        return Descp;
    }

    public void setDescp(String descp) {
        Descp = descp;
    }

    public int getScreenAnm() {
        return ScreenAnm;
    }

    public void setScreenAnm(int screenAnm) {
        ScreenAnm = screenAnm;
    }
}
