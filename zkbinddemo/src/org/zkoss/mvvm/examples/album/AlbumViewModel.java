/* AlbumViewModel.java

	Purpose:
		
	Description:
		
	History:
		Sep 28, 2011 2:38:50 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.mvvm.examples.album;

import org.zkoss.zkbind.DependsOn;
import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkbind.NotifyChange;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

/**
 * View model of examples/album/album.zul, the example 
 * picked from Martin Fowler's Presentation Model pattern (http://martinfowler.com/eaaDev/PresentationModel.html).
 * 
 * @author henrichen
 */
public class AlbumViewModel extends GenericBindComposer {
	private ListModelList albumList;
	private AlbumProxy selectedAlbum;
	
	public AlbumViewModel() {
		albumList = new ListModelList(AlbumDataSet.getAlbums(), true);
		selectedAlbum = new AlbumProxy((Album) albumList.get(0));
	}

	@DependsOn("selectedAlbum")
	public String getTitle() {
		return selectedAlbum.getTitle();
	}
	@NotifyChange
	public void setTitle(String title) {
		selectedAlbum.setTitle(title);
	}
	
	@DependsOn("selectedAlbum")
	public String getArtist() {
		return selectedAlbum.getArtist();
	}
	@NotifyChange
	public void setArtist(String artist) {
		selectedAlbum.setArtist(artist);
	}
	
	@DependsOn("selectedAlbum")
	public boolean isClassical() {
		return selectedAlbum.isClassical();
	}
	@NotifyChange
	public void setClassical(boolean classical) {
		selectedAlbum.setClassical(classical);
	}
	
	@DependsOn("selectedAlbum")
	public String getComposer() {
		return isComposerNull() ? "" : selectedAlbum.getComposer();
	}
	@NotifyChange
	public void setComposer(String composer) {
		if (isClassical()) {
			selectedAlbum.setComposer(composer);
		}
	}
	
	public Album getSelectedAlbum() {
		return selectedAlbum;
	}
	
	@NotifyChange
	public void setSelectedAlbum(Album selected) {
		this.selectedAlbum = new AlbumProxy(selected);
	}
	
	@DependsOn("selectedAlbum")
	public boolean isComposerNull() {
		return selectedAlbum.getComposer() == null;
	}
	
	@DependsOn("selectedAlbum")
	public String getFormTitle() {
		return "Album: " + getTitle();
	}
	
	@DependsOn("classical")
	public boolean isComposerFieldEnabled() {
		return isClassical();
	}
	
	@DependsOn("rowChanged")
	public boolean isApplyEnabled() {
		return isRowChanged();
	}
	
	@DependsOn("rowChanged")
	public boolean isCancelEnabled() {
		return isRowChanged();
	}
	
	@DependsOn({"title", "artist", "classical", "composer"})
	public boolean isRowChanged() {
		return selectedAlbum.isDirty();
	}
	
	public ListModel getAlbumList() {
		return albumList;
	}
	
	//Commands//
	@NotifyChange("selectedAlbum")
	public void apply() {
		selectedAlbum.acceptChanges();
	}
	
	@NotifyChange("selectedAlbum")
	public void cancel() {
		selectedAlbum.rejectChanges();
	}
}
