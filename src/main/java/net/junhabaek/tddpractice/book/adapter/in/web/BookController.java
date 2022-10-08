package net.junhabaek.tddpractice.book.adapter.in.web;

import lombok.RequiredArgsConstructor;
import net.junhabaek.tddpractice.book.application.port.in.BookCommand;
import net.junhabaek.tddpractice.book.application.port.in.BookInfo;
import net.junhabaek.tddpractice.book.application.servicce.RegisterBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final RegisterBookService registerBookService;

    private final BookRequestDtoMapper requestDtoMapper;

    private final BookResponseDtoMapper responseDtoMapper;

    @PostMapping("books")
    public ResponseEntity<BookResponseDto.RegisterBookResponse> registerBook(@RequestBody BookRequestDto.RegisterBookRequest request){
        BookCommand.RegisterBook command = requestDtoMapper.registerRequestToRegisterCommand(request);

        BookInfo resultBookInfo = registerBookService.registerBook(command);

        BookResponseDto.RegisterBookResponse responseDto = responseDtoMapper.bookInfoToRegisterBookResponse(resultBookInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
