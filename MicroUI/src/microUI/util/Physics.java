package microUI.util;

public final class Physics {
	
	public final static boolean collision(final BaseForm form, final  BaseForm otherForm) {
		
		if(form.getX() > otherForm.getX()-form.getW() 		&&
		   form.getX() < otherForm.getX()+otherForm.getW()	&&
		   form.getY() > otherForm.getY()-form.getH()		&&
		   form.getY() < otherForm.getY()+otherForm.getH()) {
		   return true;
		}
		
		return false;
	}
	
	public final static void constrain(final float x, final float y, final  BaseForm form, final  BaseForm otherForm) {
		final float px = form.getX(), py = form.getY();
		form.setPosition(x,y);
		
		if(collision(form,otherForm)) {
			form.setPosition(px,py);
		}
	}
	
}
