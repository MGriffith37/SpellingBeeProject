package app.model;

/**
 * Created by Fraser McIntosh on 16/09/2016.
 * Enum corresponding to the different files and their respective file addresses
 */
public enum WordFile {


    SPELLING_LIST("NZCER-spelling-lists.txt"),
    FAULTED(".app_files/.faulted_stats.txt"),
    FAILED(".app_files/.failed_stats.txt"),
    MASTERED(".app_files/.mastered_stats.txt"),
    REVIEW(".app_files/.reviewlist.txt"),
    ATTEMPTED(".app_files/.attempted.txt"),
	ONELIFESCORE(".app_files/.onelifescore.txt"),
	THREELIVESSCORE(".app_files/.threelivesscore.txt");
	
	
    String _filename;
    /**
     * Constructor to set filename for enum
     */
    private WordFile(String filename) {
        _filename = filename;
    }

    /**
     * Sets file name
     */
    public void setFileName(String filename){
    	_filename = filename;
    }
    /**
     * toString overridden method
     */
    @Override
    public String toString() {
        return _filename;
    }
}
