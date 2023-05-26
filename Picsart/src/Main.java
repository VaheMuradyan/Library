import java.util.*;
import java.time.LocalDate;
class Picsart {
    public static void main(String ... args){
        Book mek = new Book("mek", "es");
        Book erk = new Book("erk", "du");
        Book ere = new Book("ere","menq");
        Librarian marat = new Librarian("Marat", "chka");
        marat.addBookToLibrary(erk);
        marat.addBookToLibrary(mek);
        Borrower vahe = new Borrower("vahe", "chka");
        marat.getBook(vahe,mek);
        marat.getBook(vahe, erk);
        for(Optional<Book> ob : vahe.getMyBooks()){
            System.out.println(ob.get().getTitle());
        }
        marat.retBookLib(vahe, erk);
        //marat.retBookLib(vahe,ere);
        System.out.println();
        for(Optional<Book> ob : vahe.getMyBooks()){
            System.out.println(ob.get().getTitle());
        }
    }
}
class Book{
    private final String title;
    private final String autor;
    private final LocalDate publishDate = LocalDate.now();

    Book(String title, String autor){
        this.title = title;
        this.autor = autor;
    }
    public String getTitle(){
        return title;
    }
    public String getAutor(){
        return autor;
    }
    public LocalDate getPublishDate(){
        return publishDate;
    }
}



class Borrower {
    private final String name;
    private final String contInfo;
    private List<Optional<Book>> myBooks = new LinkedList<>();
    Borrower(String name,String contInfo){
        this.name = name;
        this.contInfo = contInfo;
    }
    public String getName(){
        return name;
    }

    public String getContInfo(){
        return contInfo;
    }

    public List<Optional<Book>> getMyBooks(){
        return myBooks;
    }

    public void addBook(Optional<Book> ob){
        myBooks.add(ob);
    }
}

class Librarian {
    private final String name;
    private final String contInfo;
    //Borrower & Librarian unen nuyn field-er@ name & contInfo, karair ogtagorceir inheritance code duplication-ic xusapelu hamar
    private final Library library = Library.getInstance();

    Librarian(String name,String contInfo){
        this.name = name;
        this.contInfo = contInfo;
    }

    public String getName(){
        return name;
    }

    public String getContInfo(){
        return contInfo;
    }

    public void getBook(Borrower borrower, Book book) {
        try{
            Optional<Book> book1 = library.getBook(book);
            borrower.addBook(book1);
        }catch (NoSuchElementException e){
            System.out.println("Ther are not book whot you wont");
        }
    }

    public void retBookLib(Borrower borrower, Book book){
        Optional<Book> book1 = Optional.ofNullable(book);
        if(borrower.getMyBooks().contains(book1)){
            library.newBook(book);
            borrower.getMyBooks().remove(book1);
            book1 = null;
        }else{
            System.out.println("You dont had thet book");
        }
    }

    public void addBookToLibrary(Book ob){
        library.newBook(ob);
    }


}

class Library{

    final LinkedList<Book> books = new LinkedList<>();
    private static final Library Instance = new Library();

    private Library(){ }

    public static Library getInstance(){
        return Instance;
    }

    public void newBook(Book ob) {
        books.add(ob);
    }


    public Optional<Book> getBook(Book ob) throws NoSuchElementException{
        Book val = null;
        for(int i = 0; i < books.size(); i++){
            if(books.get(i).getTitle().equals(ob.getTitle())){
                val = ob;
                books.remove(i);
            }
        }
        return Optional.ofNullable(val);
    }

}