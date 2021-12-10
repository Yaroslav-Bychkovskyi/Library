package com.example.Library;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class Controller {
  BookRepository bookRepository;
  UserRepository userRepository;

  @PostMapping("/addbook")
  public Book book(@RequestBody Book bookDao) {

    final var tableBook = new TableBook();
    tableBook.setName(bookDao.getName());
    tableBook.setAuthor(bookDao.getAuthor());

    TableBook save = bookRepository.save(tableBook);
    Book book = new Book();
    book.setId(save.getId());
    book.setName(save.getName());
    book.setAuthor(save.getAuthor());

    return book;
  }

  @PostMapping("/adduser")
  public User user(@RequestBody User userDao) {
    final var tableUser = new TableUser();
    tableUser.setId(userDao.getId());
    tableUser.setUsername(userDao.getUsername());


    TableUser save = userRepository.save(tableUser);
    User user = new User();
    user.setId(save.getId());
    user.setUsername(save.getUsername());

    return user;
  }

  @DeleteMapping("/book")
  public void delBook(@RequestParam int bookId) {
    Optional<TableBook> tb = bookRepository.findById(bookId);
    tb.ifPresent(tableBook -> bookRepository.delete(tableBook));

  }

  @DeleteMapping("/user")
  public void delUser(@RequestParam int userId) {
    Optional<TableUser> tu = userRepository.findById(userId);
    tu.ifPresent(tableUser -> userRepository.delete(tableUser));

  }

  @GetMapping("/take")
  public void toGetUser(@RequestParam int id, int bookId) throws Exception {
    TableUser tableUser = userRepository.getById(id);
    TableBook tableBook = bookRepository.getById(bookId);
    if (tableBook.getTableUser() != null) {
      throw new Exception();
    }
    tableUser.getTableBooks().add(tableBook);
    tableBook.setTableUser(tableUser);
    userRepository.save(tableUser);


  }

  @GetMapping("/return")
  public void returnBook(@RequestParam int bookId) throws Exception {
    TableBook tableBook = bookRepository.getById(bookId);
    if (tableBook.getTableUser() != null) {
      tableBook.setTableUser(null);
      bookRepository.save(tableBook);

    } else throw new Exception("there is no book in the user");


  }

  private Book getBook(TableBook save) {
    Book b = new Book();
    b.setName(save.getName());
    b.setId(save.getId());
    b.setAuthor(save.getAuthor());
    b.setUserId(save.getTableUser().getId());

    return b;
  }

  @GetMapping("/getuser")
  public User getUser(@RequestParam int userId) {

    TableUser tableUser = userRepository.getById(userId);
    User user = new User();
    user.setId(tableUser.getId());
    user.setUsername(tableUser.getUsername());

    List<Book> books = new ArrayList<>();
    user.setBooks(books);
    for (TableBook t : tableUser.getTableBooks()){
      Book bk = getBook(t);
      books.add(bk);
    }

    return user;
  }
}
