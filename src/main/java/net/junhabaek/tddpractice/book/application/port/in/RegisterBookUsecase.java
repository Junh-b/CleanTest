package net.junhabaek.tddpractice.book.application.port.in;

import javax.validation.Valid;

public interface RegisterBookUsecase {
    BookInfo registerBook(@Valid BookCommand.RegisterBook registerBookCommand);
}
