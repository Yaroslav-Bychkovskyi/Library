package com.example.Library;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
  private int id;
  private String username;
   private List<Book> books;
}
