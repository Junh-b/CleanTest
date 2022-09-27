package net.junhabaek.tddpractice.book.adapter.in.web;

import net.junhabaek.tddpractice.book.application.port.in.BookCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BookRequestDtoMapper {
    BookCommand.RegisterBook registerRequestToRegisterCommand(BookRequestDto.RegisterBookRequest registerRequest);
}
