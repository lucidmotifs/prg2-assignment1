package lms.model;

public class Library {

	private static Library library;
	private LibraryCollection collection = null;
	private Member member;

	// private constructor prevents instantiation from other classes
	private Library() {}	
	// makes sure that only one instance of DateUtil can be created
	public static Library getInstance() {
		if (library == null)
			library = new Library();
		return library;
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
	
