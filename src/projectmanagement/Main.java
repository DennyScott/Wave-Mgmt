package projectmanagement;

import java.awt.Image;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.demo.ThePanel;
import aurelienribon.tweenengine.Tween;
import controllers.PagesController;
import controllers.UserController;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		List<Image> icons = new ArrayList<Image>();
//		icons.add(getImage("someImage16x16.gif"));
//		icons.add(getImage("someImage32x32.gif"));
//		window.setIconImages(icons);
//		}
		PagesController page = new PagesController();
		page.launch();
	}

}
