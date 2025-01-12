package microUI.utils;

public class Physics {
	
	public final static boolean collision(BaseForm form, BaseForm otherForm) {
		
		if(form.getX() > otherForm.getX()-form.getW() 		&&
		   form.getX() < otherForm.getX()+otherForm.getW()	&&
		   form.getY() > otherForm.getY()-form.getH()		&&
		   form.getY() < otherForm.getY()+otherForm.getH()) {
			return true;
		}
		
		return false;
	}
	
	public final static void constrain(float x, float y, BaseForm form, BaseForm otherForm) {
		float px = form.getX(), py = form.getY();
		form.setPosition(x,y);
		
		if(collision(form,otherForm)) {
			form.setPosition(px,py);
		}
	}
	
}
