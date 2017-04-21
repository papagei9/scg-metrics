package ch.unibe.scg.metrics.szz.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * used for external services that can provide their information about commits (from issue trackers, etc.)
 * Set<String> automatically removes duplicates
 */
public class SZZBugRepository {

	private Set<String> bugCommits;
	
	public SZZBugRepository() {
		bugCommits = new HashSet<>();
	}

	public Set<String> getBugCommits() {
		return bugCommits;
	}

	public void setBugCommits(Set<String> bugCommits) {
		this.bugCommits = bugCommits;
	}
	
	public void addBugCommit(String commitHash) {
		this.bugCommits.add(commitHash);
	}
	
	public boolean removeBugCommit(String commitHash) {
		return this.bugCommits.remove(commitHash);
	}
	
	public boolean isBugfixCommit(String commitHash) {
		return bugCommits.contains(commitHash);
	}
}