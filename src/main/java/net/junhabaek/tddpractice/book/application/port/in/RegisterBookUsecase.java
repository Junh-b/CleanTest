package net.junhabaek.tddpractice.book.application.port.in;

public interface RegisterBookUsecase {
    BookInfo registerBook(BookCommand.RegisterBook registerBookCommand);
}
