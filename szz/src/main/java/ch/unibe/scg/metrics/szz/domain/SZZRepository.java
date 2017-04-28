package ch.unibe.scg.metrics.szz.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.repodriller.domain.Modification;

public class SZZRepository {

	private Map<String, SZZFile> files;
	
	public SZZRepository() {
		files = new HashMap<>();
	}
	
	
	public SZZFile saveOrGet(Modification m) {
		
		String fileName = m.getNewPath();
		
		if(!files.containsKey(fileName)) {
			files.put(fileName, new SZZFile(fileName));
		}
		
		return files.get(fileName);
	}
	
	public SZZFile getFile(Modification m) {
		if(!files.containsKey(m.getNewPath())) return null;
		return files.get(m.getNewPath());
	}
	
	public void rename(Modification m) {
		String oldPath = m.getOldPath();
		String newPath = m.getNewPath();
		
		SZZFile file = files.remove(oldPath);
		
		if(file != null) {
			if(!files.containsKey(newPath)) {
				file.rename(newPath);
				files.put(newPath, file);
			} else {
				
				// transfer bug count in commits of old file to new file
				SZZFile sameFile = files.get(newPath);
				
				for(SZZCommit c : file.getCommits()) {
					for(SZZCommit sc : sameFile.getCommits()) {
						if(c.getHash().equals(sc.getHash())) {
							sc.increaseBugs(c.getBugs());
							break;
						}
					}
				}
			}
		}
	}

	public Collection<SZZFile> all() {
		return files.values();
	}

}
