package microui.util;

import microui.core.base.SpatialView;

public final class Physics {
	
	public final static boolean collision(final SpatialView form, final  SpatialView otherForm) {
		
		if(form.getX() > otherForm.getX()-form.getWidth() 		&&
		   form.getX() < otherForm.getX()+otherForm.getWidth()	&&
		   form.getY() > otherForm.getY()-form.getHeight()		&&
		   form.getY() < otherForm.getY()+otherForm.getHeight()) {
		   return true;
		}
		
		return false;
	}
	
	public final static void constrain(final float x, final float y, final  SpatialView form, final  SpatialView otherForm) {
		final float px = form.getX(), py = form.getY();
		form.setPosition(x,y);
		
		if(collision(form,otherForm)) {
			form.setPosition(px,py);
			if(collision(form,otherForm)) {
				if(form.getX()+form.getWidth()/2 < otherForm.getX()+otherForm.getWidth()/2) { form.setX(otherForm.getX()-form.getWidth()); } else { form.setX(otherForm.getX()+otherForm.getWidth()); }
				if(form.getY()+form.getHeight()/2 < otherForm.getY()+otherForm.getHeight()/2) { form.setY(otherForm.getY()-form.getHeight()); } else { form.setY(otherForm.getY()+otherForm.getHeight()); }
			}
		}
		
	}
	
}
