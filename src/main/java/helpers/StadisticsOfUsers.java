package helpers;

public class StadisticsOfUsers {
	public int total;
	public int totalVisitor;
	public int totalCommensal;
	public int totalGourmet;
	public int activeVisitor;
	public int activeCommensal;
	public int activeGourmet;
	public int noActiveVisitor;
	public int noActiveCommensal;
	public int noActiveGourmet;
	
	
	public void addActiveVisitor(){
		total++;
		totalVisitor++;
		activeVisitor++;
	}
	
	public void addActiveCommensal(){
		total++;
		totalCommensal++;
		activeCommensal++;
	}
	public void addActiveGourmet(){
		total++;
		totalGourmet++;
		activeGourmet++;
	}
	public void addNoActiveVisitor(){
		total++;
		totalVisitor++;
		noActiveVisitor++;
	}
	public void addNoActiveCommensal(){
		total++;
		totalCommensal++;
		noActiveCommensal++;
	}
	public void addNoActiveGourmet(){
		total++;
		totalGourmet++;
		noActiveGourmet++;
	}
	
	public StadisticsOfUsers() {
		total=0;
		totalVisitor=0;
		totalCommensal=0;
		totalGourmet=0;
		activeVisitor=0;
		activeCommensal=0;
		activeGourmet=0;
		noActiveVisitor=0;
		noActiveCommensal=0;
		noActiveGourmet=0;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalVisitor() {
		return totalVisitor;
	}
	public void setTotalVisitor(int totalVisitor) {
		this.totalVisitor = totalVisitor;
	}
	public int getTotalCommensal() {
		return totalCommensal;
	}
	public void setTotalCommensal(int totalCommensal) {
		this.totalCommensal = totalCommensal;
	}
	public int getTotalGourmet() {
		return totalGourmet;
	}
	public void setTotalGourmet(int totalGourmet) {
		this.totalGourmet = totalGourmet;
	}
	public int getActiveVisitor() {
		return activeVisitor;
	}
	public void setActiveVisitor(int activeVisitor) {
		this.activeVisitor = activeVisitor;
	}
	public int getActiveCommensal() {
		return activeCommensal;
	}
	public void setActiveCommensal(int activeCommensal) {
		this.activeCommensal = activeCommensal;
	}
	public int getActiveGourmet() {
		return activeGourmet;
	}
	public void setActiveGourmet(int activeGourmet) {
		this.activeGourmet = activeGourmet;
	}
	public int getNoActiveVisitor() {
		return noActiveVisitor;
	}
	public void setNoActiveVisitor(int noActiveVisitor) {
		this.noActiveVisitor = noActiveVisitor;
	}
	public int getNoActiveCommensal() {
		return noActiveCommensal;
	}
	public void setNoActiveCommensal(int noActiveCommensal) {
		this.noActiveCommensal = noActiveCommensal;
	}
	public int getNoActiveGourmet() {
		return noActiveGourmet;
	}
	public void setNoActiveGourmet(int noActiveGourmet) {
		this.noActiveGourmet = noActiveGourmet;
	}
	
	
}
