package bck.kdan.quiz1.imagemanipulation;

import java.util.List;

public interface ImageManipulationView
{
	public void setBitmapDisplayDataList(List<BitmapDisplayData> dataList);
	
	public void updateDisplayView();
	
	public boolean addImageManipulationListener(ImageManipulationListener listener);
	
	public boolean removeImageManipulationListener(ImageManipulationListener listener);
}
