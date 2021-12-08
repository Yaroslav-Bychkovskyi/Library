package com.example.Library;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    if (tb.isPresent()) {
      bookRepository.delete(tb.get());

    }

  }

  @DeleteMapping("/user")
  public void delUser(@RequestParam int userId) {
    Optional<TableUser> tu = userRepository.findById(userId);
    if (tu.isPresent()) {
      userRepository.delete(tu.get());

    }

  }
}
