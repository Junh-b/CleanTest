package net.junhabaek.tddpractice.book.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @PostMapping("books")
    public ResponseEntity<Void> registerBook(BookRequestDto.RegisterBookRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
