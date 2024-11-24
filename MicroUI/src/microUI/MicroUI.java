package microUI;

import processing.core.PApplet;

public final class MicroUI {
 public static PApplet app;
 
 private MicroUI() {}
 
 public static void init(PApplet app) { MicroUI.app = app; }
 
}