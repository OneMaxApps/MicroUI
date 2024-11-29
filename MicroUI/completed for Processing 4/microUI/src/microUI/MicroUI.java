package microUI;

import processing.core.PApplet;

public final class MicroUI {
 public static PApplet app = null;
 public static int createdFormsCount = 0;
 
 private MicroUI() {}
 
 public static void init(PApplet app) { MicroUI.app = app; }
 
 public static int getCreatedFormsCount() { return createdFormsCount; }
}