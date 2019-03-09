package com.locrep.interfaces;
import java.util.List;

import com.locrep.exception.*;
import com.locrep.npm.*;
public interface Search {

	List<Npm> findNpms();
	List<Npm> findNpm(String NpmName) throws NpmNotFoundException;
}
