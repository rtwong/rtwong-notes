ToDoodle
A simple andriod To-do list
Randy Wong
rtwong
CMPUT 301

IMPORTANT: If looking at source and recompiling, need to import android-support-v7-appcompat. Tutorial to do so at https://developer.android.com/tools/support-library/setup.html under "Adding libraries with resources". The library itself can be copied from "/usr/local/share/android-sdk-linux/extras/android/support/v7/appcompat".



Default Screen opens on TodoView
	-top right menu allows access to archive/summary/email pages
	-click to check/uncheck
	-long click to archive/delete

Archive page
	-long click to unarchive/delete

Emailing
	IMPORTANT: Email apps must have email authentication done
	-Send All, sends all current and archived todo items
	-Send Archived, sends only archived todo items
	-Select Current/Archived allows user to select some set of the items
