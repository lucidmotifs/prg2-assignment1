package lms.model;

public class Library {
	// private:
	private LibraryCollection collection;
	private Member member;

	public Library() {
		
	}
	
	public void setCollection(LibraryCollection collection) {
		this.collection = collection;
	}
	
	public LibraryCollection getCollection() {
		return this.collection;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	public Member getMember() {
		return this.member;
	}
}
