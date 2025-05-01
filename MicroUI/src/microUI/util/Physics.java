package microUI.util;

import microUI.core.AbstractRectangle;

public final class Physics {
	
	public final static boolean collision(final AbstractRectangle form, final  AbstractRectangle otherForm) {
		
		if(form.getX() > otherForm.getX()-form.getW() 		&&
		   form.getX() < otherForm.getX()+otherForm.getW()	&&
		   form.getY() > otherForm.getY()-form.getH()		&&
		   form.getY() < otherForm.getY()+otherForm.getH()) {
		   return true;
		}
		
		return false;
	}
	
	public final static void constrain(final float x, final float y, final  AbstractRectangle form, final  AbstractRectangle otherForm) {
		final float px = form.getX(), py = form.getY();
		form.setPosition(x,y);
		
		if(collision(form,otherForm)) {
			form.setPosition(px,py);
			if(collision(form,otherForm)) {
				if(form.getX()+form.getW()/2 < otherForm.getX()+otherForm.getW()/2) { form.setX(otherForm.getX()-form.getW()); } else { form.setX(otherForm.getX()+otherForm.getW()); }
				if(form.getY()+form.getH()/2 < otherForm.getY()+otherForm.getH()/2) { form.setY(otherForm.getY()-form.getH()); } else { form.setY(otherForm.getY()+otherForm.getH()); }
			}
		}
		
	}
	
}
